package com.tongchuang.dao;

import com.tongchuang.model.UserInfoModel;
import com.tongchuang.model.UserLoginModel;
import com.tongchuang.model.UserVisitModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.ArrayList;

@MapperScan
public interface UserDao {
    UserLoginModel checkUserLogin(@Param("pk") String pk, @Param("password") String password);

    UserInfoModel loadUserInfo(@Param("pk") String pk);

    ArrayList<UserVisitModel> loadUserVisitInfo(@Param("pk")String pk);

    int changeProfilePic(@Param("pk")String pk,@Param("profile_pic")String filename);
}
