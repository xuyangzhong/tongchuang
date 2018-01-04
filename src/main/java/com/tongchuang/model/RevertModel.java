package com.tongchuang.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 15:38 2018/1/2
 */
@Data
public class RevertModel {
    private int id;
    private int news_id;
    private String content;
    private int parent_root;
    private int revert_id;
    private Timestamp revertTime;
    private String senderPK;
    private String receivePK;
}
