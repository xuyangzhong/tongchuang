package com.tongchuang.model;

import lombok.Data;

@Data
public class UserInfoModel {
    private int id;
    private String pk;
    private String username;
    private int sex;
    private int power;
    private String profile_pic;
    private String introduce;
    private String industry;
}
