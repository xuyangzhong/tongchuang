package com.tongchuang.controller;

import com.tongchuang.model.NewsShowModel;
import com.tongchuang.model.ZanModel;
import com.tongchuang.service.NewsService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:22 2018/1/2
 */

@Controller
@RequestMapping(value = "/news")
public class NewsController {
    public static final Logger log = LoggerFactory.getLogger(NewsController.class);

    @Setter
    private NewsService newsService;

//    @RequestMapping(value = "/newslist")
//    @ResponseBody
//    public ModelAndView showNewsLists(){
//        ArrayList<NewsShowModel> newsShowLists = newsService.showAllNews();
//        ModelAndView mav = new ModelAndView();
//        mav.addObject()
//        return mav;
//    }

    @RequestMapping(value = "/newslist")
    @ResponseBody
    public ArrayList<NewsShowModel> showNewsLists() {
        ArrayList<NewsShowModel> newsShowLists = newsService.showAllNews();
        return newsShowLists;
    }

    @RequestMapping(value = "/dozan")
    @ResponseBody
    public boolean doZan(HttpServletRequest request, HttpServletResponse response) {
        String owner_pk = request.getParameter("user_pk");
        int news_id = Integer.valueOf(request.getParameter("news_id"));
        ZanModel zan = new ZanModel();
        zan.setNews_id(news_id);
        zan.setOwner_pk(owner_pk);
        return newsService.doZan(zan);
    }

    @RequestMapping(value = "/undozan")
    @ResponseBody
    public boolean undoZan(HttpServletRequest request, HttpServletResponse response) {
        String owner_pk = request.getParameter("user_pk");
        int news_id = Integer.valueOf(request.getParameter("news_id"));
        ZanModel zan = new ZanModel();
        zan.setNews_id(news_id);
        zan.setOwner_pk(owner_pk);
        return newsService.undoZan(zan);
    }

}
