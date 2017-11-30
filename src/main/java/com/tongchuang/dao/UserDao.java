package com.tongchuang.dao;

import com.tongchuang.model.UserLoginModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    public boolean checkUserLogin(UserLoginModel userLoginModel){
        if(userLoginModel.getPk().equals("123")&&userLoginModel.getPassword().equals("123")){
            return true;
        }
        return false;
    }

}
