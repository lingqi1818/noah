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
import com.fangcloud.noah.dao.entity.ActionScriptEntity;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.ActionScriptService;

/**
 * Created by chenke on 16-8-23.
 */
@Controller
@RequestMapping("actionScript")
public class ActionScriptController {

    private static final Logger logger = LoggerFactory.getLogger(ActionScriptController.class);

    @Autowired
    private ActionScriptService actionScriptService;

    @RequestMapping("query")
    public String query(Integer pageNum, String type,String name, HttpServletRequest request) {

        PageObject<ActionScriptEntity> pageObj = actionScriptService.queryActionScripts(pageNum,type,name);

        request.setAttribute("pageObj",pageObj);
        request.setAttribute("type",type);
        request.setAttribute("name",name);
        return "actionScript/list";
    }


    /**
     * 新增数据
     * @param name
     * @param type
     * @param scriptContent
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Result addListData(String name,Integer type,String scriptContent,String remark) {

        if (type == null || StringUtils.isBlank(name) || StringUtils.isBlank(scriptContent)) {
            return new Result(false);
        }


        try {
            ActionScriptEntity entity = new ActionScriptEntity();
            entity.setName(name);
            entity.setType(type);
            entity.setScriptContent(scriptContent);
            entity.setRemark(remark);
            entity.setCreateTime(new Timestamp(new Date().getTime()));
            actionScriptService.saveActionScript(entity);
            return new Result();
        } catch (Exception e) {
            logger.error("add action script exception", e);
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
    public Result deleteActionScript(Integer id) {

        ActionScriptEntity entity = actionScriptService.queryActionScriptById(id);

        if(entity==null){
            return new Result(false).setApiMessage("不存在要删除的记录!");
        }

        actionScriptService.deleteActionScriptById(id);
        return new Result();
    }


    @RequestMapping("loadAll")
    @ResponseBody
    public Result loadAll() {

        List<ActionScriptEntity> actionScriptEntityList = actionScriptService.queryAllActionScripts();

        return new Result(true).setData(actionScriptEntityList);
    }

}
