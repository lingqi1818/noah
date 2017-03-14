package com.helijia.risk.ruleengine;


import junit.framework.TestCase;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;

import com.fangcloud.noah.ruleengine.DefaultRuleEngine;
import com.fangcloud.noah.ruleengine.RuleEngine;
import com.fangcloud.noah.ruleengine.bean.AttributeExtractor;
import com.fangcloud.noah.ruleengine.bean.DefaultAttributeExtractor;
import com.fangcloud.noah.ruleengine.expression.IKExpressionEvaluator;
import com.fangcloud.noah.ruleengine.expression.VelocityExpressionEvaluator;
import com.fangcloud.noah.ruleengine.rule.Rule;
import com.fangcloud.noah.ruleengine.rule.action.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则引擎测试类
 * 
 * @author chenke
 * @date 2016年1月22日 下午2:15:00
 */
public class TestRuleEngine extends TestCase {
    private RuleEngine         engine;
    private AttributeExtractor extractor;
    private Test               t;

    public static class Test {
        private String a;
        private TestB  testb;

        public TestB getTestb() {
            return testb;
        }

        public void setTestb(TestB testb) {
            this.testb = testb;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

    }

    public static class TestB {
        private String b;

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

    }

    public void setUp() {
        t = new Test();
        t.setA("123");
        TestB tb = new TestB();
        tb.setB("456");
        t.setTestb(tb);
        engine = new DefaultRuleEngine();
        extractor = new DefaultAttributeExtractor();
    }

    public void testExp() {
        List<Variable> variables = new ArrayList<Variable>();
        variables.add(Variable.createVariable("a", 2));
        variables.add(Variable.createVariable("b", "你好"));
        Boolean result = (Boolean) ExpressionEvaluator.evaluate("(a/2==1||a==1)&&a==2&&b==\"你好\"",
                variables);
        assertTrue(result);
    }

//    public void testExecRule() {
//        Action action = new Action() {
//            @Override
//            public void execute(Map<String, Object> contextMap) {
//                System.out.println("Execute Action");
//            }
//        };
//        Rule rule = new Rule("${risk.testb.b}==\"456\"", action);
//        engine.putDomainToContext("risk", t);
//        engine.execute(rule);
//        assertTrue(true);
//    }


//    public void testExecRule2() {
//        Action action = new Action() {
//            @Override
//            public void execute(Map<String, Object> contextMap,String description) {
//                System.out.println("Execute Action");
//            }
//        };
//        Rule rule = new Rule("id","test","device_id==\"456\"", action);
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("device_id","456");
//        engine.putDomainToContext(map);
//        Object object = engine.execute(rule);
//        System.out.println(object);
//        assertTrue(true);
//    }

    public void testIKExpressionEvaluator() {
        com.fangcloud.noah.ruleengine.expression.ExpressionEvaluator evaluator = new IKExpressionEvaluator();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("risk", t);
        Boolean res = (Boolean) evaluator.evaluate("${risk.testb.b}==\"456\"", params);
        assertTrue(res);
        Boolean res1 = (Boolean) evaluator.evaluate("${risk.a}==\"123\" ", params);
        assertTrue(res1);
    }

    public void testGetValByKey() {
        assertEquals("123", extractor.extractAttribute(t, "a", "a_a"));
        assertEquals("456", extractor.extractAttribute(t, "b", "b_testb_b"));
    }

    public void testVelocityExpressionEvaluator() {
        com.fangcloud.noah.ruleengine.expression.ExpressionEvaluator evaluator = new VelocityExpressionEvaluator();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("risk", t);
        String res = (String) evaluator.evaluate("#if(${risk.testb.b}==\"456\")true#end", params);
        assertEquals(res, "true");
    }

}
