package com.tongchuang.dao;

import com.tongchuang.model.ProvinceDetailInfo;
import com.tongchuang.model.ProvinceImgModel;
import com.tongchuang.model.ProvinceModel;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface MapDao {
    ArrayList<ProvinceModel> loadProvinceRoughInfo();

    ArrayList<String> loadUsernamesByProvinceId(@Param("provin_id") int provin_id);

    ProvinceDetailInfo loadProvinceDetailInfo(@Param("provin_name")String provin_name);

    ArrayList<ProvinceImgModel> loadProvinceImgByProvinceId(@Param("provin_id") int provin_id) ;

    ArrayList<String> loadPKsByProvinceId(@Param("provin_id") int provin_id);

    int saveOrUpdateProvinceInfo(@Param("provin_id") int provin_id,@Param("introduce") String introduce,@Param("scenic") String scenic);

}
