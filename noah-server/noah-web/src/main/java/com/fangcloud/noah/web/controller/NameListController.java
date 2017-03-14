package com.fangcloud.noah.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangcloud.noah.api.api.enums.NameListGrade;
import com.fangcloud.noah.api.api.enums.NameListType;
import com.fangcloud.noah.api.api.model.NameList;
import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.dao.enums.NameListStatusType;
import com.fangcloud.noah.service.apiservice.ApiService;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.NameListService;

/**
 * Created by chenke on 16-8-20.
 */
@Controller
@RequestMapping("namelist")
public class NameListController {

    private static final Logger logger = LoggerFactory.getLogger(NameListController.class);

    @Autowired
    private NameListService     nameListService;

    @Autowired
    private ApiService          apiService;

    @RequestMapping("query")
    public String query(Integer pageNum, Integer nameType, Integer nameGrade, String content,
                        HttpServletRequest req) {

        PageObject<NameList> pageObj = nameListService.queryNameList(pageNum, nameType, nameGrade,
                content);

        req.setAttribute("content", content);
        req.setAttribute("pageObj", pageObj);

        return "namelist/list";
    }

    @RequestMapping("toEditName")
    public String toEditName(Integer nameId, HttpServletRequest req) {
        NameList nameList = nameListService.queryNameObj(nameId);
        req.setAttribute("nameObj", nameList);
        return "namelist/namelistform";
    }

    @RequestMapping("updateNameObj")
    public String updateNameObj(Integer nameId, Integer grade, String remark,
                                HttpServletRequest req) {

        NameList oldNameList = nameListService.queryNameObj(nameId);

        NameList nameList = new NameList();
        nameList.setId(nameId);
        nameList.setGrade(grade);
        nameList.setRemark(remark);
        nameListService.updateNameList(nameList);

        //如果是从黑名单转为白名单,则修改hlj.black_list
        if (oldNameList != null && oldNameList.getType().equals(NameListType.MOBILE.getCode())
                && oldNameList.getGrade() == NameListGrade.BLACK.getCode()
                && nameList.getGrade() == NameListGrade.WHITE.getCode()) {
            apiService.updateBlackList2White(oldNameList.getContent());
        }

        return query(null, null, null, null, req);
    }

    @ResponseBody
    @RequestMapping("add")
    public Result addNameList(String content, Integer type, Integer grade, String remark) {

        if (StringUtils.isBlank(content)) {
            return new Result(false);
        }

        try {

            NameList nameList = new NameList();
            nameList.setContent(content);
            nameList.setGrade(grade);
            nameList.setType(type);
            nameList.setStatus(NameListStatusType.INIT.getCode());
            nameList.setGmtCreated(new Timestamp(new Date().getTime()));
            nameList.setGmtModified(nameList.getGmtCreated());
            nameList.setRemark(remark);

            nameListService.saveNameList(nameList);

            return new Result();

        } catch (Exception e) {
            logger.error("add name list exception", e);
            return new Result(false);
        }
    }

    @ResponseBody
    @RequestMapping("updateNameListStatus")
    public Result updateNameListStatus(Integer nameId, Integer status) {

        try {

            NameList nameList = nameListService.queryNameObj(nameId);
            nameList.setStatus(status);
            nameList.setGmtModified(new Timestamp(new Date().getTime()));

            nameListService.updateNameList(nameList);

            return new Result();

        } catch (Exception e) {
            logger.error("updateNameListStatus exception", e);
            return new Result(false);
        }
    }

    @RequestMapping("sync")
    public void syncBlackList2ApiSystem() {

        logger.info("sync starting...");

        List<NameList> nameLists = nameListService.getSyncBlackList();

        if (nameLists != null) {
            logger.info("nameLists size:" + nameLists.size());
            for (NameList record : nameLists) {
                try {
                    apiService.saveBlackList(record);
                    record.setStatus(NameListStatusType.SYNC_SUCCESS.getCode());
                    nameListService.updateNameListStatus(record);

                } catch (Exception e) {
                    logger.error("同步到API主库异常：", e);
                    record.setStatus(NameListStatusType.SYNC_FAIL.getCode());
                    nameListService.updateNameListStatus(record);
                }
            }
        }
        logger.info("sync end...");
    }

}
