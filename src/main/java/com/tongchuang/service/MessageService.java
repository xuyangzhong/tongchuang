package com.tongchuang.service;

import com.tongchuang.customException.MessageOperationException;
import com.tongchuang.dao.MessageDao;
import com.tongchuang.dao.UserDao;
import com.tongchuang.model.*;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:24 2018/1/2
 */

@Service
public class MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Setter
    private MessageDao messageDao;

    @Setter
    private UserDao userDao;

    public ArrayList<MessageShowModel> showAllMessage(int now_sign, int check_num) {
        ArrayList<MessageModel> messageLists = messageDao.getAllMessage(now_sign, check_num);
        return getRevertAndZanByMessageLists(messageLists);
    }

    public ArrayList<MessageShowModel> getMessageByPK(String pk,int index, int size) {
        ArrayList<MessageModel> messageLists = messageDao.getMessageByPK(pk,index,size);
        return getRevertAndZanByMessageLists(messageLists);
    }

    private ArrayList<MessageShowModel> getRevertAndZanByMessageLists(ArrayList<MessageModel> messageLists) {
        ArrayList<MessageShowModel> messageShowLists = new ArrayList<MessageShowModel>();
        MessageShowModel messageShow;
        for (MessageModel message : messageLists) {
            messageShow = new MessageShowModel();
            String messagePk = message.getOwner_pk();
            UserInfoModel messageOwner = userDao.loadUserInfo(messagePk);
            messageShow.setMessage(message);
            messageShow.setMessageOwner(messageOwner);
            int message_id = message.getId();


            //获取赞的用户信息
            ArrayList<UserInfoModel> zanOwners = new ArrayList<UserInfoModel>();
            ArrayList<ZanModel> zans = messageDao.loadZanPksByMessageId(message_id);
            messageShow.setZans(zans);
            if (zans != null && zans.size() != 0) {
                for (ZanModel zan : zans) {
                    String zanPK = zan.getOwner_pk();
                    UserInfoModel zanOwner = userDao.loadUserInfo(zanPK);
                    zanOwners.add(zanOwner);
                }
            }
            messageShow.setZanOwnerLists(zanOwners);


            //获取评论
            ArrayList<RevertModel> reverts = messageDao.loadRevertsByMessageId(message_id);
            messageShow.setReverts(reverts);

            //获取评论的用户信息
            ArrayList<UserInfoModel> revertOwners = new ArrayList<UserInfoModel>();
            UserInfoModel revertOwner;
            for (RevertModel revert : reverts) {
                revertOwner = userDao.loadUserInfo(revert.getSenderPK());
                revertOwners.add(revertOwner);
                //若不是一级评论
                if (revert.getReceivePK() != null) {
                    revertOwner = userDao.loadUserInfo(revert.getReceivePK());
                    revertOwners.add(revertOwner);
                }
            }
            messageShow.setRevertOwnerLists(revertOwners);

            messageShowLists.add(messageShow);
        }
        return messageShowLists;
    }

    public void addMessage(MessageModel message) {
        messageDao.addMessage(message.getTitle(), message.getContent(),
                message.getZan_num(), message.getCreateTime(), message.getOwner_pk());
        log.info(String.format("the pk %s create message", message.getOwner_pk()));
    }

    public void deleteMessage(int message_id) {
        messageDao.deleteMessage(message_id);
        log.info(String.format("delete the message successly, message_id is %d", message_id));

        //删除动态下的评论
        ArrayList<RevertModel> reverts = messageDao.loadRevertsByMessageId(message_id);
        for (RevertModel revert : reverts) {
            deleteRevert(revert.getId());
        }
    }

    public void doZan(ZanModel zan) {
        checkDeleteInZan(zan);
        ZanModel tempZan = messageDao.hasZan(zan.getMessage_id(), zan.getOwner_pk());
        if (tempZan != null) {
            log.error(String.format("the zan is existent , message_id is %d and owner_pk is %s", zan.getMessage_id(), zan.getOwner_pk()));
            return;
        }
        messageDao.addZan(zan.getMessage_id(), zan.getOwner_pk());
        log.info(String.format("the pk %s do zan , message_id is %d", zan.getOwner_pk(), zan.getMessage_id()));
    }

    public void undoZan(ZanModel zan) {
        ZanModel tempZan = messageDao.hasZan(zan.getMessage_id(), zan.getOwner_pk());
        if (tempZan == null) {
            log.error(String.format("the zan is non-existent , message_id is %d and owner_pk is %s", zan.getMessage_id(), zan.getOwner_pk()));
            throw new MessageOperationException("no_find_zan");
        }
        messageDao.deleteZan(tempZan.getId());
        log.info(String.format("the pk %s undo zan , message_id is %d", zan.getOwner_pk(), zan.getMessage_id()));
    }

    public void addRevert(RevertModel revert) {
        checkDeleteInRevert(revert);
        messageDao.addRevert(revert.getMessage_id(), revert.getContent(),
                revert.getParent_root(), revert.getRevert_id(),
                revert.getRevertTime(), revert.getSenderPK(),
                revert.getReceivePK());
        log.info(String.format("the pk %s revert , message_id is %d", revert.getSenderPK(), revert.getMessage_id()));
    }

    public void deleteRevert(int revert_id) {
        RevertModel revert = messageDao.loadRevertByRevertId(revert_id);
        if (revert == null) {
            log.error(String.format("the revert is non-existent , revert_id is %d", revert.getId()));
            throw new MessageOperationException("no_find_revert");
        }
        messageDao.deleteRevert(revert_id);
        log.info(String.format("delete the pk %s revert successly, revert_id is %d", revert.getSenderPK(), revert_id));

        cascadeDelete(revert_id);
    }

    //级联删除评论的评论
    private void cascadeDelete(int revert_id) {
        ArrayList<RevertModel> reverts = messageDao.loadRevertsByRevertId(revert_id);
        if (reverts == null)
            return;
        for (RevertModel revert : reverts) {
            messageDao.deleteRevert(revert.getId());
            cascadeDelete(revert.getId());
        }
    }

    //检查评论对象是否删除
    private void checkDeleteInRevert(RevertModel revert) {
        if (revert.getParent_root() == 0) {
            MessageModel message = messageDao.loadMessageByMessageId(revert.getMessage_id());
            if (message == null) {
                log.error("要评论的动态不存在,message_id is " + revert.getMessage_id());
                throw new MessageOperationException("no_find_message");
            }
        } else {
            RevertModel revertTemp = messageDao.loadRevertByRevertId(revert.getRevert_id());
            if (revertTemp == null) {
                log.error("要评论的评论不存在,revert_id is " + revert.getRevert_id());
                throw new MessageOperationException("no_find_revert");
            }
        }
    }

    private void checkDeleteInZan(ZanModel zan) {
        MessageModel message = messageDao.loadMessageByMessageId(zan.getMessage_id());
        if (message == null) {
            log.error("要赞的动态不存在,message_id is " + zan.getMessage_id());
            throw new MessageOperationException("no_find_message");
        }
    }
}
