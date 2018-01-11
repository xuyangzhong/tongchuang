var session;
window.addEventListener("load", requestSession(), false);
window.addEventListener("load", requestMessage(), false);

var getName = function (pk, list) {
    for (var i = 0; i < list.length; i++) {
        if (pk === list[i].pk)
            break;
    }
    return list[i].username;
}

var getPhoto = function (pk, list) {
    for (var i = 0; i < list.length; i++) {
        if (pk === list[i].pk)
            break;
    }
    return list[i].profile_pic;
}

var msgGenerator = function (message_id) {//id = msgid_xxxxx;
    $("#post-contain").append("<article class=\"post\" id = \"msgid_" +
        message_id + "\"></article>");
}

var msgHeadGenerator = function (photo, name, time) {
    return "<div class=\"post-head\">" +
        "<div class=\"user-pto\">" +
        "<img src=\"" + photo + "\"/>" +
        "</div>" +
        "<div class=\"user-info\">" +
        "<p class=\"user-name\">" + name + "</p>" +
        "<p class=\"post-date\">" + time + "</p>" +
        "</div>" +
        "</div>";
}

var msgContentGenerator = function (content) {
    return "<div class=\"post-content\" >" +
        "<p>" + content + "</p>" +
        "</div>";
}

var msgWidgetsGenerator = function (message) {
    var thumbUpNum = message.zan_num;
    return "<div class=\"widgets-container\">" +
        "<span class=\"views-count\">浏览" + thumbUpNum + "次</span>" +
        "<img class=\"reply-button\" src=\"/icon/liuyan.png\"/>" +
        "<img class=\"thumb-up-button\" src=\"/icon/zan.png\"/>" +
        "</div>";
}

var msgThumbsGenerator = function (thumbsList, thumbsUperList) {
    var str = "";
    for (var i = 0; i < thumbsList.length; i++) {
        if (i < thumbsList.length - 1) {
            str = str + getName(thumbsList[i].owner_pk, thumbsUperList) + "、";
        }
        else {
            str = str + getName(thumbsList[i].owner_pk, thumbsUperList) + "觉得很赞";
        }
    }
    if (thumbsList.length > 0) {
        return "<div class=\"thumbs-container\">" +
            "<div style=\"display: inline\"><img src=\"/icon/zanall.png\" /></div>" +
            "<div class = \"thumbs-list\">" +
            str +
            "</div>" +
            "</div>";
    }
    else
        return "";
}

var msgCommentsGenerator = function (commentsList, commentsPosterList) {
    var compare = function (x, y) {
        console.log("x=" + x + "    " + "y=" + y + "\n");
        if (x.parent_root === y.parent_root) {
            if (x.parent_root === 0) {
                return (x.revertTime > y.revertTime) ? true : false;
            }
            else {
                if (x.revert_id > y.revert_id)
                    return true;
                else if (x.revert_id === y.revert_id) {
                    return (x.revertTime > y.revertTime) ? true : false;
                }
                else {
                    return false;
                }
            }
        }
        else if (x.parent_root > y.parent_root) {
            return (x.revert_id >= y.id) ? true : false;
        }
        else {
            return (y.revert_id >= x.id) ? false : true;
        }
    };
    var html = "<div class=\"comments-container\">";
    var commemtId, photo, sender_name, receiver_name, content, date;
    commentsList.sort(compare);
    commentsList.sort(compare);
    commentsList.forEach(function (element, index) {
        console.log(element.content + "\n");
        if (element.parent_root === 0) {
            commemtId = element.id;
            photo = "/headpic/" + getPhoto(element.senderPK, commentsPosterList);
            sender_name = getName(element.senderPK, commentsPosterList);
            content = element.content;
            date = new Date(element.revertTime);
            date = (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + ((date.getMinutes() > 10) ? ("" + date.getMinutes()) : ("0" + date.getMinutes()));
            html = html + "<div class=\"comment parent\" id='comment_id_" + commemtId + "' onclick='changeReplyId(this)' data-receive-pk='" + element.receivePK + "' data-sender-pk='" + element.senderPK + "'>" +
                "<div class=\"comment-poster-pto\">" +
                "<img src=\"" + photo + "\"/>" +
                "</div>" +
                "<div class=\"comment-post\">" +
                "<span class=\"comment-sender-name\">" + sender_name + "</span>:" + "<span>" + content + "</span>" +
                "<p class=\"comment-post-time\">" + date + "</p>" +
                "</div>" +
                "</div>";
        }
        else {
            commemtId = element.id;
            photo = "/headpic/" + getPhoto(element.senderPK, commentsPosterList);
            sender_name = getName(element.senderPK, commentsPosterList);
            receiver_name = getName(element.receivePK, commentsPosterList);
            content = element.content;
            date = new Date(element.revertTime);
            date = (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + ((date.getMinutes() > 10) ? ("" + date.getMinutes()) : ("0" + date.getMinutes()));
            html = html + "<div class=\"comment child\" id='comment_id_" + commemtId + "' onclick='changeReplyId(this)'data-receive-pk='" + element.receivePK + "' data-sender-pk='" + element.senderPK + "' data-revert-id='" + element.revert_id + "'>" +
                "<div class=\"comment-poster-pto\">" +
                "<img src=\"" + photo + "\"/>" +
                "</div>" +
                "<div class=\"comment-post\">" +
                "<span class=\"comment-sender-name\">" + sender_name + "</span>回复<span class=\"comment-receiver-name\">" + receiver_name + "</span>:&nbsp;&nbsp;" + "<span>" + content + "</span>" +
                "<p class=\"comment-post-time\">" + date + "</p>" +
                "</div>" +
                "</div>";
        }
    });
    html = html + "</div>";
    return html;
}

var msgFootGenerator = function (message, commentsList, thumbsList, msgPoster, thumbsUperList, commentsPosterList) {
    return "<div class=\"post-foot\">" +
        msgWidgetsGenerator(message) +
        msgThumbsGenerator(thumbsList, thumbsUperList) +
        msgCommentsGenerator(commentsList, commentsPosterList) +
        commentsSenderGenerator(message) +
        "</div>";
}

var commentsSenderGenerator = function (message) {
    var text_name = "text_id_" + message.id;
    var sender_pk = session.pk;
    var receive_pk = message.owner_pk;
    var reply_btn_id = "reply_btn_id_" + message.id;
    return "<div class=\"comments-sender input-group\">" +
        "<span class=\"input-group-addon\">@</span>" +
        "<span class=\"input-group-addon\">&nbsp;&nbsp;&nbsp;</span>" +
        "<input type=\"text\" name=\"" + text_name + "\" class=\"form-control\" placeholder=\"评论\">" +
        "<span class=\"input-group-btn\">" +
        "<button class=\"btn btn-default\" id=\"" + reply_btn_id + "\" type=\"button\" data-sender-pk=\"" + sender_pk + "\" data-receive-pk=\"" + receive_pk + "\" data-parent-root=\"0\" data-reverts-id=\"-1\" onclick=\"sendComment('" + reply_btn_id + "')\">评论</button>" +
        "</span>" +
        "</div>";
}

var msgContentWrapper = function (message_id, content) {
    if (content.length > 150) {
        return content.substring(0, 150) + "<span id=\"" +
            "othercontent" + message_id +
            "\" style=\"display:none;\">" +
            content.substring(100) +
            "</span><a onclick=\"javascript:wrapMsgcontent('" +
            "othercontent" + news_id +
            "');return false\">查看全文>></a>";
    }
    else {
        return content;
    }
}

var wrapMsgContent = function (wrapedId) {
    if (document.getElementById(wrapedId).style.display === "none") {
        document.getElementById(wrapedId).style.display = "";
    }
    else {
        document.getElementById(wrapedId).style.display = "none";
    }
}

var showMsgHead = function (message, msgPoster) {
    var
        photo = "/headpic/" + msgPoster.profile_pic,
        name = msgPoster.username,
        post_time = new Date(message.createTime),
        message_id = message.id;
    post_time = post_time.getFullYear() + '-' + (post_time.getMonth() + 1) + "-" + post_time.getDate() + " " + post_time.getHours() + ":" + ((post_time.getMinutes() > 10) ? ("" + post_time.getMinutes()) : ("0" + post_time.getMinutes()));
    $("#msgid_" + message_id).append(msgHeadGenerator(photo, name, post_time));
}

var showMsgContent = function (message) {
    var
        message_id = message.id,
        content = message.content;
    content = msgContentWrapper(message_id, content);
    $("#msgid_" + message_id).append(msgContentGenerator(content));
}

var showMsgFoot = function (message, commentsList, thumbsList, msgPoster, thumbsUperList, commentsPosterList) {
    $("#msgid_" + message.id).append(msgFootGenerator(message, commentsList, thumbsList, msgPoster, thumbsUperList, commentsPosterList));
}

function requestMessage() {
    var messages = $.ajax({
        url: '/message/messagelist.do',
        data: {"now_sign": 0, "check_num": 10},
        type: "POST",
        dataType: "json",
        success: function (res) {
            var message, commentsList, thumbsList;
            var msgPoster, thumbsUperList, commentsPosterList;
            res.forEach(function (element) {
                message = element.message;
                commentsList = element.reverts;
                thumbsList = element.zans;
                msgPoster = element.messageOwner;
                thumbsUperList = element.zanOwnerLists;
                commentsPosterList = element.revertOwnerLists;
                msgGenerator(message.id);
                showMsgHead(message, msgPoster);
                showMsgContent(message);
                showMsgFoot(message, commentsList, thumbsList, msgPoster, thumbsUperList, commentsPosterList);
            })
        }
    });
}

function requestSession() {
    $.ajax({
        url: '/login/getSession.do',
        type: "POST",
        dataType: "text",
        async: false,
        success: function (res) {
            session = JSON.parse(res);
        }
    });
}

function changeReplyId(obj) {
    var login_pk = session.pk + "";
    $(obj).style.cssText = $(obj).css("background-color", "#e7e7e7");
    //var login_pk = "123";
    var id = $(obj).parent().parent().parent().attr("id") + "";
    id = id.substring(6);
    var receive_pk = $(obj).attr("data-sender-pk") + "";
    var input = $(obj).parent();
    input = input.next();
    input = input.children().next().next().next();
    //var  input = $(obj).parent().next().childNodes;
    var text = input.children();
    if ($(obj).hasClass("child")) {
        var revert_id = $(obj).attr("data-revert-id");
        if (login_pk === receive_pk) {
            receive_pk = $(obj).attr("data-receive-pk") + "";
            text.attr("data-receive-pk", receive_pk);
        }
        text.attr("data-reverts-id", revert_id);
        text.attr("data-parent-root", "1");
    }
    else {
        if (login_pk === receive_pk) {
        }
        else {
            var revert_id = $(obj).attr("id").substring(11);
            text.attr("data-receive-pk", receive_pk);
            text.attr("data-reverts-id", revert_id);
            text.attr("data-parent-root", "1");
        }
    }
}

function sendComment(obj) {
    var id = obj.substring(13);//reply_btn_id_xxx
    var replyContent = $("input:text[name='" + "text_id_" + id + "']").val();
    var senderPK = $("#" + obj).attr("data-sender-pk") + "";
    var receivePK = $("#" + obj).attr("data-receive-pk") + "";
    var parent_root = $("#" + obj).attr("data-parent-root") + "";
    var revert_id = $("#" + obj).attr("data-reverts-id") + "";
    var data = {
        "message_id": id,
        "parent_root": parent_root,
        "content": replyContent,
        "revert_id": revert_id,
        "senderPK": senderPK,
        "receivePK": receivePK
    };
    console.log(data);
    $.ajax({
        url: '/message/doRevert.do',
        data: data,
        type: "POST",
        dataType: "json",
        success: function (res) {
            if (res === true)
                window.location.reload();
        }
    })
}

