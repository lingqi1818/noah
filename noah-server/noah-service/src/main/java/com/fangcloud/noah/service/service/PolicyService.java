package com.fangcloud.noah.service.service;

import com.fangcloud.noah.dao.entity.PolicyEntity;
import com.fangcloud.noah.dao.mapper.PolicyMapper;
import com.fangcloud.noah.service.common.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by chenke on 16-5-6.
 */
@Service
public class PolicyService {

    @Autowired
    private PolicyMapper policyMapper;


    public PageObject<PolicyEntity> queryPolicyList(Integer pageNum, String deviceType) {

        if (pageNum == null) {
            pageNum = 1;
        }

        int total = policyMapper.selectPolicyCount(deviceType);

        PageObject<PolicyEntity> datas = new PageObject<PolicyEntity>(pageNum, total);

        List<PolicyEntity> result = policyMapper.selectPolicyList(datas.getOffSet(), datas.getPageSize(), deviceType);

        datas.initViewData(result);

        return datas;
    }


    public PolicyEntity queryPolicyById(Integer id) {

        return policyMapper.selectById(id);

    }

    public void addPolicy(PolicyEntity policy) {
        policyMapper.insert(policy);
    }

    public List<PolicyEntity> loadAllPolicy() {
        return policyMapper.loadAllPolicy();
    }

    public PolicyEntity queryPolicyByEventTypeAndDeviceType(String eventType, Integer deviceType) {

        return policyMapper.selectPolicyByEventTypeAndDeviceType(eventType, deviceType);
    }

    public void deletePolicy(PolicyEntity policyEntity) {
        policyMapper.deletePolicyById(policyEntity.getId());
    }

    public void updatePolicy(PolicyEntity policy) {

        PolicyEntity policyEntity = policyMapper.selectById(policy.getId());

        if(policyEntity!=null){
            policyEntity.setName(policy.getName());
            policyEntity.setEventType(policy.getEventType());
            policyEntity.setEventId(policy.getEventId());
            policyEntity.setDeviceType(policy.getDeviceType());
            policyEntity.setMinRisk(policy.getMinRisk());
            policyEntity.setMaxRisk(policy.getMaxRisk());
            policyEntity.setRemark(policy.getRemark());
            policyEntity.setActionName(policy.getActionName());

            policyMapper.updatePolicyById(policyEntity);
        }

    }
}
