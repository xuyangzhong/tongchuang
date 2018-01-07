package com.tongchuang.service;

import com.tongchuang.customException.NewsOperationException;
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
            for (RevertModel revert : reverts) {
                revertOwner = userDao.loadUserInfo(revert.getSenderPK());
                revertOwners.add(revertOwner);
                //若不是一级评论
                if (revert.getReceivePK() != null) {
                    revertOwner = userDao.loadUserInfo(revert.getReceivePK());
                    revertOwners.add(revertOwner);
                }
            }
            newsShow.setRevertOwnerLists(revertOwners);

            newsShowLists.add(newsShow);
        }
        return newsShowLists;
    }

    public void addNews(NewsModel news) {
        newsDao.addNews(news.getTitle(), news.getContent(),
                news.getZan_num(), news.getCreateTime(), news.getOwner_pk());
        log.info(String.format("the pk %s create news", news.getOwner_pk()));
    }

    public void deleteNews(int news_id) {
        newsDao.deleteNews(news_id);
        log.info(String.format("delete the news successly, news_id is %d", news_id));

        //删除动态下的评论
        ArrayList<RevertModel> reverts = newsDao.loadRevertsByNewsId(news_id);
        for (RevertModel revert : reverts) {
            deleteRevert(revert.getId());
        }
    }

    public void doZan(ZanModel zan) {
        checkDeleteInZan(zan);
        ZanModel tempZan = newsDao.hasZan(zan.getNews_id(), zan.getOwner_pk());
        if (tempZan != null) {
            log.error(String.format("the zan is existent , news_id is %d and owner_pk is %s", zan.getNews_id(), zan.getOwner_pk()));
            return;
        }
        newsDao.addZan(zan.getNews_id(), zan.getOwner_pk());
        log.info(String.format("the pk %s do zan , news_id is %d", zan.getOwner_pk(), zan.getNews_id()));
    }

    public void undoZan(ZanModel zan) {
        ZanModel tempZan = newsDao.hasZan(zan.getNews_id(), zan.getOwner_pk());
        if (tempZan == null) {
            log.error(String.format("the zan is non-existent , news_id is %d and owner_pk is %s", zan.getNews_id(), zan.getOwner_pk()));
            throw new NewsOperationException("no_find_zan");
        }
        newsDao.deleteZan(tempZan.getId());
        log.info(String.format("the pk %s undo zan , news_id is %d", zan.getOwner_pk(), zan.getNews_id()));
    }

    public void addRevert(RevertModel revert) {
        checkDeleteInRevert(revert);
        newsDao.addRevert(revert.getNews_id(), revert.getContent(),
                revert.getParent_root(), revert.getRevert_id(),
                revert.getRevertTime(), revert.getSenderPK(),
                revert.getReceivePK());
        log.info(String.format("the pk %s revert , news_id is %d", revert.getSenderPK(), revert.getNews_id()));
    }

    public void deleteRevert(int revert_id) {
        RevertModel revert = newsDao.loadRevertByRevertId(revert_id);
        if (revert == null) {
            log.error(String.format("the revert is non-existent , revert_id is %d", revert.getId()));
            throw new NewsOperationException("no_find_revert");
        }
        newsDao.deleteRevert(revert_id);
        log.info(String.format("delete the pk %s revert successly, revert_id is %d", revert.getSenderPK(), revert_id));

        cascadeDelete(revert_id);
    }

    //级联删除评论的评论
    private void cascadeDelete(int revert_id) {
        ArrayList<RevertModel> reverts = newsDao.loadRevertsByRevertId(revert_id);
        if (reverts == null)
            return;
        for (RevertModel revert : reverts) {
            newsDao.deleteRevert(revert.getId());
            cascadeDelete(revert.getId());
        }
    }

    //检查评论对象是否删除
    private void checkDeleteInRevert(RevertModel revert) {
        if (revert.getParent_root() == 0) {
            NewsModel news = newsDao.loadNewsByNewsId(revert.getNews_id());
            if (news == null) {
                log.error("要评论的动态不存在,news_id is " + revert.getNews_id());
                throw new NewsOperationException("no_find_news");
            }
        } else {
            RevertModel revertTemp = newsDao.loadRevertByRevertId(revert.getRevert_id());
            if (revertTemp == null) {
                log.error("要评论的评论不存在,revert_id is " + revert.getRevert_id());
                throw new NewsOperationException("no_find_revert");
            }
        }
    }

    private void checkDeleteInZan(ZanModel zan){
        NewsModel news = newsDao.loadNewsByNewsId(zan.getNews_id());
        if (news == null) {
            log.error("要赞的动态不存在,news_id is " + zan.getNews_id());
            throw new NewsOperationException("no_find_news");
        }
    }
}
