var session;
window.addEventListener("load", requestSession(), false);
window.addEventListener("load", requestUserDetail(), false);

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

function upLoadMsg() {
    var pk = session.pk;
    var title = "";
    var content = $("#write-memo textarea").val();
    console.log($("#write-memo textarea").val());
    $.ajax({
        url: "/message/publishmessage.do",
        data: {
            "pk": pk,
            "title": title,
            "content": content
        },
        type: "POST",
        dataType: "text",
        success: function (res) {
            console.log(res);
            if (res === true)
                window.location.reload()
        }
    })
}

function requestUserDetail() {
    var username = session.username;
    var pk = session.pk;
    pk = 123;
    var photo, introduction, career;
    var userInfoDetail = $.ajax({
        url: '/user/userinfo.do',
        data: {"pk": pk},
        type: "POST",
        dataType: "json",
        success: function (res) {
            photo = "/headpic/" + res.userinfo.profile_pic;
            introduction = res.userinfo.introduce;
            career = res.userinfo.industry;
            $("#user-info-pto").append("<img src=\"" + photo + "\">");
            $("p", $("#user-info-introduction")).text = introduction;
            $("p", $("#user-info-career")).text = career;

        }
    });
}

function showUserIntroduction() {
    if ($(".list-group-item.disabled")[0].style.display === "none")
        $(".list-group-item.disabled")[0].style.display = "";
    else
        $(".list-group-item.disabled")[0].style.display = "none";
}