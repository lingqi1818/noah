package com.fangcloud.noah.ruleengine;


import java.util.Map;

import com.fangcloud.noah.ruleengine.rule.Rule;

/**
 * <pre>
 * 通用规则引擎
 *
 * 为方便维护和管理，目前规则的实现主要针对返回值为:True|False的条件表达式
 *
 * 当规则满足之后，可以执行相应的<tt>Action</tt>
 * </pre>
 *
 * @author chenke
 * @date 2016年1月22日 下午2:22:08
 */
public interface RuleEngine {


    /**
     * 执行指定的规则
     *
     * @param rule
     */
    public Object execute(Rule rule);

    /**
     * 将域对象丢入引擎上下文中
     *
     * @param key
     * @param val
     */
    public void putDomainToContext(String key, Object val);


    public void putDomainToContext(Map<String, Object> paramsMap);

}
