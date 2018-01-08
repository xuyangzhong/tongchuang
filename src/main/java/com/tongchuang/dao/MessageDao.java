package com.tongchuang.dao;

import com.tongchuang.model.MessageModel;
import com.tongchuang.model.RevertModel;
import com.tongchuang.model.ZanModel;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:25 2018/1/2
 */
public interface MessageDao {
    ArrayList<MessageModel> getAllMessage(@Param("now_sign") int now_sign, @Param("check_num") int check_num);

    ArrayList<MessageModel> getMessageByPK(@Param("owner_pk") String pk);

    ArrayList<ZanModel> loadZanPksByMessageId(@Param("message_id") int message_id);

    ArrayList<RevertModel> loadRevertsByMessageId(@Param("message_id") int message_id);

    MessageModel loadMessageByMessageId(@Param("id") int message_id);

    RevertModel loadRevertByRevertId(@Param("id") int revert_id);

    int addMessage(@Param("title") String title, @Param("content") String content,
                @Param("zan_num") int zan_num, @Param("createTime") Timestamp createTime,
                @Param("owner_pk") String owner_pk);

    int deleteMessage(@Param("id") int id);

    int addZan(@Param("message_id") int message_id, @Param("owner_pk") String owner_pk);

    ZanModel hasZan(@Param("message_id") int message_id, @Param("owner_pk") String owner_pk);

    int deleteZan(@Param("id") int zan_id);

    int addRevert(@Param("message_id") int message_id, @Param("content") String content, @Param("parent_root") int parent_root,
                  @Param("revert_id") int revert_id, @Param("revertTime") Timestamp revertTime, @Param("senderPK") String senderPK,
                  @Param("receivePK") String receivePK);

    int deleteRevert(@Param("id") int revert_id);

    ArrayList<RevertModel> loadRevertsByRevertId(@Param("revert_id") int revert_id);
}
