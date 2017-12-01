package com.tongchuang.dao;

import com.tongchuang.model.UserLoginModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserDao {
    UserLoginModel checkUserLogin(@Param("pk") String pk, @Param("password") String password);
}
