package com.tongchuang.model;

import lombok.Data;

@Data
public class UserLoginModel {
    private int id;
    private String pk;
    private String username;
    private String password;
    private int power;
}
