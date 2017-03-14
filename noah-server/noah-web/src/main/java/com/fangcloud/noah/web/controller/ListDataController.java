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

import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.dao.entity.ListDataEntity;
import com.fangcloud.noah.dao.entity.ListTypeEntity;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.ListDataService;
import com.fangcloud.noah.service.service.ListTypeService;

/**
 * Created by chenke on 16-8-23.
 */
@Controller
@RequestMapping("listData")
public class ListDataController {

    private static final Logger logger = LoggerFactory.getLogger(ListDataController.class);

    @Autowired
    private ListTypeService     listTypeService;

    @Autowired
    private ListDataService     listDataService;

    @RequestMapping("query")
    public String query(Integer pageNum, String dataType, String dataValue,
                        HttpServletRequest request) {

        List<ListTypeEntity> listTypeEntityList = listTypeService.queryAllListType();

        PageObject<ListDataEntity> pageObj = listDataService.queryListDatas(pageNum, dataType,
                dataValue);

        request.setAttribute("listTypeEntityList", listTypeEntityList);
        request.setAttribute("pageObj", pageObj);
        request.setAttribute("dataType", dataType);
        request.setAttribute("dataValue", dataValue);
        return "listData/list";
    }

    /**
     * 新增数据
     * 
     * @param dataType
     * @param dataValue
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Result addListData(String dataType, String dataValue, String remark) {

        if (StringUtils.isBlank(dataType) || StringUtils.isBlank(dataValue)) {
            return new Result(false);
        }

        try {

            ListDataEntity entity = new ListDataEntity();
            entity.setDataType(dataType);
            entity.setDataValue(dataValue);
            entity.setRemark(remark);
            entity.setCreateTime(new Timestamp(new Date().getTime()));
            listDataService.saveListData(entity);
            return new Result();
        } catch (Exception e) {
            logger.error("add list data exception", e);
            return new Result(false);
        }
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Result deleteListData(Integer id) {

        ListDataEntity entity = listDataService.queryListDataById(id);

        if (entity == null) {
            return new Result(false).setApiMessage("不存在要删除的记录!");
        }

        listDataService.deleteListDataById(id);

        return new Result();
    }

}
