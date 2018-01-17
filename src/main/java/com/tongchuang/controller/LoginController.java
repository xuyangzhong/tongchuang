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
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Setter
    private LoginService loginService;

    private static final String LOGIN_FAIL = "user_error";

    private static final String LOGIN_SUCCESS = "success";

    private static final String EXIT_SUCCESS = "success";

    @RequestMapping(value = "/getSession")
    @ResponseBody
    public UserSessionModel getSession(HttpServletRequest request, HttpServletResponse response){
        return (UserSessionModel) request.getSession().getAttribute("userSession");
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        if(request.getSession().getAttribute("userSession")!=null){
            request.getRequestDispatcher("/map/mapindex.html");
            return null;
        }
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/checklogin")
    @ResponseBody
    public String checklogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pk = request.getParameter("pk").trim();
        String password = request.getParameter("password");

        UserLoginModel userLoginModel = loginService.checkLoginValidity(pk,password);

        if(userLoginModel == null){
            return LOGIN_FAIL;
        }

        UserSessionModel userSessionModel = new UserSessionModel();
        userSessionModel.setPk(userLoginModel.getPk());
        userSessionModel.setUsername(userLoginModel.getUsername());


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        userSessionModel.setLoginTime(sdf.format(date));
        userSessionModel.setPower(userLoginModel.getPower());

        request.getSession().setAttribute("userSession",userSessionModel);

        return LOGIN_SUCCESS;
    }

    @RequestMapping(value = "/exit")
    @ResponseBody
    public String exit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getSession().removeAttribute("userSession");
        return EXIT_SUCCESS;
    }

}
