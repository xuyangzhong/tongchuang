package com.tongchuang.controller;

import com.tongchuang.model.UserInfoModel;
import com.tongchuang.service.UserService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Setter
    private UserService userService;

    @RequestMapping(value = "/userinfo")
    public ModelAndView getUserInfoByPK(HttpServletRequest request, HttpServletResponse response){
        String pk = request.getParameter("pk");
        UserInfoModel userInfo = userService.getUserInfoByPK(pk);
        ModelAndView mav = new ModelAndView();
        mav.addObject("userinfo",userInfo);
        mav.setViewName("userinfo");
        return mav;
    }
}
