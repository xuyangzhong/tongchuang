package com.tongchuang.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserMapModel {
    private ProvinceModel provinceModel;
    private int user_num;
    private ArrayList<String> user_lists;
}
