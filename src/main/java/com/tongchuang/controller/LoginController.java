package com.tongchuang.controller;

import com.tongchuang.model.UserLoginModel;
import com.tongchuang.model.UserSessionModel;
import com.tongchuang.service.LoginService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    public static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Setter
    private LoginService loginService;

    private static final String PARAM_NULL_ERROR = "null_error";

    private static final String LOGIN_FAIL = "user_error";

    private static final String LOGIN_SUCCESS = "success";

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/test")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("city", "test");
        mav.setViewName("hello");
        return mav;
    }

    @RequestMapping(value = "/checklogin")
    @ResponseBody
    public boolean checklogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pk = request.getParameter("pk").trim();
        String password = request.getParameter("password");

        UserLoginModel userLoginModel = loginService.checkLoginValidity(pk,password);

        if(userLoginModel == null){
            System.out.println("login fail");
            return false;
        }

        UserSessionModel userSessionModel = new UserSessionModel();
        userSessionModel.setPk(userLoginModel.getPk());
        userSessionModel.setUsername(userLoginModel.getUsername());


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        userSessionModel.setLoginTime(sdf.format(date));
        userSessionModel.setPower(userLoginModel.getPower());

        request.getSession().setAttribute("userSession",userSessionModel);

        System.out.println("login success");
        return true;
    }

    @RequestMapping(value = "/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getSession().removeAttribute("userSession");
        response.sendRedirect("/userlogin/index.html");
    }

}
