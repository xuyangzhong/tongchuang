package com.tongchuang.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:30 2018/1/2
 */
@Data
public class MessageShowModel {
    private MessageModel message;
    private ArrayList<RevertModel> reverts;
    private ArrayList<ZanModel> zans;

    private UserInfoModel messageOwner;
    private ArrayList<UserInfoModel> zanOwnerLists;
    private ArrayList<UserInfoModel> revertOwnerLists;
}
