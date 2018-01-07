package com.tongchuang.dao;

import com.tongchuang.model.NewsModel;
import com.tongchuang.model.RevertModel;
import com.tongchuang.model.ZanModel;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:25 2018/1/2
 */
public interface NewsDao {
    ArrayList<NewsModel> getAllNews();

    ArrayList<ZanModel> loadZanPksByNewsId(@Param("news_id")int news_id);

    ArrayList<RevertModel> loadRevertsByNewsId(@Param("news_id")int news_id);

    NewsModel loadNewsByNewsId(@Param("id")int news_id);

    RevertModel loadRevertByRevertId(@Param("id")int revert_id);

    int addNews(@Param("title")String title,@Param("content")String content,
                @Param("zan_num")int zan_num,@Param("createTime")Timestamp createTime,
                @Param("owner_pk")String owner_pk);

    int deleteNews(@Param("id")int id);

    int addZan(@Param("news_id")int news_id,@Param("owner_pk")String owner_pk);

    ZanModel hasZan(@Param("news_id")int news_id,@Param("owner_pk")String owner_pk);

    int deleteZan(@Param("id")int zan_id);

    int addRevert(@Param("news_id")int news_id, @Param("content")String content, @Param("parent_root")int parent_root,
                  @Param("revert_id")int revert_id, @Param("revertTime")Timestamp revertTime, @Param("senderPK")String senderPK,
                  @Param("receivePK")String receivePK);

    int deleteRevert(@Param("id")int revert_id);

    ArrayList<RevertModel> loadRevertsByRevertId(@Param("revert_id")int revert_id);
}
