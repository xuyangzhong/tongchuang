package com.tongchuang.service;

import com.tongchuang.dao.UserDao;
import com.tongchuang.model.UserInfoModel;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
