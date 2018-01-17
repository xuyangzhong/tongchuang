package com.tongchuang.task;

import com.tongchuang.utils.ApplicationContextUtil;
import com.tongchuang.dao.NewsDao;
import com.tongchuang.model.NewsModel;
import com.tongchuang.netResource.NewsPullResource;
import lombok.Setter;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 * @Description: 获取新闻的执行任务
 * @Author: Yeliang
 * @Date: Create in 5:16 2018/1/10
 */
public class GetNewsTask extends TimerTask {
    private NewsPullResource newsPullResource;

    @Setter
    private NewsDao newsDao;

    public GetNewsTask(ApplicationContext applicationContext) {
        super();
        newsDao = (NewsDao) ApplicationContextUtil.getBean(applicationContext,"newsDao");
        newsPullResource = NewsPullResource.getInstance();
    }


    @Override
    public void run() {
        System.out.println("the task is run @ " + new Date());
        ArrayList<NewsModel> newslist = newsPullResource.pullNews();
        if(newslist == null){
            return;
        }
        for (NewsModel news : newslist) {
            newsDao.saveNews(news.getNews_id(), news.getTitle(), news.getSource(), news.getGmt_publish(), news.getUrl());
        }
    }
}
