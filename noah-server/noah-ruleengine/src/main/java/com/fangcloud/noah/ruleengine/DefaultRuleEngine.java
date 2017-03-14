package com.fangcloud.noah.ruleengine;


import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangcloud.noah.ruleengine.exception.RuleEngineRuntimeException;
import com.fangcloud.noah.ruleengine.expression.DefaultExpressionEvaluator;
import com.fangcloud.noah.ruleengine.expression.ExpressionEvaluator;
import com.fangcloud.noah.ruleengine.rule.Rule;
import com.fangcloud.noah.ruleengine.rule.action.Action;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 默认规则引擎实现
 *
 * @author chenke
 * @date 2016年1月22日 下午2:25:00
 */
public class DefaultRuleEngine implements RuleEngine {

    private static Logger logger = LoggerFactory.getLogger(DefaultRuleEngine.class);

    ExpressionEvaluator expressionEvaluator = new DefaultExpressionEvaluator();
    private Map<String, Object> contextMap = new HashMap<String, Object>();


    @Override
    public Object execute(final Rule rule) {

        try {
            Object result = expressionEvaluator.evaluate(rule.getExpression(), contextMap);

            return result;

        } catch (Exception e) {
            throw new RuleEngineRuntimeException(e);
        }
    }

    public void putDomainToContext(String key, Object val) {
        contextMap.put(key, val);
    }

    @Override
    public void putDomainToContext(Map<String, Object> paramsMap) {
        if (MapUtils.isNotEmpty(paramsMap)) {
            contextMap.putAll(paramsMap);
        }
    }

}
