package com.tongchuang.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:42 2018/1/10
 */
public class ApplicationContextUtil implements ApplicationContextAware {
    public void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(ApplicationContext applicationContext,String beanName) {
        return applicationContext.getBean(beanName);
    }
}

