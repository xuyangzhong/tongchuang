package com.tongchuang.controller;

import com.tongchuang.dao.UserDao;
import com.tongchuang.model.UserLoginModel;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    public static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Setter
    private UserDao userDao;

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

        UserLoginModel userLoginModel = new UserLoginModel();
        userLoginModel.setPk(pk);
        userLoginModel.setPassword(password);

        return userDao.checkUserLogin(userLoginModel);
    }

    @RequestMapping(value = "/exit")
    public void exit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.getSession().removeAttribute("userSession");
        response.sendRedirect("/userlogin/index.html");
    }

}
