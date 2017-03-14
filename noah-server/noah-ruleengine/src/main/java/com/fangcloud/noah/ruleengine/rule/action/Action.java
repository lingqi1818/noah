package com.fangcloud.noah.ruleengine.rule.action;

import java.util.Map;

/**
 * Created by chenke on 16-8-20.
 */
public interface Action {

    public void execute(Map<String, Object> contextMap,String description);

}
