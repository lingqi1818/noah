package com.fangcloud.noah.service.file;


import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangcloud.noah.ruleengine.rule.action.Action;
import com.fangcloud.noah.service.executor.RuleExecutor;

import groovy.lang.GroovyClassLoader;

/**
 * Created by chenke on 16-8-25.
 */
public class FileListener extends FileAlterationListenerAdaptor {

    private final Logger logger = LoggerFactory.getLogger(FileListener.class);

    private RuleExecutor ruleExecutor;

    private String actionType;

    public FileListener(RuleExecutor ruleExecutor, String actionType) {
        super();
        this.ruleExecutor = ruleExecutor;
        this.actionType = actionType;
    }

    @Override
    public void onFileCreate(File file) {

        System.out.println(file.getName());
        addGroovyClass(file);

    }

    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName());
        onFileCreate(file);
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println(file.getName());

        if (actionType.equals("action")) {
            ruleExecutor.removeRuleAction(file.getName());
        } else if (actionType.equals("policy")) {
            ruleExecutor.removePolicyAction(file.getName());
        }

    }

    public void addGroovyClass(File file) {

        ClassLoader parent = getClass().getClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);

        try {
            Class groovyClass = loader.parseClass(file);
            Action groovyAction = (Action) groovyClass.newInstance();
            if (actionType.equals("action")) {
                ruleExecutor.addRuleAction(file.getName(), groovyAction);
            } else if (actionType.equals("policy")) {
                ruleExecutor.addPolicyAction(file.getName(), groovyAction);
            }
        } catch (Exception e) {
            logger.error("加载action脚本异常", e);
        }
    }
}
