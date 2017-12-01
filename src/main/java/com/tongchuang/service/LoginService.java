package com.tongchuang.service;

import com.tongchuang.dao.UserDao;
import com.tongchuang.model.UserLoginModel;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {

    public static final Logger log = LoggerFactory.getLogger(LoginService.class);


    @Setter
    private UserDao userDao;

    public UserLoginModel checkLoginValidity(String pk,String password){
        UserLoginModel userLoginModel = userDao.checkUserLogin(pk,password);
        return userLoginModel;
    }

}
