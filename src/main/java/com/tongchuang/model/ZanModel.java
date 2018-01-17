package com.tongchuang.model;

import lombok.Data;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:31 2018/1/2
 */
@Data
public class ZanModel {
    private int id;
    private int message_id;
    private String owner_pk;
    private int isdelete;
}
