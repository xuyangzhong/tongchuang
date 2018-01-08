package com.tongchuang.dao;

import com.tongchuang.model.UserInfoModel;
import com.tongchuang.model.UserLoginModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserDao {
    UserLoginModel checkUserLogin(@Param("pk") String pk, @Param("password") String password);

    UserInfoModel loadUserInfo(@Param("pk") String pk);

    int changeProfilePic(@Param("pk")String pk,@Param("profile_pic")String filename);
}
