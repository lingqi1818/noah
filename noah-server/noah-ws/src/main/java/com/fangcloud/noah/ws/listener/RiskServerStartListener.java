package com.fangcloud.noah.ws.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fangcloud.noah.ws.server.RiskServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by chenke on  Date: 16-1-15
 */
public class RiskServerStartListener implements ServletContextListener {

    private RiskServer riskServer;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        System.out.println("Server starting...");

        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:/spring/spring-context-ws.xml");
        riskServer = (RiskServer) ac.getBean("riskServer");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                riskServer.start();
            }
        }).start();

        System.out.println("Server start End...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        riskServer.stop();
        System.out.println("Server shutdown success...");
    }
}
