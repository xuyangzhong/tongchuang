package com.tongchuang.controller;

import com.tongchuang.dao.NewsDao;
import com.tongchuang.model.NewsModel;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 18:05 2018/1/10
 */
@Controller
@RequestMapping(value = "/news")
public class NewsController {
    private static final Logger log = LoggerFactory.getLogger(NewsController.class);

    //默认查询10条
    private static int DEFAULT_SIZE = 10;

    private static int DEFAULT_INDEX = 0;

    @Setter
    private NewsDao newsDao;

    @RequestMapping(value = "/newslist")
    @ResponseBody
    public ArrayList<NewsModel> showNewsLists(HttpServletRequest request, HttpServletResponse response){
        String index_str = request.getParameter("index");
        int index;
        //未设置查询开始标志，则从1开始
        if (index_str == null) {
            index = DEFAULT_INDEX;
        } else {
            index = Integer.valueOf(index_str);
        }
        String check_num_str = request.getParameter("size");
        int size;
        //未设置查询条数，则默认为10条
        if (check_num_str == null) {
            size = DEFAULT_SIZE;
        } else {
            size = Integer.valueOf(check_num_str);
        }
        //禁止一次查询超过50条
        if (size > 50) {
            size = DEFAULT_SIZE;
        }
        ArrayList<NewsModel> newslist = newsDao.loadAllNews(index, size);
        return newslist;
    }
}
