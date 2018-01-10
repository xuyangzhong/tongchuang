window.addEventListener("load", requestUserDetail(), false);

function upLoadMsg() {
    var pk = '<%=Session["pk"] %>';
    pk = 123;
    var title = "";
    var content = $("#write-memo textarea").val();
    console.log($("#write-memo textarea").val());
    var upLoad = $.ajax({
        url: "/message/publishmessage.do",
        data: {"pk": pk, "title": title, "content": content},
        type: "POST",
        dataType: "json",
        contentType: 'application/json; charset=UTF-8',
        success: function (res) {
            if (res === true)
                window.location.reload()
        }
    })
}

function requestUserDetail() {
    var username = '<%=Session["username"] %>';
    var pk = '<%=Session["pk"] %>';
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