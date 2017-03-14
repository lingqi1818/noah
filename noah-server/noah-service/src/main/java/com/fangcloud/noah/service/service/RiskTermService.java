package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.RiskTermEntity;
import com.fangcloud.noah.dao.mapper.RiskTermMapper;
import com.fangcloud.noah.service.common.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenke on 16-9-24.
 */
@Service
public class RiskTermService {

    @Autowired
    private RiskTermMapper riskTermMapper;

    public void saveRiskTerm(RiskTermEntity record) {
        riskTermMapper.insert(record);
    }

    public PageObject<RiskTermEntity> queryRiskTermList(Integer pageNum,  String word) {
        int totalItem = riskTermMapper.selectRiskTermListCount(word);
        if (pageNum == null) {
            pageNum = 1;
        }
        PageObject<RiskTermEntity> datas = new PageObject<RiskTermEntity>(pageNum, totalItem);
        List<RiskTermEntity> rst = riskTermMapper.selectRiskTermList(datas.getOffSet(), datas.getPageSize(), word);
        datas.initViewData(rst);
        return datas;
    }


    public RiskTermEntity queryRiskTerm(Integer id){
        return riskTermMapper.selectById(id);
    }

    public void updateRiskTerm(RiskTermEntity entity){
        riskTermMapper.updateById(entity);
    }

    public void deleteRiskTerm(Integer id){
        riskTermMapper.deleteById(id);
    }

    public void updateStatus(RiskTermEntity record) {

        riskTermMapper.updateStatus(record);
    }

}
