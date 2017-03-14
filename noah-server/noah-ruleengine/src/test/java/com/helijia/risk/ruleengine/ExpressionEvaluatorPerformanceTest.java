package com.helijia.risk.ruleengine;

import com.fangcloud.noah.ruleengine.expression.IKExpressionEvaluator;
import com.fangcloud.noah.ruleengine.expression.VelocityExpressionEvaluator;
import com.helijia.risk.ruleengine.TestRuleEngine.Test;
import com.helijia.risk.ruleengine.TestRuleEngine.TestB;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * ik-expression && velocity性能测试
 * 
 * @author chenke
 * @date 2016年1月25日 下午4:44:38
 */
public class ExpressionEvaluatorPerformanceTest {
    private static int                         THREAD_NUM        = 50;
    private static IKExpressionEvaluator ikEvaluator       = new IKExpressionEvaluator();
    private static VelocityExpressionEvaluator velocityEvaluator = new VelocityExpressionEvaluator();
    private static CountDownLatch              latch1            = new CountDownLatch(THREAD_NUM);
    private static CountDownLatch              latch2            = new CountDownLatch(THREAD_NUM);

    private static TestRuleEngine.Test creatTest() {
        Test t = new Test();
        t.setA("123");
        TestB tb = new TestB();
        tb.setB("456");
        t.setTestb(tb);
        return t;
    }

    public static void main(String args[]) {
        try {
            mutiThreadTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void mutiThreadTest() throws Exception {

        new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                for (int i = 0; i < THREAD_NUM; i++) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("risk", creatTest());
                            for (int i = 0; i < 10000; i++) {
                                ikEvaluator.evaluate(
                                        "${risk.a}==\"123\" && ${risk.testb.b}==\"456\"", params);
                            }
                            latch1.countDown();
                        }
                    }).start();
                }
                try {
                    latch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ik:" + (System.currentTimeMillis() - start));
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                for (int i = 0; i < THREAD_NUM; i++) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            Map<String, Object> params1 = new HashMap<String, Object>();
                            params1.put("risk", creatTest());
                            for (int i = 0; i < 10000; i++) {
                                velocityEvaluator.evaluate("#if(${risk.a}==\"123\")true#end",
                                        params1);
                            }
                            latch2.countDown();
                        }
                    }).start();
                }
                try {
                    latch2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("velocity:" + (System.currentTimeMillis() - start));
            }
        }).start();

    }

}
