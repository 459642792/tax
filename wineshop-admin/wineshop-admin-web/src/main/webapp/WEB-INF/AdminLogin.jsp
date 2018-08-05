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
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/base.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/Content/login.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/Content/Home/favicon.ico"
          type="image/x-icon">
    <link rel="icon" href="${pageContext.request.contextPath}/Content/Home/favicon.ico" type="image/x-icon">
    <script src="${pageContext.request.contextPath}/Scripts/libs/jquery-2.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/Scripts/jquery.cookie.min.js"></script>
    <script src="${pageContext.request.contextPath}/Scripts/extend/CommonBase.min.js"></script>
</head>

<body>
<div class="login_container">
    <header>
        <div class="login_top">
            <img src="${pageContext.request.contextPath}/Content/themes/smart/img/login_logo.png" alt="">
        </div>
    </header>
    <section>
        <div class="login_form">
            <div class="dialog" id="dialog"><img
                    src="${pageContext.request.contextPath}/Content/themes/smart/img/login_error.png" alt=""><span
                    id="login_eror_text"></span></div>
            <div>
                <input type="text" class="phone" name="tel" id="tel" placeholder="请输入账号" maxlength="11"/><br/>
            </div>
            <div class="login_form_list">
                <input type="password" class="pwd" name="tel" id="valCode" placeholder="请输入密码" maxlength="20"/><br/>
            </div>
            <div class="login_btn_list">
                <a href="javascript:void(0)" onclick="login()" class="login_btn">登录</a>
            </div>
            <div class="t_r"><a href="${pageContext.request.contextPath}/user/login" class="toAdminLogin">我是运营商>></a>
            </div>
        </div>
    </section>
</div>
</body>
<script type="text/javascript" color="255,127,80" opacity='1' zIndex="2" count="150"
        src="//cdn.bootcss.com/canvas-nest.js/1.0.0/canvas-nest.min.js"></script>
<script type="text/javascript">
    //手机搜索键登录处理
    document.onkeydown = function (e) {
        e = e || window.event;
        if (e.keyCode == 13) {
            login();
        }
    }


    if ('${loginout}' === 'true' || '${pageContext.request.getParameter("loginout")}' === 'true')
        $.cookie('access_token', null, {path: '/'}); // 删除 cookie

    var reload = '${pageContext.request.getParameter("reload")}';
    var fromUrl = '${pageContext.request.getParameter("fromUrl")}';
    var loginOut = '${loginout}';

    function login() {
        var tel = $.trim($("#tel").val());
        var code = $.trim($("#valCode").val());
        if (tel === "") {
            showError("请输入正确的账号");
            return;
        }
        if (code === "") {
            showError("请输入密码");
            return;
        }

        $.post("${pageContext.request.contextPath}/api/user/login", {userName: tel, password: code}, function (ret) {
            if (ret.success) {
                $.cookie('access_token', ret.data.token, {path: '/'});
                if (fromUrl !== '')
                    window.location.href = fromUrl;
                else
                    window.location.href = '${pageContext.request.contextPath}/Index/index'
            } else
                showError(ret.message);
        });
    }

    (function () {
        $("#tel").focus();
        if (reload === "") {
            var url = '${pageContext.request.contextPath}/user/AdminLogin?reload=1';
            if (fromUrl !== "")
                url += "&fromUrl=" + encodeURIComponent(fromUrl);
            if (loginOut !== '')
                url += "&loginout=true";
            window.location.href = url;
        }

    })();

    function showError(txt) {
        $("#login_eror_text").text(txt);
        $("#dialog").slideDown();
        setTimeout(function () {
            $("#dialog").slideUp()
        }, 3000)
    }
</script>
</html>
