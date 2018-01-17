package com.tongchuang.controller;

import com.tongchuang.model.MessageShowModel;
import com.tongchuang.model.UserInfoModel;
import com.tongchuang.model.VisitShowModel;
import com.tongchuang.service.MessageService;
import com.tongchuang.service.UserService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static String[] allowPicSuffixes = {"jpg", "png", "jpeg"};

    //默认查询10条
    private static int DEFAULT_SIZE = 10;

    private static int DEFAULT_INDEX = 0;

    //最大上传10M
    private static long maxUploadSize = 10485760;

    @Setter
    private UserService userService;

    @Setter
    private MessageService messageService;

    @RequestMapping(value = "test")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("test");
        return mav;
    }

    @RequestMapping(value = "/userinfo")
    public ModelAndView getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String pk = request.getParameter("pk");
        UserInfoModel userInfo = userService.getUserInfoByPK(pk);
        ModelAndView mav = new ModelAndView();
        mav.addObject("userinfo", userInfo);
        mav.setViewName("userinfo");
        return mav;
    }

    @RequestMapping(value = "/showVisitUser")
    @ResponseBody
    public VisitShowModel showVisitUser(HttpServletRequest request, HttpServletResponse response){
        String pk = request.getParameter("pk");
        VisitShowModel visitShow = userService.getVisitInfo(pk);
        return visitShow;
    }

    @RequestMapping(value = "/usermessage")
    @ResponseBody
    public ArrayList<MessageShowModel> showMessageListByUser(HttpServletRequest request, HttpServletResponse response) {
        String pk = request.getParameter("user_pk");
        String index_str = request.getParameter("index");
        int index;
        //未设置查询开始标志，则从1开始
        if (index_str == null) {
            index = DEFAULT_INDEX;
        } else {
            index = Integer.valueOf(index_str);
        }
        String check_num_str = request.getParameter("size");
        int size;
        //未设置查询条数，则默认为10条
        if (check_num_str == null) {
            size = DEFAULT_SIZE;
        } else {
            size = Integer.valueOf(check_num_str);
        }
        //禁止一次查询超过50条
        if (size > 50) {
            size = DEFAULT_SIZE;
        }
        return messageService.getMessageByPK(pk,index,size);
    }

    @RequestMapping(value = "/change_profile_pic")
    @ResponseBody
    public String changeProfilePic(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        String pk = request.getParameter("pk");

        //上传文件
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                String fileOldName = file.getOriginalFilename();
                String suffix = fileOldName.substring(fileOldName.indexOf(".") + 1);

                if (file == null || file.getSize() == 0) {
                    return "no_find_file";
                }

                if (file.getSize() > maxUploadSize) {
                    return "beyond_size_limit";
                }
                String fileNewName = pk + "-" + new Timestamp(new Date().getTime()) + "." + suffix;
                boolean isSuccessChangeDB;
                try {
                    userService.changeProfilePic(pk, fileNewName);
                    isSuccessChangeDB = true;
                } catch (Exception e) {
                    log.error("change profile pic fail", e);
                    isSuccessChangeDB = false;
                }

                if (isSuccessChangeDB == false) {
                    return "false";
                }

                String path = request.getSession().getServletContext().getRealPath("/headpic/" + fileNewName);
                file.transferTo(new File(path));

            }
        }
        return "success";
    }

    private boolean checkIllegalSuffix(String suffix) {
        if (suffix == null)
            return false;
        for (String allowSuffix : allowPicSuffixes) {
            if (allowSuffix.equals(suffix))
                return true;
        }
        return false;
    }
}
