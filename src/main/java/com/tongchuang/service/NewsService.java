package com.tongchuang.service;

import com.tongchuang.dao.NewsDao;
import com.tongchuang.dao.UserDao;
import com.tongchuang.model.*;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:24 2018/1/2
 */
public class NewsService {
    public static final Logger log = LoggerFactory.getLogger(NewsService.class);

    @Setter
    private NewsDao newsDao;

    @Setter
    private UserDao userDao;

    public ArrayList<NewsShowModel> showAllNews() {
        ArrayList<NewsShowModel> newsShowLists = new ArrayList<NewsShowModel>();
        ArrayList<NewsModel> newsLists = newsDao.getAllNews();
        NewsShowModel newsShow;
        for (NewsModel news : newsLists) {
            newsShow = new NewsShowModel();
            String newsPk = news.getOwner_pk();
            UserInfoModel newsOwner = userDao.loadUserInfo(newsPk);
            newsShow.setNews(news);
            newsShow.setNewsOwner(newsOwner);
            int news_id = news.getId();


            //获取赞的用户信息
            ArrayList<UserInfoModel> zanOwners = new ArrayList<UserInfoModel>();
            ArrayList<ZanModel> zans = newsDao.loadZanPksByNewsId(news_id);
            newsShow.setZans(zans);
            if (zans != null && zans.size() != 0) {
                for (ZanModel zan : zans) {
                    String zanPK = zan.getOwner_pk();
                    UserInfoModel zanOwner = userDao.loadUserInfo(zanPK);
                    zanOwners.add(zanOwner);
                }
            }
            newsShow.setZanOwnerLists(zanOwners);


            //获取评论
            ArrayList<RevertModel> reverts = newsDao.loadRevertsByNewsId(news_id);
            newsShow.setReverts(reverts);

            //获取评论的用户信息
            ArrayList<UserInfoModel> revertOwners = new ArrayList<UserInfoModel>();
            UserInfoModel revertOwner;
            for(RevertModel revert : reverts){
                revertOwner = userDao.loadUserInfo(revert.getSenderPK());
                revertOwners.add(revertOwner);
                //若不是一级评论
                if(revert.getReceivePK()!=null){
                    revertOwner = userDao.loadUserInfo(revert.getReceivePK());
                    revertOwners.add(revertOwner);
                }
            }
            newsShow.setRevertOwnerLists(revertOwners);

            newsShowLists.add(newsShow);
        }
        return newsShowLists;
    }

    public boolean doZan(ZanModel zan){
        int count = newsDao.addZan(zan.getNews_id(),zan.getOwner_pk());
        return count != 0 ? true : false;
    }

    public boolean undoZan(ZanModel zan){
        ZanModel tempZan = newsDao.hasZan(zan.getNews_id(),zan.getOwner_pk());
        if(tempZan == null){
            log.error(String.format("the zan is non-existent , news_id is %s and owner_pk is %d",zan.getNews_id(),zan.getOwner_pk()));
            return false;
        }
        int count = newsDao.deleteZan(tempZan.getId());
        return count != 0 ? true : false;
    }
}
