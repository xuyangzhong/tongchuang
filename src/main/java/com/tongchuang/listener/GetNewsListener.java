package com.tongchuang.listener;

import com.tongchuang.timer.GetNewsTimer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description: 定时获取新闻的监听器
 * @Author: Yeliang
 * @Date: Create in 4:52 2018/1/10
 */
public class GetNewsListener implements ServletContextListener{
    private GetNewsTimer timer = new GetNewsTimer();

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        timer.startTimer(servletContextEvent);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        timer.stopTimer();
    }
}
