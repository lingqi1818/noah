package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.Event;
import com.fangcloud.noah.service.vo.EventStatisticsTmp;
import com.fangcloud.noah.service.vo.EventStatisticsVo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenke on 16-12-13.
 */
@Service
public class RiskEventStatisticsService {

    private final Logger logger = LoggerFactory.getLogger(RiskEventStatisticsService.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MongoTemplate mongoTemplate;



    public EventStatisticsVo getTodayEventStatistics(String deviceType) throws ParseException {


        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);

        String startTime = sdf.format(today.getTime());

        List<EventStatisticsVo> eventStatisticsVoList = this.queryEventStatistics(deviceType,"",startTime,"");


        return eventStatisticsVoList.get(0);
    }



    public List<EventStatisticsVo> queryEventStatistics(String deviceType,String eventType,String startTime,String endTime) throws ParseException {

        Query query = new Query();

        Criteria criteria = new Criteria();

        Criteria startTimeCriteria = Criteria.where("createTime").gte(sdf.parse(startTime));

        if(StringUtils.isNotBlank(endTime)){
            Criteria endTimeCriteria = Criteria.where("createTime").lte(sdf.parse(endTime));
            criteria.andOperator(startTimeCriteria,endTimeCriteria);
        }else {
            criteria.and("createTime").gte(sdf.parse(startTime));
        }


        if(StringUtils.isNotBlank(deviceType)){
            criteria.and("deviceType").is(Integer.valueOf(deviceType));
        }

        if(StringUtils.isNotBlank(eventType)){
            criteria.and("eventType").is(eventType);
        }
        query.addCriteria(criteria);

        String map = "function Map() {\n" +
                "\n" +
                "var date= new Date(this.createTime);\n" +
                "var dateKey = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();\n" +
                "var acceptCount = 0;\n" +
                "var reviewCount = 0;\n" +
                "var rejectCount = 0;\n" +
                "if(this.decision==1){acceptCount =1};\n" +
                "if(this.decision==2){reviewCount=1};\n" +
                "if(this.decision==3){rejectCount=1}; \n" +
                "emit(\n" +
                " dateKey,\n" +
                " {totalCount:1,acceptCount:acceptCount,reviewCount:reviewCount,rejectCount:rejectCount}\n" +
                ");\n" +
                "}";

        String reduce = "function Reduce(key, values) {\n" +
                "\n" +
                "var totalCount = 0;\n" +
                "var acceptCount = 0;\n" +
                "var reviewCount = 0;\n" +
                "var rejectCount = 0;\n" +
                "\n" +
                "values.forEach(function(val) {  \n" +
                "\ttotalCount +=val.totalCount\n" +
                "        acceptCount +=val.acceptCount;\t\n" +
                "\treviewCount +=val.reviewCount;\t\n" +
                "\trejectCount +=val.rejectCount;\t\n" +
                "    }); \n" +
                "\n" +
                "return {\"date\":key,\"totalCount\":totalCount,\"acceptCount\":acceptCount,\"reviewCount\":reviewCount,\"rejectCount\":rejectCount};\n" +
                "}";

        String inputCollectionName = "event";

        MapReduceResults<EventStatisticsTmp> reduceResults =  mongoTemplate.mapReduce(query,inputCollectionName,map,reduce,EventStatisticsTmp.class);

        Iterator<EventStatisticsTmp> iterator = reduceResults.iterator();

        List<EventStatisticsVo> eventStatisticsVos = new ArrayList<EventStatisticsVo>();

        while(iterator.hasNext()){
            EventStatisticsTmp tmp = iterator.next();
            EventStatisticsVo vo = tmp.getValue();
            eventStatisticsVos.add(vo);

        }

        return eventStatisticsVos;
    }




    public void processStatiscs(Event event){

        //设备采集率




        //事件调用量




    }
}
