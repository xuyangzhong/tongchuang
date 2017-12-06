package com.tongchuang.dao;

import com.tongchuang.model.ProvinceDetailInfo;
import com.tongchuang.model.ProvinceModel;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface MapDao {
    ArrayList<ProvinceModel> loadProvinceRoughInfo();

    ArrayList<String> loadUsernamesByProvinceId(@Param("provin_id") int provin_id);

    ProvinceDetailInfo loadProvinceDetailInfo(@Param("provin_id")int provin_id);
}
