package com.fangcloud.noah.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.RuleEntity;

import java.util.List;

/**
 * Created by chenke on 16-8-20.
 */
@Repository
public interface RuleMapper {

    int insert(RuleEntity rule);


    List<RuleEntity> selectRuleByPolicyId(@Param("policyId") Integer policyId);

    List<RuleEntity> loadAllRule();

    RuleEntity selectRuleById(Integer ruleId);

    void updateRule(RuleEntity rule);

    void deleteRuleById(Integer id);
}
