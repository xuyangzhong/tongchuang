package com.tongchuang.controller;

import com.tongchuang.model.NewsShowModel;
import com.tongchuang.model.RevertModel;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
        try {
            newsService.doZan(zan);
            return true;
        }catch(Exception e){
            log.error("doZan fail",e);
            return false;
        }

    }

    @RequestMapping(value = "/undozan")
    @ResponseBody
    public boolean undoZan(HttpServletRequest request, HttpServletResponse response) {
        String owner_pk = request.getParameter("user_pk");
        int news_id = Integer.valueOf(request.getParameter("news_id"));
        ZanModel zan = new ZanModel();
        zan.setNews_id(news_id);
        zan.setOwner_pk(owner_pk);
        try {
            newsService.undoZan(zan);
            return true;
        }catch(Exception e){
            log.error("undoZan fail",e);
            return false;
        }
    }

    @RequestMapping(value = "/doRevert")
    @ResponseBody
    public boolean doRevert(HttpServletRequest request, HttpServletResponse response) {
        int news_id = Integer.valueOf(request.getParameter("news_id"));
        String content = request.getParameter("content");
        int parent_root = Integer.valueOf(request.getParameter("parent_root"));
        int revert_id;
        if(parent_root == 0){
            revert_id = -1;
        }else {
            revert_id = Integer.valueOf(request.getParameter("revert_id"));
        }
        String senderPK = request.getParameter("senderPK");
        String receivePK = request.getParameter("receivePK");
        Timestamp revertTime = new Timestamp(new Date().getTime());
        if(parent_root == 0){
            revert_id = -1;
        }

        RevertModel revert = new RevertModel();
        revert.setNews_id(news_id);
        revert.setContent(content);
        revert.setParent_root(parent_root);
        revert.setRevertTime(revertTime);
        revert.setRevert_id(revert_id);
        revert.setSenderPK(senderPK);
        revert.setReceivePK(receivePK);

        try {
            newsService.addRevert(revert);
            return true;
        }catch(Exception e){
            log.error("doRevert fail",e);
            return false;
        }
    }

    @RequestMapping(value = "/removeRevert")
    @ResponseBody
    public boolean removeRevert(HttpServletRequest request, HttpServletResponse response){
        int revert_id = Integer.valueOf(request.getParameter("revert_id"));
        try {
            newsService.deleteRevert(revert_id);
            return true;
        }catch(Exception e){
            log.error("removeRevert fail",e);
            return false;
        }
    }

}
