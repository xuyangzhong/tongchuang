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
    return "<div class=\"thumbs-container\">" +
        "<div style=\"display: inline\"><img src=\"/icon/zanall.png\" /></div>" +
        "<div class = \"thumbs-list\">" +
        str +
        "</div>" +
        "</div>";
}

var msgCommentsGenerator = function (commentsList, commentsPosterList) {
    var compare = function (x, y) {
        console.log("x=" + x + "    " + "y=" + y + "\n");
        if (x.parent_root === y.parent_root) {
            if (x.parent_root === 0) {
                return (x.revertTime > y.revertTime) ? true : false;
            }
            else {
                return (x.revert_id > y.revert_id) ? true : false;
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
    var photo, sender_name, receiver_name, content, date;
    commentsList.sort(compare);
    commentsList.forEach(function (element, index) {
        console.log(element.content + "\n");
        if (element.parent_root === 0) {
            photo = "/headpic/" + getPhoto(element.senderPK, commentsPosterList);
            sender_name = getName(element.senderPK, commentsPosterList);
            content = element.content;
            date = new Date(element.revertTime);
            date = (date.getMonth() + 1) + "-" + date.getDay();
            html = html + "<div class=\"comment parent\">" +
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
            photo = "/headpic/" + getPhoto(element.senderPK, commentsPosterList);
            sender_name = getName(element.senderPK, commentsPosterList);
            receiver_name = getName(element.receivePK, commentsPosterList);
            content = element.content;
            date = new Date(element.revertTime);
            date = (date.getMonth() + 1) + "-" + date.getDay();
            html = html + "<div class=\"comment child\">" +
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
    post_time = post_time.getFullYear() + '-' + (post_time.getMonth() + 1) + "-" + post_time.getDay();
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

