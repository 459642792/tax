<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page import="com.blueteam.base.constant.Constants" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <title>附近酒行首页</title>
    <link href="Content/themes/smart/css/bootstrap.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="Content/themes/smart/css/css.min.css" rel="stylesheet"/>
    <link href="Content/themes/smart/css/font-awesome.min.css"
          rel="stylesheet" type="text/css" media="screen"/>
    <link href="Content/themes/smart/css/base.css" rel="stylesheet"/>
    <link href="Content/themes/smart/css/home.css" rel="stylesheet"/>
    <link href="Content/themes/smart/css/smartadmin-production.min.css"
          rel="stylesheet" type="text/css" media="screen"/>
    <link href="Content/themes/smart/css/smartadmin-skins.min.css"
          rel="stylesheet" type="text/css" media="screen"/>
    <link href="Content/themes/smart/css/summernote.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <link href="Scripts/jqPagination/kkpager_blue.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <link href="Content/themes/smart/css/summernote-bs3.css"
          rel="stylesheet" type="text/css"/>
    <link href="Content/themes/smart/css/chosen.css" rel="stylesheet"
          type="text/css"/>
    <link href="Content/Home/home.css" rel="stylesheet"/>
    <link href="Content/Home/confrim.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="Content/Home/favicon.ico"
          type="image/x-icon">
    <link rel="icon" href="Content/Home/favicon.ico" type="image/x-icon">
    <style type="text/css">
        .dataTables_length {
            float: right;
        }

        .dataTables_processing {
            position: absolute;
            top: 50%;
            left: 50%;
            height: 45px;
            margin-left: -80px;
            padding: 10px 50px 0px 50px;
            text-align: center;
            font-size: 1.2em;
            background-color: rgba(43, 38, 38, 0.9);
            color: white;
            box-shadow: 0px 0px 7px #5E5656;
            margin-right: auto;
        }

        .smart-form .label.col {
            text-align: right;
        }

        .smart-form section {
            margin-bottom: 5px;
        }

        .no-padding .note-editor {
            border: 1px solid #a9a9a9;
        }

        .note-editor .panel-heading {
            padding: 0px 5px 5px;
        }

        .tableBtn {
            margin-right: 10px;
        }

        .dropzone {
            min-height: 100px;
        }

        .dropzone .dz-preview {
            margin: 15px;
        }

        #page_loading {
            opacity: 0.8;
            background-color: rgba(255, 255, 255, 1);
            height: 100%;
            width: 100%;
            position: fixed;
            z-index: 1;
            margin-top: 0px;
            top: 0px;
        }

        #page_loading-center {
            opacity: 100;
            width: 100%;
            height: 100%;
            position: relative;
        }

        #page_loading-center-absolute {
            opacity: 100;
            position: absolute;
            left: 50%;
            top: 50%;
            height: 100px;
            width: 100px;
            margin-top: -50px;
            margin-left: -50px;
        }

        .object {
            opacity: 100;
            width: 25px;
            height: 25px;
            background-color: rgba(255, 255, 255, 0);
            margin-right: auto;
            margin-left: auto;
            border: 4px solid rgba(239, 74, 74, 1);
            left: 37px;
            top: 37px;
            position: absolute;
        }

        #page_first_object {
            opacity: 100;
            -webkit-animation: first_object 1s infinite;
            animation: first_object 1s infinite;
            -webkit-animation-delay: 0.5s;
            animation-delay: 0.5s;
        }

        #page_second_object {
            opacity: 100;
            -webkit-animation: second_object 1s infinite;
            animation: second_object 1s infinite;
        }

        #page_third_object {
            opacity: 100;
            -webkit-animation: third_object 1s infinite;
            animation: third_object 1s infinite;
            -webkit-animation-delay: 0.5s;
            animation-delay: 0.5s;
        }

        #page_forth_object {
            opacity: 100;
            -webkit-animation: forth_object 1s infinite;
            animation: forth_object 1s infinite;
        }

        @-webkit-keyframes first_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(150%, 150%) scale(2, 2);
                -webkit-transform: translate(150%, 150%) scale(2, 2);
                transform: translate(150%, 150%) scale(2, 2);
            }

            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }

        @keyframes first_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(150%, 150%) scale(2, 2);
                -webkit-transform: translate(150%, 150%) scale(2, 2);
                transform: translate(150%, 150%) scale(2, 2);
            }

            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }

        @-webkit-keyframes second_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(-150%, 150%) scale(2, 2);
                -webkit-transform: translate(-150%, 150%) scale(2, 2);
                transform: translate(-150%, 150%) scale(2, 2);
            }
            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }

        @keyframes second_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(-150%, 150%) scale(2, 2);
                -webkit-transform: translate(-150%, 150%) scale(2, 2);
                transform: translate(-150%, 150%) scale(2, 2);
            }
            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }

        @-webkit-keyframes third_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(-150%, -150%) scale(2, 2);
                -webkit-transform: translate(-150%, -150%) scale(2, 2);
                transform: translate(-150%, -150%) scale(2, 2);
            }
            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }

        @keyframes third_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(-150%, -150%) scale(2, 2);
                -webkit-transform: translate(-150%, -150%) scale(2, 2);
                transform: translate(-150%, -150%) scale(2, 2);
            }
            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }

        @-webkit-keyframes forth_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(150%, -150%) scale(2, 2);
                -webkit-transform: translate(150%, -150%) scale(2, 2);
                transform: translate(150%, -150%) scale(2, 2);
            }
            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }

        @keyframes forth_object {
            0% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
            50% {
                -ms-transform: translate(150%, -150%) scale(2, 2);
                -webkit-transform: translate(150%, -150%) scale(2, 2);
                transform: translate(150%, -150%) scale(2, 2);
            }
            100% {
                -ms-transform: translate(1, 1) scale(1, 1);
                -webkit-transform: translate(1, 1) scale(1, 1);
                transform: translate(1, 1) scale(1, 1);
            }
        }
    </style>
</head>
<body class="fixed-header fixed-navigation fixed-ribbon">
<header id="header" class="tb_al">
    <input type="hidden" data-url="${pageContext.request.contextPath}" id="indexUrl"
           data-base-url="<%=Constants.APIS_WEBSITE%>">
    <div id="logo-group" class="xg_hd_logo">
		<span id="logo" style="margin-top: 8px; cursor: pointer;"
              onclick="window.location.href = '${pageContext.request.contextPath}';"> <img
                style="width: 160px;" src="Content/themes/smart/img/logo.png"
                class="visible-xs" alt="附近酒行">
			<c:choose>
                <c:when test="${Power=='admin'}">
                    <img style="width: 160px;" src="Content/themes/smart/img/logo_pc_admin.png" class="hidden-xs"
                         alt="附近酒行">
                </c:when>
                <c:otherwise>
                    <img style="width: 160px;" src="Content/themes/smart/img/logo_pc.png" class="hidden-xs" alt="附近酒行">
                </c:otherwise>
            </c:choose>
		</span>
        <div class="ajax-dropdown" style="left:132px;">
            <div class="btn-group btn-group-justified" data-toggle="buttons">
                <label class="btn btn-default"> <input type="radio"
                                                       name="activity"
                                                       id="/api/message/getAdminMessages">
                    <span data-name="activityText">系统通222知 (<span id="newsCount"></span>)</span>
                </label>
            </div>
            <div class="ajax-notifications custom-scroll"></div>
            <span class="pull-right" id="activityLoadTime"> 加载时间:
				<button type="button" style="display: none;"
                        data-loading-text="&lt;i class='fa fa-refresh fa-spin'&gt;&lt;/i&gt; Loading..."
                        class="btn btn-xs btn-default pull-right">
					<i class="fa fa-refresh"></i>
				</button>
			</span>
        </div>
    </div>
    <div class="project-context hidden-xs ss_cai">
        <div id="hide-menu" class="btn-header pull-right">
			<span> <a href="javascript:void(0);" data-action="toggleMenu"
                      title="显示/隐藏导航菜单"><img src="Content/Home/image/le16.png"/></a>
			</span>
        </div>
    </div>

    <c:if test="${Power=='admin'}">
    <span id="activity" class="activity-dropdown"> <img
            src="Content/Home/image/zhong.png"/> <i class="fa"></i> <b
            id="newsBadge" class="badge">0</b>
	</span>
    </c:if>

    <div class="pull-right">
        <div id="hide-menu" class="btn-header pull-right visible-xs">
			<span> <i class="fa fa-user"></i> <a
                    href="javascript:void(0);" data-action="toggleMenu"
                    title="显示/隐藏导航菜单"><img src="Content/Home/image/le16.png"/></a>
			</span>
        </div>
        <div id="logout" class="btn-header transparent pull-right">
			<span> <a href="${pageContext.request.contextPath}/user/loginout" title="退出"
                      data-action="userLogout" data-logout-msg="您确定要退出吗?"><img
                    src="Content/Home/image/le15.png"/></a></span>
        </div>
        <div id="fullscreen" class="btn-header transparent pull-right">
			<span> <a href="javascript:void(0);"
                      data-action="launchFullscreen" title="全屏"><img
                    src="Content/Home/image/le14.png"/></a></span>
        </div>
    </div>
</header>
<aside id="left-panel" class="left-panel_2x">
    <div class="login-info">
		<span> <a href="javascript:void(0);" id="show-shortcut"
                  data-action="toggleShortcut"> <img
                src="Content/Home/image/male.png" alt="me" id="online" class="online"/>
				<div>
					<h5>欢迎您</h5>
					<p id="homeBannerFactory"></p>
				</div>
		</a>
		</span>
    </div>
    <nav>
        <ul>
            <c:if test="${Power=='admin'}">
            <li><a class="nav-wechat" href="/Home/Main" title="我的附近酒行"><img
                    src="Content/Home/image/le1.png"/><span>我的附近酒行</span></a></li>
            <li><a href="#"><img src="Content/Home/image/le2.png"/><span
                    class="menu-item-parent">用户管理</span></a>
                <ul>
                    <li><a class="nav-wechat" href="${pageContext.request.contextPath}/user/carriersList"
                           title="运营商"><span>运营商</span></a>
                    </li>

                    <li><a class="nav-wechat" href="${pageContext.request.contextPath}/user/vendorList"
                           title="商家"><span>商家</span></a></li>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/user/userList"
                           title="普通用户"><span>普通用户</span></a></li>
                </ul>
            </li>
            </c:if>
            <li><a href="#" title="营销系统"><img
                    src="Content/Home/image/le3.png"/><span>营销系统</span></a>
                <ul>
                    <c:if test="${Power=='admin'}">
                        <li><a class="nav-wechat" href="${pageContext.request.contextPath}/couponMain/Main"
                               title="优惠券"><span>优惠券</span></a></li>
                    </c:if>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/discover/discoverMain"
                           title="发现列表"><span>发现列表</span></a></li>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/promotion/index"
                           title="首页活动"><span>首页活动</span></a></li>
                </ul>
            </li>
            <c:if test="${Power=='admin'}">
            <li><a href="#"><img src="Content/Home/image/le4.png"/> <span
                    class="menu-item-parent">广告管理</span></a>
                <ul>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/adv/advList/CREATE_MANAGER_LISTIMAGE"
                           title="首页头图"><span>首页头图</span></a></li>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/adv/advList/CREATE_Sdpp_IMAGELIST"
                           title="十大品牌头图"><span>十大品牌头图</span></a></li>
                    <li><a class="nav-wechat" href="${pageContext.request.contextPath}/recommVendor/VendorMain"
                           title="十大品牌头图"><span>推荐店铺</span></a></li>
                </ul>
            </li>
            <li><a href="#"><img src="Content/Home/image/le5.png"/><span
                    class="menu-item-parent">交易信息</span></a>
                <ul>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/orderAdmin/showOrderList"
                           title="交易数据"> <span>订单管理</span></a></li>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/orderInfo/listOrderInfo"
                           title="交易数据"> <span>交易数据</span></a></li>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/settlement/index"
                           title="结算"> <span>结算</span></a></li>
                </ul>
            </li>
                <%--<li><a href="#"><img src="Content/Home/image/le7.png"/><span--%>
                <%--class="menu-item-parent">消息管理</span></a>--%>
                <%--<ul>--%>
                <%--<li><a class="nav-wechat"--%>
                <%--href="/AttentionCenter/AttentionCenter" title="系统消息"><span>系统消息</span></a>--%>
                <%--</li>--%>
                <%--</ul>--%>
                <%--</li>--%>
            <li><a href="#"><img src="Content/Home/image/priceMart.png" style="width: 20px;height:20px"/><span
                    class="menu-item-parent">酒币商城</span></a>
                <ul>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/tradedGoods/pageTradedGoods"
                           title="商家品牌库"><span>商品管理</span></a></li>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/goodsCashRecord/pageGoodsCashRecord"
                           title="商家品牌库"><span>兑换记录</span></a></li>
                </ul>
            </li>
            <li><a href="#"><img src="Content/Home/image/priceMart.png" style="width: 20px;height:20px"/><span
                    class="menu-item-parent">商品管理</span></a>
                <ul>
                    <li><a class="nav-wechat" style="    margin-left: 20px;"
                           href="${pageContext.request.contextPath}/goodsAttr/listGoodsAttr"
                           title="属性管理"><span>属性管理</span></a></li>
                    <li><a class="nav-wechat" style="    margin-left: 20px;"
                           href="${pageContext.request.contextPath}/goodsBrand/listGoodsBrand"
                           title="品牌管理"><span>品牌管理</span></a></li>
                    <li><a class="nav-wechat" style="    margin-left: 20px;"
                           href="${pageContext.request.contextPath}/goodsAdmin/listGoodsAdmin"
                           title="商品管理"><span>商品管理</span></a></li>
                    <li><a class="nav-wechat" style="    margin-left: 20px;"
                           href="${pageContext.request.contextPath}/goodsVerify/listGoodsVerify"
                           title="商品管理"><span>商品审核</span></a></li>
                </ul>
            </li>
                <li>
                    <a href="#"><img src="Content/Home/image/le8.png"/><span
                            class="menu-item-parent">客服管理</span></a>
                    <ul>
                        <li><a class="nav-wechat"
                               href="${pageContext.request.contextPath}/reportAdmin/showReportList"
                               title="举报管理"><span>举报管理</span></a></li>
                    </ul>
                </li>
            <li>
                <a href="#"><img src="Content/Home/image/le8.png"/><span
                        class="menu-item-parent">系统设置</span></a>
                <ul>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/pinpaiMain/Main"
                           title="商家品牌库"><span>商家品牌库</span></a></li>
                    <li><a class="nav-wechat"
                           href="${pageContext.request.contextPath}/ApkManager/apkMain"
                           title="APK设置"><span>Apk设置</span></a></li>
                </ul>
            </li>

            </c:if>
    </nav>
</aside>

<div id="main" role="main">
    <div id="ribbon" class="ribbon2">
			<span class="ribbon-button-alignment"> <a
                    href="javascript:window.location.reload()" id="refresh"
                    class="btn btn-ribbon" data-title="刷新页面" rel="tooltip"
                    data-placement="bottom" data-html="true" data-original-title="刷新页面"><img
                    src="Content/Home/image/le13.png"/></a>
			</span>
        <ol class="breadcrumb">
            <!-- This is auto generated -->
        </ol>
    </div>
    <div id="content"></div>
</div>
<%--<div class="page-footer">
    <div class="row">
        <div class="col-xs-12 col-sm-6">
            <span class="txt-color-white">赶酒会企业管理 &copy; 2015-2018${pageContext.request.contextPath}</span>
        </div>

        <div class="col-xs-6 col-sm-6 text-right hidden-xs">
            <div class="txt-color-white inline-block">
                <button class="btn btn-xs  txt-color-white" title="回到顶部"
                    onclick="commands['scroll up']()">
                    <i class="fa fa-arrow-circle-up fa-lg"></i>
                </button>
            </div>
        </div>
    </div>
</div>--%>
<script src="Scripts/libs/jquery-1.8.2.js"></script>
<script src="Scripts/libs/jquery-ui-1.10.3.js"></script>
<script src="Scripts/plugin/jquery.ui.touch-punch.js"></script>
<script src="Scripts/extend/CommonBase.min.js"></script>
<script src="Scripts/prototypeHelper.js"></script>
<script src="Scripts/app.config.js"></script>
<script src="Scripts/bootstrap/bootstrap.js"></script>
<script src="Scripts/notification/SmartNotification.js"></script>
<script src="Scripts/smartwidgets/jarvis.widget.js"></script>
<script src="Scripts/plugin/jquery.easy-pie-chart.js"></script>
<script src="Scripts/plugin/jquery.sparkline.js"></script>
<script src="Scripts/plugin/jquery.maskedinput.js"></script>
<script src="Scripts/plugin/select2.js"></script>
<script src="Scripts/plugin/bootstrap-slider.js"></script>
<script src="Scripts/plugin/jquery.mb.browser.js"></script>
<script src="Scripts/plugin/fastclick.js"></script>
<script src="Scripts/formLoading.js"></script>
<script src="Scripts/app.js"></script>
<script src="Scripts/jquery.chosen.js"></script>
<script src="Scripts/jquery.validate.min.js"></script>
<script src="Scripts/jquery.validate.messages_zh.min.js"></script>
<script src="Scripts/jquery.cookie.min.js"></script>
<script src="Scripts/common.js"></script>
<script src="Scripts/plugin/dropzone/dropzone.min.js"></script>
<script src="Scripts/plugin/datatables/jquery.dataTables.js"></script>
<script src="Scripts/plugin/datatables/dataTables.colVis.js"></script>
<script src="Scripts/plugin/datatables/dataTables.tableTools.js"></script>
<script src="Scripts/plugin/datatables/dataTables.bootstrap.js"></script>
<script src="Scripts/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="Scripts/confirm.js"></script>
<script src="Scripts/jqPagination/jquery.page.js"></script>
<script src="Scripts/vue.min.js"></script>
<script type="text/javascript" src="Scripts/libs/gjhImgUpload.js"></script>
<script src="Scripts/extend/VueExtend.min.js"></script>
<script src="Scripts/extend/ListModel.min.js"></script>
<script src="Scripts/extend/EditModel.min.js"></script>
<script type="text/javascript">
    function hideNotifications() {
        $("#activity").click();
    }

    //未登录跳转登录页
    if ($.cookie('access_token') == null || $.cookie('access_token') == undefined || $.cookie('access_token') == '') {
        window.location.href = "<%=Constants.APIS_WEBSITE%>/user/login";
    }

    try {
        var title = {
            "middleButton": {
                "title": '@ViewBag.Title', // 标题名,
                "buttonType": ""
            },
            "leftButton": {
                "exist": 'false', // 为true，加载func的方法，为false 返回app
                "func": ""
            }
        }

        appJs.setTitleBar(JSON.stringify(title));
    } catch (e) {
    }
    (function ($) {
        var token = encodeURIComponent($.cookie('access_token'));
        console.log(decodeURIComponent($.cookie('access_token')));
        if ('WebSocket' in window) {
            websocket = new WebSocket("ws://<%=Constants.SOCKET_URL%>/ws", token, {
                debug: true,
                maxReconnectAttempts: 4
            });
        } else if ('MozWebSocket' in window) {
            websocket = new MozWebSocket("ws://<%=Constants.SOCKET_URL%>/ws");
        } else {
            websocket = new SockJS("https://<%=Constants.SOCKET_URL%>/ws/sockjs/sockjs");
        }
        websocket.onopen = function (data) {
            try {
                console.log("websocket is open");
            } catch (err) {
                console.log(err)
            }
        }
        websocket.onmessage = function (data) {

            try {
                var count = parseInt($("#newsCount").text()) > 99 ? '99+' : parseInt($("#newsCount").text())
                $("#newsCount").text(count);
                $("#newsBadge").text(count);
            } catch (err) {
                console.log(err)
            }
        };
        websocket.onclose = function (data) {
            try {
                console.log("websocket is close");
            } catch (err) {
                console.log(err)
            }
        };
        decodeURI($.cookie('access_token'))
        $.ajax({
            url: "${pageContext.request.contextPath}/api/user/current?ticket=" + (new Date().getTime()),
            type: "get",
            success: function (ret) {
                if (ret && ret.success && ret.data) {
                    $("#homeBannerFactory").text(ret.data.nickname);
                    if (ret.data.headimage !== "" && ret.data.headimage !== null && ret.data.headimage !== undefined) {
                        $("#online").attr("src", ret.data.headimage)
                    }
                }
            }
        });
        $.ajax({
            url: "<%=Constants.APIS_WEBSITE%>/message/getNotReadingMessageCount",
            type: "get",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('access_token', token);
            },//这里设置header
            success: function (data) {
                if (data.success) {
                    var count = data.data > 99 ? '99+' : data.data
                    $("#newsCount").text(count);
                    $("#newsBadge").text(count);
                } else {
                    console.log(data.message)
                }
            }
        })
        /*

         * 公共方法
         */
        $.fn.initDatepicker = function (opt) {
            opt = $.extend(true, {
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                changeYear: true,
                dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
                dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
                monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                nextText: '<i class="fa fa-chevron-right"></i>',
                prevText: '<i class="fa fa-chevron-left"></i>',
                onClose: function (dateText, inst) {

                }
            }, opt);
            $(this).datepicker(opt);
        }
    })(jQuery);

    /*
     * 设置左侧菜单选中
     * #menu:string 菜单层级内容
     * #bread:string 顶部面包屑菜单内容
     * eq:setMenuChange("增值服务|摇一摇", "商家后台|增值服务|摇一摇|奖品管理");
     */
    function setMenuChange(menu, bread) {
        if (menu && menu.length > 0) {
            var menuArr = menu.split("|");
            var pli = $("nav").eq(0).find(".menu-item-parent:contains('" + menuArr[0] + "')").eq(0).parent().parent();
            if (pli.length > 0) {
                pli.addClass("open active");
                pli.find(".collapse-sign").eq(0).find("img").eq(0).attr("src", "Content/Home/image/le12.png");
                var ul = pli.children("ul").eq(0);
                if (ul.length > 0) {
                    ul.show();
                    var span = ul.find("span:contains('" + menuArr[1] + "')").eq(0).css({color: "#d32f2f"});
                }
            }
        }
        if (bread && bread.length > 0) {
            $(".breadcrumb").eq(0).html("");
            var strArr = bread.split("|");
            $.each(strArr, function (i, s) {
                if (s && s.length > 0) {
                    $(".breadcrumb").eq(0).append("<li>" + s + "</li>");
                }
            });
        }
    }
</script>
</body>
</html>
