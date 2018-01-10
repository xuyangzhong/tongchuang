package com.tongchuang.controller;

import com.tongchuang.model.ProvinceDetailInfo;
import com.tongchuang.model.ProvinceImgUrlsModel;
import com.tongchuang.model.UserInfoModel;
import com.tongchuang.model.UserMapModel;
import com.tongchuang.service.MapService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/map")
public class MapController {
    private static final Logger log = LoggerFactory.getLogger(MapController.class);

    @Setter
    private MapService mapService;

    @RequestMapping(value = "/mapindex")
    @ResponseBody
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/maplist")
    @ResponseBody
    public ArrayList<UserMapModel> loadMapList(HttpServletRequest request, HttpServletResponse response){
        ArrayList<UserMapModel> userMapLists = mapService.loadUserMapData();
        return userMapLists;
    }

    @RequestMapping(value = "/provin_detail")
//    @ResponseBody
    public ModelAndView getProvinceDetailInfo(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        int provin_id = Integer.valueOf(request.getParameter("provin_id"));

        //该省份详细信息
        ProvinceDetailInfo provinceDetailInfo = mapService.getProvinDetailInfoByProvinId(provin_id);
        mav.addObject("provinceDetailInfo",provinceDetailInfo);

        //所在该省份同学
        ArrayList<UserInfoModel> userlist = mapService.getProvinUserByProvinId(provin_id);
        mav.addObject("userlist",userlist);

        //该省份幻灯片
//        ProvinceImgUrlsModel provinceImgUrls = mapService.getProvinceImgUrlsByProvinceId(provin_id);
        //简单设置成所有省份都是相同三张图片
        ProvinceImgUrlsModel provinceImgUrls = new ProvinceImgUrlsModel();
        provinceImgUrls.setProvin_id(provin_id);
        provinceImgUrls.setSlideImgNum(3);
        ArrayList<String> imgUrls = new ArrayList<String>();
        imgUrls.add("img_1_1.jpg");
        imgUrls.add("img_1_2.jpg");
        imgUrls.add("img_1_3.jpg");
        provinceImgUrls.setSlideImgUrls(imgUrls);

        mav.addObject("provinceImgUrls",provinceImgUrls);

        mav.setViewName("test");
        return mav;
    }

//    @RequestMapping(value = "/provin_imgs")
//    @ResponseBody
//    public ProvinceImgUrlsModel getProvinceImgs(HttpServletRequest request, HttpServletResponse response){
//        int provin_id = Integer.valueOf(request.getParameter("provin_id"));
//        ProvinceImgUrlsModel provinceImgUrlsModel = mapService.getProvinceImgUrlsByProvinceId(provin_id);
//        return provinceImgUrlsModel;
//    }

    @RequestMapping(value = "/change_province_info")
    @ResponseBody
    public boolean changeProvinceInfo(HttpServletRequest request, HttpServletResponse response){
        int provin_id = Integer.valueOf(request.getParameter("provin_id"));
        String introduce = request.getParameter("introduce");
        String scenic = request.getParameter("scenic");
        ProvinceDetailInfo provinceDetailInfo = new ProvinceDetailInfo();
        provinceDetailInfo.setProvin_id(provin_id);
        provinceDetailInfo.setIntroduce(introduce);
        provinceDetailInfo.setScenic(scenic);

        return mapService.updateProvinceInfo(provinceDetailInfo);
    }
}
