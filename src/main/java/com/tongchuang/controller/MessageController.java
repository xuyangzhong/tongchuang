package com.tongchuang.controller;

import com.tongchuang.customException.MessageOperationException;
import com.tongchuang.model.MessageModel;
import com.tongchuang.model.MessageShowModel;
import com.tongchuang.model.RevertModel;
import com.tongchuang.model.ZanModel;
import com.tongchuang.service.MessageService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:22 2018/1/2
 */

@Controller
@RequestMapping(value = "/message")
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    //默认查询10条
    private static int default_check_num = 10;

    //默认从第1条开始
    private static int default_now_sign = 0;

    @Setter
    private MessageService messageService;

    @RequestMapping(value = "/message")
    public ModelAndView messageIndex(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("message");
        return mav;
    }

    @RequestMapping(value = "/messagelist")
    @ResponseBody
    public ArrayList<MessageShowModel> showMessageLists(HttpServletRequest request, HttpServletResponse response) {
        String now_sign_str = request.getParameter("now_sign");
        int now_sign;

        //未设置查询开始标志，则从0开始
        if (now_sign_str == null) {
            now_sign = default_now_sign;
        } else {
            now_sign = Integer.valueOf(now_sign_str);
        }

        String check_num_str = request.getParameter("check_num");
        int check_num;

        //未设置查询条数，则默认为10条
        if (check_num_str == null) {
            check_num = default_check_num;
        } else {
            check_num = Integer.valueOf(check_num_str);
        }
        //禁止一次查询超过50条
        if (check_num > 50) {
            check_num = default_check_num;
        }
        ArrayList<MessageShowModel> messageShowLists = messageService.showAllMessage(now_sign, check_num);
        return messageShowLists;
    }

    @RequestMapping(value = "/publishmessage")
    @ResponseBody
    public boolean publishMessage(HttpServletRequest request, HttpServletResponse response) {
        String pk = request.getParameter("pk");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Timestamp createTime = new Timestamp(new Date().getTime());
        int zan_num = 0;
        MessageModel message = new MessageModel();
        message.setTitle(title);
        message.setContent(content);
        message.setOwner_pk(pk);
        message.setCreateTime(createTime);

        try {
            messageService.addMessage(message);
            return true;
        } catch (Exception e) {
            log.error("publish message fail", e);
            return false;
        }
    }

    @RequestMapping(value = "/deletemessage")
    @ResponseBody
    public boolean deleteMessage(HttpServletRequest request, HttpServletResponse response) {
        int message_id = Integer.valueOf(request.getParameter("message_id"));
        try {
            messageService.deleteMessage(message_id);
            return true;
        } catch (Exception e) {
            log.error("delete message fail", e);
            return false;
        }
    }

    @RequestMapping(value = "/dozan")
    @ResponseBody
    public String doZan(HttpServletRequest request, HttpServletResponse response) {
        String pk = request.getParameter("user_pk");
        int message_id = Integer.valueOf(request.getParameter("message_id"));
        ZanModel zan = new ZanModel();
        zan.setMessage_id(message_id);
        zan.setOwner_pk(pk);
        try {
            messageService.doZan(zan);
            return "true";
        } catch (MessageOperationException e) {
            return "overdue";
        } catch (Exception e) {
            log.error("doZan fail", e);
            return "false";
        }
    }

    @RequestMapping(value = "/undozan")
    @ResponseBody
    public String undoZan(HttpServletRequest request, HttpServletResponse response) {
        String owner_pk = request.getParameter("user_pk");
        int message_id = Integer.valueOf(request.getParameter("message_id"));
        ZanModel zan = new ZanModel();
        zan.setMessage_id(message_id);
        zan.setOwner_pk(owner_pk);
        try {
            messageService.undoZan(zan);
            return "true";
        } catch (MessageOperationException e) {
            return "overdue";
        } catch (Exception e) {
            log.error("undoZan fail", e);
            return "false";
        }
    }

    @RequestMapping(value = "/doRevert")
    @ResponseBody
    public String doRevert(HttpServletRequest request, HttpServletResponse response) {
        int message_id = Integer.valueOf(request.getParameter("message_id"));
        String content = request.getParameter("content");
        int parent_root = Integer.valueOf(request.getParameter("parent_root"));
        int revert_id;
        if (parent_root == 0) {
            revert_id = -1;
        } else {
            revert_id = Integer.valueOf(request.getParameter("revert_id"));
        }
        String senderPK = request.getParameter("senderPK");
        String receivePK = request.getParameter("receivePK");
        Timestamp revertTime = new Timestamp(new Date().getTime());
        if (parent_root == 0) {
            revert_id = -1;
        }

        RevertModel revert = new RevertModel();
        revert.setMessage_id(message_id);
        revert.setContent(content);
        revert.setParent_root(parent_root);
        revert.setRevertTime(revertTime);
        revert.setRevert_id(revert_id);
        revert.setSenderPK(senderPK);
        revert.setReceivePK(receivePK);

        try {
            messageService.addRevert(revert);
            return "true";
        } catch (MessageOperationException e) {
            return "overdue";
        } catch (Exception e) {
            log.error("doRevert fail", e);
            return "false";
        }
    }

    @RequestMapping(value = "/removeRevert")
    @ResponseBody
    public String removeRevert(HttpServletRequest request, HttpServletResponse response) {
        int revert_id = Integer.valueOf(request.getParameter("revert_id"));
        try {
            messageService.deleteRevert(revert_id);
            return "true";
        } catch (MessageOperationException e) {
            return "overdue";
        } catch (Exception e) {
            log.error("removeRevert fail", e);
            return "false";
        }
    }

}
