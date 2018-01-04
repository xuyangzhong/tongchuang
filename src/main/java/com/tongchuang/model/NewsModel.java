package com.tongchuang.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 15:36 2018/1/2
 */

@Data
public class NewsModel {
    private int id;
    private String title;
    private String content;
    private int zan_num;
    private Timestamp createTime;
    private String owner_pk;
}
