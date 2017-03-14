package com.fangcloud.noah.ruleengine.expression;

import org.apache.commons.lang3.StringUtils;
import org.wltea.expression.datameta.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenke on 16-8-26.
 */
public class DefaultExpressionEvaluator implements ExpressionEvaluator {
    @Override
    public Object evaluate(String expression, Map<String, Object> paramValues) {

        if (StringUtils.isEmpty(expression)) {
            throw new IllegalArgumentException("expression is null .");
        }
        List<Variable> variables = new ArrayList<Variable>();

        if (paramValues != null) {
            Set<Map.Entry<String, Object>> paramValueEntry = paramValues.entrySet();
            for (Map.Entry<String, Object> entry : paramValueEntry) {
                Variable v = Variable.createVariable(entry.getKey(), entry.getValue());
                variables.add(v);
            }
        }

        return org.wltea.expression.ExpressionEvaluator.evaluate(expression, variables);
    }
}
