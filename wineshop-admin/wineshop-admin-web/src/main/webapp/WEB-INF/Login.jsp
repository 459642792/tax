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
    <title>附近酒行-运营商登录</title>
    <link href="${pageContext.request.contextPath}/Content/themes/smart/css/base.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/Content/login.css" rel="stylesheet"/>
    <link rel="icon" href="${pageContext.request.contextPath}/Content/Home/favicon.ico" type="image/x-icon">
    <script src="${pageContext.request.contextPath}/Scripts/libs/jquery-2.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/Scripts/jquery.cookie.min.js"></script>

</head>

<body>
<div class="login_container">
    <header>
        <div class="login_top">
            <img src="${pageContext.request.contextPath}/Content/themes/smart/img/login_logo.png" alt="">
            <img src="${pageContext.request.contextPath}/Content/themes/smart/img/login_user.png" alt="">
        </div>
    </header>
    <section>
        <div class="login_form">
            <div class="dialog" id="dialog"><img
                    src="${pageContext.request.contextPath}/Content/themes/smart/img/login_error.png" alt=""><span
                    id="login_eror_text"></span></div>
            <div>
                <input type="text" class="phone" name="tel" id="tel" placeholder="请输入账号" maxlength="11"/>
            </div>
            <div class="login_form_list">
                <input type="text" class="code" name="tel" id="valCode" placeholder="请输入验证码" maxlength="4"/>
                <input type="button" id="getCode" value="获取验证码" class="getCode">
            </div>
            <div class="login_btn_list">
                <a href="javascript:void(0)" id="toLogin" class="login_btn">登录</a>
            </div>
            <div class="t_r"><a href="${pageContext.request.contextPath}/user/AdminLogin"
                                class="toAdminLogin">我是管理员>></a></div>
        </div>
    </section>
</div>
<script type="text/javascript" color="255,127,80" opacity='0.7' zIndex="2" count="150"
        src="//cdn.bootcss.com/canvas-nest.js/1.0.0/canvas-nest.min.js"></script>
<script>
    var reload = '${pageContext.request.getParameter("reload")}';
    var fromUrl = '${pageContext.request.getParameter("fromUrl")}';
    $(function () {
        $.cookie('access_token', null, {path: '/'})
        $("#tel").focus();
        if (reload === "") {
            var url = '${pageContext.request.contextPath}/user/login?reload=1';
            if (fromUrl !== "")
                url += "&fromUrl=" + encodeURIComponent(fromUrl);
            window.location.href = url;
        }
        var timer = 60;
        var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        $("#getCode").on("click", function () {
            var phone = $("#tel").val().trim();
            if (!phoneReg.test(phone)) {
                showError("请输入正确的账号");
                return false;
            }
            var interval = setInterval(function () {
                if (timer > 0) {
                    timer--
                    $("#getCode").val("重新发送(" + timer + ")").css({"color": "gray"});
                    $("#getCode").attr("disabled", "disabled");
                } else {
                    clearInterval(interval);
                    $("#getCode").removeAttr("disabled");
                    $("#getCode").val("发送验证码").css({"color": "red"});
                    timer = 60;
                }
            }, 1000)
            $.ajax({
                url: "<%=Constants.APIS_WEBSITE%>/user/sendCode",
                data: {phone: phone},
                type: "Get",
                success: function (data) {
                    if (!data.success) {
                        console.log(data.message);
                        return false;
                    }
                }
            })

        })
        $("#toLogin").on("click", function () {
            login();
        })
        document.onkeydown = function (e) {
            e = e || window.event;
            if (e.keyCode == 13) {
                login();
            }

        }

        function login() {
            var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
            var phone = $("#tel").val().trim();
            var code = $("#valCode").val().trim();
            if (!phoneReg.test(phone)) {
                showError("请输入正确的账号");
                return false;
            }
            else if (code == "" && code.length != 4) {
                showError("请输入正确的四位验证码");
                return false;
            }
            $.ajax({
                url: "<%=Constants.APIS_WEBSITE%>/user/carriers/login",
                data: {phone: phone, code: code},
                type: "POST",
                success: function (data) {
                    if (data.success) {
                        $.cookie('access_token', data.data.token, {path: '/'});
                        window.location.href = '${pageContext.request.contextPath}/Index/index'
                    } else {
                        showError(data.message);
                    }

                }
            })


        }

        function showError(txt) {
            $("#login_eror_text").text(txt);
            $("#dialog").slideDown();
            setTimeout(function () {
                $("#dialog").slideUp()
            }, 3000)
        }
    })

</script>
</body>
</html>
