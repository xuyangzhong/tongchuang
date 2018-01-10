package com.tongchuang.service;

import com.tongchuang.dao.MapDao;
import com.tongchuang.dao.UserDao;
import com.tongchuang.model.*;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MapService {
    private static final Logger log = LoggerFactory.getLogger(MapService.class);

    @Setter
    private MapDao mapDao;

    @Setter
    private UserDao userDao;

    public ArrayList<UserMapModel> loadUserMapData() {
        ArrayList<ProvinceModel> provinceLists = mapDao.loadProvinceRoughInfo();
        int provin_id;
        ArrayList<String> user_lists;
        ArrayList<UserMapModel> userMapLists = new ArrayList<UserMapModel>();
        for (ProvinceModel province : provinceLists) {
            String usernames = "";
            UserMapModel userMapModel = new UserMapModel();
            userMapModel.setProvinceModel(province);
            provin_id = province.getProvin_id();
            user_lists = mapDao.loadUsernamesByProvinceId(provin_id);
            if (user_lists == null) {
                user_lists = new ArrayList<String>();
            }
            for (String username : user_lists) {
                usernames += username;
            }
            if (usernames.length() != 0) {
                usernames = usernames.substring(1);
            }
            userMapModel.setUsernames(usernames);
            userMapModel.setUser_lists(user_lists);
            userMapModel.setUser_num(user_lists.size());
            userMapLists.add(userMapModel);
        }

        return userMapLists;
    }

    public ProvinceDetailInfo getProvinDetailInfoByProvinId(int provin_id) {
        return mapDao.loadProvinceDetailInfo(provin_id);
    }

    public ArrayList<UserInfoModel> getProvinUserByProvinId(int provin_id) {
        ArrayList<UserInfoModel> users = new ArrayList<UserInfoModel>();
        ArrayList<String> pks = mapDao.loadPKsByProvinceId(provin_id);
        if (pks == null) {
            return users;
        }
        for (String pk : pks) {
            UserInfoModel user = userDao.loadUserInfo(pk);
            users.add(user);
        }
        return users;
    }

    public ProvinceImgUrlsModel getProvinceImgUrlsByProvinceId(int provin_id) {
        ArrayList<ProvinceImgModel> provinceImgs = mapDao.loadProvinceImgByProvinceId(provin_id);
        ProvinceImgUrlsModel imgUrls = new ProvinceImgUrlsModel();
        imgUrls.setProvin_id(provin_id);
        int slideImgNum = 0;
        int normalImgNum = 0;
        ArrayList<String> slideImgUrls = new ArrayList<String>();
        ArrayList<String> normalImgUrls = new ArrayList<String>();
        String imgUrl;
        for (ProvinceImgModel imgModel : provinceImgs) {
            imgUrl = "img" + "_" + provin_id + "_" + imgModel.getId() + "." + imgModel.getSuffix();
            if (imgModel.getType() == 0) {
                slideImgNum++;
                slideImgUrls.add(imgUrl);
            } else {
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

    public boolean updateProvinceInfo(ProvinceDetailInfo provinceDetailInfo) {
        int result;
        result = mapDao.saveOrUpdateProvinceInfo(provinceDetailInfo.getProvin_id(), provinceDetailInfo.getIntroduce(), provinceDetailInfo.getScenic());
        if (result == 0) {
            return false;
        } else {
            return true;
        }
    }
}





