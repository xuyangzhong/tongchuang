package com.tongchuang.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 16:36 2018/1/10
 */

@Data
public class NewsModel {
    private int id;
    private String news_id;
    private String title;
    private String source;
    private Timestamp gmt_publish;
    private String url;

    @Override
    public String toString() {
        return "NewsModel{" +
                "id=" + id +
                ", news_id='" + news_id + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", gmt_publish=" + gmt_publish +
                ", url='" + url + '\'' +
                '}';
    }
}
