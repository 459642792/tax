<%--
  Created by IntelliJ IDEA.
  User: clam
  Date: 2017/4/13
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.blueteam.base.constant.Constants" %>
<html>
<head>
    <title>附近酒行-管理员登录</title>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/bootstrap.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/css.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/font-awesome.min.css"
          rel="stylesheet" type="text/css" media="screen"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/base.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/home.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/smartadmin-production.min.css"
          rel="stylesheet" type="text/css" media="screen"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/smartadmin-skins.min.css"
          rel="stylesheet" type="text/css" media="screen"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/summernote.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/summernote-bs3.css"
          rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/chosen.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.request.contextPath}/Content/Home/home.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Content/Home/favicon.ico"
          type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/Content/Home/favicon.ico" type="image/x-icon">
    <script src="${pageContext.request.contextPath}/Scripts/libs/jquery-2.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/Scripts/jquery.cookie.min.js"></script>
</head>

<body>
<style>
    .login_username {
        display: flex;
        width: 50%;
        margin: 15% auto auto auto;
        justify-content: center;
        flex-direction: column;

    }

    .userIndex footer .disble:before {
        display: none;
    }

    html {
        cursor: pointer;
        height: 100%;
        font-family: '微软雅黑';
        background-color: rgba(105, 105, 105, 0.13);
    }

    .login_username header div .shangjia {
        margin: 1rem 0 0 1.2rem;
        height: 36px;
    }

    .login_username header div .fjjh {
        margin: 0 0 0 -1rem;
        height: 30px;
    }

    .login_username header div {
        text-align: center;
    }

    .login_username header div p {
        font-size: 24px;
        color: red;
        text-align: center;
    }

    .login_username main {
        padding-left: 10px;
        width: 50%;

        margin: 0 auto;
    }

    .login_username main label {
        font-size: 16px;
        color: rgba(0, 0, 0, 0.54);
        width: 15%;
        margin-right: 0.1rem;
    }

    .login_username main .phone {
        border: none;
        width: 47%;;
        outline: none;
    }

    input::-webkit-input-placeholder {
        color: rgba(0, 0, 0, 0.54);
        font-weight: normal;
    }

    .login_username main .list_yanzhengma {
        border: none;
        outline: none;
        background-color: #ffffff;
        border-left: 1px solid gainsboro;
        font-size: 16px;
        color: red;
        width: auto;
        font-weight: 500;
    }

    ::-webkit-input-placeholder {
        font-family: '微软雅黑';
        font-size: 16px;
        color: gainsboro;
    }

    .login_username main .border_self {
        padding: 10px 0 10px 10px;
        width: 311px;
        margin: 5px auto;
        background-color: #ffffff;
    }

    input {
        -webkit-appearance: none;
        color: rgba(0, 0, 0, 0.54);
        font-weight: normal; /*去除input默认样式*/
    }

    input {
        border-radius: 0;
        font-size: 18px;
        /*height:34px;*/
    }

    .login_username footer p {

    }

    input::-webkit-input-placeholder {
        color: #878787;
    }

    .login_username footer {
        text-align: center;
        padding: 10px;
        width: 50%;
        margin: 0 auto;
    }

    .login_username footer p {
        color: #878787;
        font-size: 13px;
        width: 311px;
        margin: 0 auto;
        margin: 10px auto 5px auto;
    }

    .login_username footer span {
        background-color: rgba(216, 43, 43, 0.94);
        color: #ffffff;
        display: block;
        padding: 0.2rem;
        margin: 0 auto;
        width: 311px;
        border-radius: 3%;
        font-size: 20px;
    }

    .disble {
        color: #8E8B8B !important;
    }
</style>
<div class="login_username">
    <header>
        <div>
            <%--<img class="shangjia" src="<%= fileServer %>/NodeProgram/fjjh/imgs/public/shangjia1.png" /><br />--%>
            <%--<img class="fjjh" src="<%= fileServer %>/NodeProgram/fjjh/imgs/public/fujinjiuhang.png" />--%>
        </div>
    </header>
    <main>
        <div class="border_self" style="border:none">
            <label style="">手机号</label>
            <input type="tel" class="phone" name="tel" id="tel" autocomplete="off" placeholder="请输入手机号" maxlength="11"/><br/>
        </div>
        <div class="border_self">
            <label>验证码</label>
            <input type="tel" class="phone" name="valCode" id="valCode" autocomplete="off" maxlength="4"
                   placeholder="请输入验证码"/>
            <input class="list_yanzhengma" onclick="settime(this)" value="发送验证码" type="button">
        </div>
    </main>
    <footer>
        <p>管理员登录</p>
        <span class="login list" onclick="login()">登录</span>
    </footer>
</div>
<script type="text/javascript">
    var countdown = 60;

    function settime(val) {
        if ($.trim($("#tel").val()) == "") {
            alert('请输入电话号码')
            return false;
        }
        var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if (!myreg.test($("#tel").val())) {
            alert('请输入有效的手机号码')
            return false;
        }
        if (countdown == 0) {
            $.get("<%=Constants.APIS_WEBSITE%>/user/sendCode", {phone: $("#tel").val().trim()}, function (data) {
                if (!data.success) {
                    alert(data.message);
                    return false;
                }
            });
            val.removeAttribute("disabled");
            $(val).val("发送验证码")
            countdown = 60;
            $(val).removeClass('disble')
            return false;
        } else {
            $(val).attr("disabled", true);
            $(val).addClass('disble')
            $(val).val("重新发送(" + countdown + ")")
            countdown--;
        }

        setTimeout(function () {
            settime(val)
        }, 1000)
    }

    //手机搜索键登录处理
    document.onkeydown = function (e) {
        e = e || window.event;
        if (e.keyCode == 13) {
            pageModel();
        }

    }

</script>
<script type="text/javascript">
    var reload = '${pageContext.request.getParameter("reload")}';
    var fromUrl = '${pageContext.request.getParameter("fromUrl")}';

    function login() {
        var tel = $.trim($("#tel").val());
        var code = $.trim($("#valCode").val());

        $.post("${pageContext.request.contextPath}/api/user/login", {phone: tel, code: code}, function (ret) {
            if (ret.success) {
                $.cookie('access_token', ret.data.token, {path: '/'});
                if (fromUrl !== '')
                    window.location.href = fromUrl;
                else
                    window.location.href = '${pageContext.request.contextPath}/Index';
            } else
                alert(ret.message);
        });
    }

    (function () {

        if (reload === "") {
            var url = '${pageContext.request.contextPath}/user/login?reload=1';
            if (fromUrl !== "")
                url += "fromUrl=" + fromUrl;
            window.location.href = url;
        }

    })();
</script>
</html>
