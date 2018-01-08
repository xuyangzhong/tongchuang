window.addEventListener("load", getdata(), false);

var getcontent = function (news_id, content) {
    if (content.length > 100) {
        return content.substring(0, 100) + "<span id=\"" + "othercontent" + news_id + "\" style=\"display:none;\">" + content.substring(100) + "</span><a onclick=\"javascript:wrapcontent('" + "othercontent" + news_id + "');return false\">查看全文>></a>";
    }
    else {
        return content;
    }
}

var gethtml = function (id, title, author, createTime, content, zanNum, revertsNum) {
    return "<article class=\"post\" id=\"" + id + "\"> <div class=\"post-head\"> <h1 class=\"post-title\">" + title + "</h1> <div class=\"post-meta\"> <span class=\"author\">作者：" + author + "</span> &nbsp;&nbsp;<time class=\"post-date\"> " + createTime + "</time> </div> </div> <div class=\"post-content\"> <p> " + getcontent(id, content) + "</p> </div> <hr style=\"height:1px;border:none;border-top:1px solid #ebebeb;\"/> <div class=\"post-stats\"> <span class=\"post-votes\">" + zanNum + "</span>&nbsp;赞&nbsp;-&nbsp; <span class=\"post-view\">" + revertsNum + "</span>&nbsp;浏览 </div> <div class=\"post-votes\"> <button type=\"button\" class=\"btn btn-default btn-sm\"> <span class=\"glyphicon glyphicon-thumbs-up\"></span> </button> <button type=\"button\" class=\"btn btn-default btn-sm\"> <span class=\"glyphicon glyphicon-thumbs-down\"></span> </button> </div><div class = \"post-reverts\"></div> </article>"
}

var getrevert = function (id, parent_root, sender_name, receive_name, content) {
    if (parent_root === 0) {
        return "<p class=\"parent\" id=\"" + "reverts" + id + "\">" + sender_name + ":&nbsp;&nbsp;" + content + "</p>";
    }
    else {
        return "<p class=\"child\" id=\"" + "reverts" + id + "\">" + sender_name + "&nbsp;" + "回复" + receive_name + ":&nbsp;&nbsp;" + content + "</p>";
    }
}

var wrapcontent = function (obj) {
    var objdisplay = document.getElementById(obj).style.display;
    if (objdisplay === "none") {
        document.getElementById(obj).style.display = "";
    }
    else {
        document.getElementById(obj).style.display = "none";
    }
}

var getOwner = function (pk, revertOwnerList) {
    for (var i = 0; i < revertOwnerList.length; i++) {
        if (pk === revertOwnerList[i].pk)
            break;
    }
    return revertOwnerList[i].username;
}

var compare = function (x, y) {
    if (x.parent_root > y.parent_root)
        return true;
    else if (x.parent_root === y.parent_root && x.revertTime > y.revertTime)
        return true;
    else
        return false;
}

var split = function (revertList) {
    revertList.sort(compare);
}


function getdata() {
    var news = $.ajax({
        url: '/message/messagelist.do',
        data: {"now_sign": 0, "check_num": 10},
        type: "POST",
        dataType: "json",
        success: function (res) {
            var id, title, author, createTime, content, zanNum, revertsNum;
            var ownerlist, revertlist, news_id, content, parent_root, parent_id, revertTime, senderPk, receiverPk;
            var html, newhtml;
            res.forEach(function (value, index) {
                id = value.message.id;
                title = value.message.title;
                author = value.messageOwner.username;
                createTime = new Date(value.message.createTime);
                createTime = createTime.getFullYear() + "-" + createTime.getMonth() + "-" + createTime.getDay();
                content = value.message.content;
                zanNum = value.message.zan_num;
                revertsNum = value.zans.length;
                html = $("#post-contain").html();
                newhtml = gethtml(id, title, author, createTime, content, zanNum, revertsNum);
                $("#post-contain").html(html + newhtml);
                ownerlist = value.revertOwnerLists;
                revertlist = value.reverts;
                revertlist.sort(compare);
                console.log(index + "\n");
                revertlist.forEach(function (value2) {
                    if (value2.parent_root === 0) {
                        $("#" + value2.message_id + " div.post-reverts").html($("#" + value2.message_id + " div.post-reverts").html() + getrevert(value2.id, value2.parent_root, getOwner(value2.senderPK, ownerlist), "", value2.content));
                        console.log(getOwner(value2.senderPK, ownerlist) + ":" + value2.content);
                    }
                    else {
                        $("#reverts" + value2.revert_id).append(getrevert(value2.id, value2.parent_root, getOwner(value2.senderPK, ownerlist), getOwner(value2.receivePK, ownerlist), value2.content));
                        console.log(value2.content + '\n');
                    }
                })
            });
        }
    });
}




