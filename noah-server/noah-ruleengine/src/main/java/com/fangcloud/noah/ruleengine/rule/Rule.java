package com.fangcloud.noah.ruleengine.rule;

import com.fangcloud.noah.ruleengine.rule.action.Action;

/**
 * 代表规则引擎中的一个规则
 *
 * @author chenke
 * @date 2016年1月22日 下午2:33:21
 */
public class Rule {

    private String id;

    private String name;

    private String expression;


    public Rule(String id,String name,String expression) {
        this.id = id;
        this.name = name;
        this.expression = expression;
    }


    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
