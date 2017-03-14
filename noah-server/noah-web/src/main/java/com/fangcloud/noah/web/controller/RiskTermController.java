package com.fangcloud.noah.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangcloud.noah.api.api.enums.RiskWordType;
import com.fangcloud.noah.common.api.util.Result;
import com.fangcloud.noah.dao.entity.RiskTermEntity;
import com.fangcloud.noah.service.common.PageObject;
import com.fangcloud.noah.service.service.RiskTermService;

/**
 * 危险词管理 Created by chenke on 16-8-20.
 */
@Controller
@RequestMapping("riskTerm")
public class RiskTermController {

    private static final Logger logger = LoggerFactory.getLogger(RiskTermController.class);

    @Autowired
    private RiskTermService     riskTermService;

    /**
     * 查询危险词
     * 
     * @param pageNum
     * @param word
     * @param req
     * @return
     */
    @RequestMapping("query")
    public String query(Integer pageNum, String word, HttpServletRequest req) {

        PageObject<RiskTermEntity> pageObj = riskTermService.queryRiskTermList(pageNum, word);

        RiskWordType[] riskWordTypes = RiskWordType.values();

        Map<String, String> riskWordTypeMap = new HashMap<String, String>();
        for (RiskWordType w : riskWordTypes) {
            riskWordTypeMap.put(w.getCode(), w.getName());
        }

        if (pageObj != null) {
            List<RiskTermEntity> riskTermEntityList = pageObj.getDatas();

            for (RiskTermEntity entity : riskTermEntityList) {
                String wordType = entity.getWordType();
                if (StringUtils.isNotBlank(wordType)) {
                    String[] wordTypeArray = wordType.split(",");
                    List<String> wordTypeStrList = new ArrayList<String>();
                    for (String wt : wordTypeArray) {
                        wordTypeStrList.add(riskWordTypeMap.get(wt));
                    }

                    entity.setWordTypeStr(StringUtils.join(wordTypeStrList, ","));

                }
            }

        }

        req.setAttribute("word", word);
        req.setAttribute("pageObj", pageObj);
        req.setAttribute("riskwordTypes", riskWordTypes);

        return "term/list";
    }

    /**
     * 新增危险词
     * 
     * @param word
     * @param wholeWord
     * @param allowSkip
     * @param weight
     * @return
     */
    @ResponseBody
    @RequestMapping("add")
    public Result addRiskTerm(String word, String[] wordType, Integer wholeWord, Integer allowSkip,
                              Integer weight) {

        if (StringUtils.isBlank(word) || ArrayUtils.isEmpty(wordType)) {
            return new Result(false);
        }

        try {

            RiskTermEntity riskTermEntity = new RiskTermEntity();
            riskTermEntity.setWord(word);
            riskTermEntity.setWordType(StringUtils.join(wordType, ","));
            riskTermEntity.setWholeWord(wholeWord);
            riskTermEntity.setAllowSkip(allowSkip);
            riskTermEntity.setWeight(weight);
            riskTermEntity.setStatus(1);
            riskTermEntity.setCreateTime(new Timestamp(new Date().getTime()));
            riskTermEntity.setUpdateTime(riskTermEntity.getCreateTime());
            riskTermService.saveRiskTerm(riskTermEntity);
            return new Result();
        } catch (Exception e) {
            logger.error("add risk term exception", e);
            return new Result(false);
        }
    }

    /**
     * 跳转到修改页面
     * 
     * @param id
     * @return
     */
    @RequestMapping("showUpdate")
    @ResponseBody
    public Result showUpdate(Integer id) {
        RiskTermEntity entity = riskTermService.queryRiskTerm(id);

        if (entity != null) {
            return new Result(true).setData(entity);
        } else {
            return new Result(false).setApiMessage("不存在要修改的记录!");
        }
    }

    /**
     * 修改危险词
     * 
     * @param id
     * @param word
     * @param wordType
     * @param wholeWord
     * @param allowSkip
     * @param weight
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public Result updateRiskTerm(Integer id, String word, String[] wordType, Integer wholeWord,
                                 Integer allowSkip, Integer weight) {

        if (StringUtils.isBlank(word) || ArrayUtils.isEmpty(wordType)) {
            return new Result(false);
        }

        try {
            RiskTermEntity riskTermEntity = new RiskTermEntity();
            riskTermEntity.setId(id);
            riskTermEntity.setWord(word);
            riskTermEntity.setWordType(StringUtils.join(wordType, ","));
            riskTermEntity.setWholeWord(wholeWord);
            riskTermEntity.setAllowSkip(allowSkip);
            riskTermEntity.setWeight(weight);
            riskTermEntity.setStatus(1);
            riskTermEntity.setUpdateTime(new Timestamp(new Date().getTime()));

            riskTermService.updateRiskTerm(riskTermEntity);
            return new Result();
        } catch (Exception e) {
            logger.error("修改危险词异常：", e);
            return new Result(false).setApiMessage("修改危险词异常!");
        }

    }

    /**
     * 物理删除危险词
     *
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Result deleteRiskTerm(Integer id) {

        RiskTermEntity entity = riskTermService.queryRiskTerm(id);

        if (entity == null) {
            return new Result(false).setApiMessage("不存在要删除的记录!");
        }

        riskTermService.deleteRiskTerm(id);

        return new Result();
    }

    /**
     * 设置危险词有效无效状态
     * 
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("updateStatus")
    public Result updateStatus(Integer id, Integer status) {

        try {

            RiskTermEntity riskTermEntity = new RiskTermEntity();
            riskTermEntity.setId(id);
            status = status == 1 ? 0 : 1;
            riskTermEntity.setStatus(status);
            riskTermService.updateStatus(riskTermEntity);

            return new Result();

        } catch (Exception e) {
            logger.error("update risk termStatus exception", e);
            return new Result(false);
        }
    }

}
