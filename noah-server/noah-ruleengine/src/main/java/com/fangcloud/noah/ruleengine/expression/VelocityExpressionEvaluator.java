package com.fangcloud.noah.ruleengine.expression;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

public class VelocityExpressionEvaluator implements ExpressionEvaluator {
    private VelocityEngine ve;

    public VelocityExpressionEvaluator() {
        ve = new VelocityEngine();
    }


    public Object evaluate(String expression, Map<String, Object> paramValues) {
        VelocityContext context = new VelocityContext();
        if (paramValues != null) {
            for (String key : paramValues.keySet()) {
                context.put(key, paramValues.get(key));
            }
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(bos);
            ve.evaluate(context, out, "VelocityExpressionEvaluator", expression);
            out.flush();
            return bos.toString();
        } catch (Exception e) {
            throw new RuntimeException("evaluate expression failed,expression:" + expression, e);
        }
    }

}
