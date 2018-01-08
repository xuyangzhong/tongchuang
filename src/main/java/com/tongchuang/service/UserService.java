package com.tongchuang.service;

import com.tongchuang.dao.UserDao;
import com.tongchuang.model.UserInfoModel;
import com.tongchuang.model.UserVisitModel;
import com.tongchuang.model.VisitShowModel;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Setter
    private UserDao userDao;

    public UserInfoModel getUserInfoByPK(String pk){
        return userDao.loadUserInfo(pk);
    }

    public void changeProfilePic(String pk,String fileName){
        userDao.changeProfilePic(pk,fileName);
    }

    public VisitShowModel getVisitInfo(String pk){
        ArrayList<UserVisitModel> userVisitInfos = userDao.loadUserVisitInfo(pk);
        ArrayList<UserInfoModel> visitorLists = new ArrayList<UserInfoModel>();

        if(userVisitInfos == null){
            return null;
        }

        for(UserVisitModel userVisit : userVisitInfos){
            UserInfoModel user = userDao.loadUserInfo(userVisit.getVisitor_pk());
            visitorLists.add(user);
        }

        VisitShowModel visitShow = new VisitShowModel();
        visitShow.setVisitInfoLists(userVisitInfos);
        visitShow.setVisitorLists(visitorLists);
        return visitShow;
    }
}
