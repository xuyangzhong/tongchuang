package com.tongchuang.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 21:08 2018/1/8
 */

@Data
public class VisitShowModel {
    ArrayList<UserVisitModel> visitInfoLists;
    ArrayList<UserInfoModel> visitorLists;
}
