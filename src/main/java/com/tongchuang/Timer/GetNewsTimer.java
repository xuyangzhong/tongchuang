package com.tongchuang.Timer;

import com.tongchuang.Task.GetNewsTask;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

/**
 * @Description: 获取新闻的定时器类
 * @Author: Yeliang
 * @Date: Create in 4:57 2018/1/10
 */
public class GetNewsTimer {
    private Timer timer;

    public void startTimer(ServletContextEvent servletContextEvent) {
        timer = new Timer();
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
//        try {
//            date = sdf.parse("2018-01-10");
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
        gc.setTime(date);
        System.out.println(date);
        //每1000 * 60 * 60 * 24毫秒即每天0:00执行一次
        timer.schedule(new GetNewsTask(WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext())),
                gc.getTime(), 1000 * 60 * 60 * 24);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        System.out.println("GetNewsTimer end");
    }
}
