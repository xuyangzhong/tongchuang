package com.tongchuang.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 20:59 2018/1/8
 */
@Data
public class UserVisitModel {
    private int id;
    private String pk;
    private String visitor_pk;
    private Timestamp visitTime;
}
