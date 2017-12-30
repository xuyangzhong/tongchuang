package com.tongchuang.service;

import com.tongchuang.dao.MapDao;
import com.tongchuang.model.*;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MapService {
    public static final Logger log = LoggerFactory.getLogger(MapService.class);

    @Setter
    private MapDao mapDao;

    public ArrayList<UserMapModel> loadUserMapData() {
        ArrayList<ProvinceModel> provinceLists = mapDao.loadProvinceRoughInfo();
        int provin_id;
        ArrayList<String> user_lists;
        ArrayList<UserMapModel> userMapLists = new ArrayList<UserMapModel>();
        for (ProvinceModel province : provinceLists) {
            UserMapModel userMapModel = new UserMapModel();
            userMapModel.setProvinceModel(province);
            provin_id = province.getProvin_id();
            user_lists = mapDao.loadUsernamesByProvinceId(provin_id);
            if (user_lists == null) {
                user_lists = new ArrayList<String>();
            }
            userMapModel.setUser_lists(user_lists);
            userMapModel.setUser_num(user_lists.size());
            userMapLists.add(userMapModel);
        }

        return userMapLists;
    }

    public ProvinceDetailInfo getProvinDetailInfoByProvinId(int provin_id){
        return mapDao.loadProvinceDetailInfo(provin_id);
    }

    public ProvinceImgUrlsModel getProvinceImgUrlsByProvinceId(int provin_id){
        ArrayList<ProvinceImgModel> provinceImgs = mapDao.loadProvinceImgByProvinceId(provin_id);
        ProvinceImgUrlsModel imgUrls = new ProvinceImgUrlsModel();
        imgUrls.setProvin_id(provin_id);
        int slideImgNum = 0;
        int normalImgNum = 0;
        ArrayList<String> slideImgUrls = new ArrayList<String>();
        ArrayList<String> normalImgUrls = new ArrayList<String>();
        String imgUrl;
        for(ProvinceImgModel imgModel : provinceImgs){
            imgUrl = "img"+"_"+provin_id+"_"+imgModel.getId()+"."+imgModel.getSuffix();
            if(imgModel.getType() == 0){
                slideImgNum++;
                slideImgUrls.add(imgUrl);
            }else{
                normalImgNum++;
                normalImgUrls.add(imgUrl);
            }
        }
        imgUrls.setNormalImgNum(normalImgNum);
        imgUrls.setNormalImgUrls(normalImgUrls);
        imgUrls.setSlideImgNum(slideImgNum);
        imgUrls.setSlideImgUrls(slideImgUrls);
        return imgUrls;
    }
}






