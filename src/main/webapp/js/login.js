function checkLogin(){
    var pk = $("#pk").val();
    var password = $("#password").val();

    if(pk == null || pk.trim() == ""){
        checkPK();
        return false;
    }
    if(password == null || password.trim() == ""){
        checkpassword();
        return false;
    }
    $.ajax({
        type : "post",
        url : "/login/checklogin.do",
        cache : false,
        async : true,
        data : {
            "pk" : pk,
            "password" : password
        },

        success:function(result){
            if("user_error" == result) {
                $("#myAlert").removeClass('hidden');
            }else if("success" == result){
                window.location.href = "/map/mapindex.html";
            }
        }
    })
}

function hide(){
    $("#myAlert").addClass('hidden');
}
function checkPK(){
    $("#pk").attr("placeholder","用户名不能为空");
}
function resetPK(){
    $("#pk").attr("placeholder","");
}
function checkpassword(){
    $("#password").attr("placeholder","密码不能为空");
}
function resetpassword(){
    $("#password").attr("placeholder","");
}