package com.tongchuang.dao;

import com.tongchuang.model.NewsModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 16:56 2018/1/10
 */

@MapperScan
public interface NewsDao {
    int saveNews(@Param("news_id")String news_id, @Param("title")String title,
                 @Param("source")String source, @Param("gmt_publish")Timestamp gmt_publish,
                 @Param("url")String url);

    ArrayList<NewsModel> loadAllNews(@Param("index") int index, @Param("size") int size);
}
