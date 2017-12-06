package com.tongchuang.controller;

import com.tongchuang.model.ProvinceDetailInfo;
import com.tongchuang.model.UserMapModel;
import com.tongchuang.service.MapService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/map")
public class MapController {
    public static final Logger log = LoggerFactory.getLogger(MapController.class);

    @Setter
    private MapService mapService;

    @RequestMapping(value = "/maplist")
    @ResponseBody
    public ArrayList<UserMapModel> loadMapList(HttpServletRequest request, HttpServletResponse response){
        ArrayList<UserMapModel> userMapLists = mapService.loadUserMapData();
        return userMapLists;
    }

    @RequestMapping(value = "/provin_detail")
    @ResponseBody
    public ProvinceDetailInfo getProvinceDetailInfo(HttpServletRequest request, HttpServletResponse response){
        int provin_id = Integer.valueOf(request.getParameter("provin_id"));
        ProvinceDetailInfo provinceDetailInfo = mapService.getProvinDetailInfoByProvinId(provin_id);
        return provinceDetailInfo;
    }
}
