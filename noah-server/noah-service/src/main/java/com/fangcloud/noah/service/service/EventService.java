package com.fangcloud.noah.service.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.dao.entity.Event;
import com.fangcloud.noah.dao.enums.EventType;
import com.fangcloud.noah.dao.model.EventQuery;
import com.fangcloud.noah.service.common.PageObject;

/**
 * Created by chenke on 16-3-17.
 */
@Service
public class EventService {

    private final Logger               logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private MongoTemplate              mongoTemplate;

    private SimpleDateFormat           sdf    = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private RiskEventStatisticsService riskEventStatisticsService;

    public void saveEvent(Event event) {

        Assert.notNull(event, "event is null");
        mongoTemplate.insert(event);

    }

    public PageObject<Event> queryPage(EventQuery eventQuery) {
        Query query = new Query();

        Criteria createTimeCr = null;
        if (StringUtils.isNotBlank(eventQuery.getStartTime())) {

            try {

                createTimeCr = Criteria.where("createTime")
                        .gte(sdf.parse(eventQuery.getStartTime()));
            } catch (ParseException e) {
                throw new RiskRuntimeException("开始时间参数异常");
            }

        }

        Criteria endTimeCr = null;
        if (StringUtils.isNotBlank(eventQuery.getEndTime())) {
            try {

                createTimeCr = Criteria.where("createTime").lte(sdf.parse(eventQuery.getEndTime()));
            } catch (ParseException e) {
                throw new RiskRuntimeException("结束时间参数异常");
            }
        }

        if (createTimeCr != null && endTimeCr != null) {
            query.addCriteria(new Criteria().andOperator(createTimeCr, endTimeCr));
        } else if (createTimeCr != null) {
            query.addCriteria(createTimeCr);
        } else if (endTimeCr != null) {
            query.addCriteria(endTimeCr);
        }

        if (StringUtils.isNotBlank(eventQuery.getAccountLogin())) {
            query.addCriteria(Criteria.where("accountLogin").is(eventQuery.getAccountLogin()));
        }

        if (StringUtils.isNotBlank(eventQuery.getDeviceId())) {
            query.addCriteria(Criteria.where("deviceId").is(eventQuery.getDeviceId()));
        }

        if (StringUtils.isNotBlank(eventQuery.getIp())) {
            query.addCriteria(Criteria.where("ip").is(eventQuery.getIp()));
        }

        if (StringUtils.isNotBlank(eventQuery.getBeaconId())) {
            query.addCriteria(Criteria.where("beaconId").is(eventQuery.getBeaconId()));
        }

        if (StringUtils.isNotBlank(eventQuery.getDecision())) {
            query.addCriteria(
                    Criteria.where("decision").is(Integer.valueOf(eventQuery.getDecision())));
        }

        if (StringUtils.isNotBlank(eventQuery.getEventType())) {
            query.addCriteria(Criteria.where("eventType").is(eventQuery.getEventType()));
        }

        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "createTime")));

        int totalItem = this.getPageCount(query);

        if (eventQuery.getPage() < 1) {
            eventQuery.setPage(1);
        }

        PageObject<Event> datas = new PageObject<Event>(eventQuery.getPage(), totalItem);

        if (totalItem > 0) {
            query.skip(datas.getOffSet());
            query.limit(datas.getPageSize());
            List<Event> lists = this.mongoTemplate.find(query, Event.class);

            datas.initViewData(lists);
            return datas;
        }

        return datas;
    }

    public int getPageCount(Query query) {
        return (int) this.mongoTemplate.count(query, Event.class);
    }

    public Event queryEventById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return this.mongoTemplate.findOne(query, Event.class);
    }

    public void processRiskEventMessage(Event event) {

        riskEventStatisticsService.processStatiscs(event);
        //设备id采集不需保存
        if (EventType.GETCONFIG.getCode().equals(event.getEventType())) {
            return;
        }
        this.saveEvent(event);

    }
}
