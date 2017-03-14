package com.fangcloud.noah.ruleengine.expression;

import java.util.Map;

/**
 * 表达式计算器
 *
 * @author chenke
 * @date 2016年1月26日 下午2:10:23
 */
public interface ExpressionEvaluator {
    public Object evaluate(String expression, Map<String, Object> paramValues);
}
