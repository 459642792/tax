<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page import="com.blueteam.base.constant.Constants" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/goods/brand/css/goods_brand_list.css"/>
</head>
<body class="brandListMain">
<div id="widget-grid">
    <!--头部信息编写开始-->
    <div class="brandListHeader col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <div class='jarviswidget jarviswidget-color-white jarviswidget-sortable'>
            <header role="heading">
                <div class="sb"><span>查询条件 </span></div>
            </header>
            <div role="content">
                <form id="search-form" class="smart-form" novalidate="novalidate">
                    <fieldset>
                        <div class="row">
                            <section class="col col-3">
                                <label class="label">品牌名称:</label>
                                <label class="input">
                                    <input type="text" maxlength="30" placeholder="输入品牌名称" id="searchBrandName">
                                </label>
                            </section>
                            <section class="col col-3">
                                <label class="label">更新时间:</label>
                                <label class="input">
                                    <i class="icon-append fa fa-calendar"></i>
                                    <input type="text" readonly class="form-control" data-date-format="yyyy-mm-dd" placeholder="开始时间" id="searchStartDate"/>
                                    <i class="clearDate" onclick="clearDate('searchStartDate')"><img src="Content/Home/image/close_gray.png"></i>
                                </label>
                            </section>
                            <section class="col col-3">
                                <label class="label">&nbsp;</label>
                                <label class="input">
                                    <i class="icon-append fa fa-calendar"></i>
                                    <input type="text" readonly class="form-control" data-date-format="yyyy-mm-dd" placeholder="结束时间" id="searchEndDate">
                                    <i class="clearDate" onclick="clearDate('searchEndDate')"><img src="Content/Home/image/close_gray.png"></i>
                                </label>
                            </section>
                        </div>
                        <div class="row">
                            <section class="col col-3 brandAble">
                                状态:
                                <label for="checkboxEnable"><input type="checkbox" name="able" id="checkboxEnable" value="0">禁用 </label>
                                <label for="checkboxDisable"><input type="checkbox" name="able" id="checkboxDisable" value="1">启用 </label>
                            </section>
                        </div>
                    </fieldset>
                    <footer>
                        <a class="btn btn-primary atncol_c" onclick="searchBrandList()">查询</a>
                        <a class="btn btn-success " onclick="updateBrand('','marinBrandAdd')">添加主品牌</a>
                    </footer>
                </form>
            </div>
        </div>
    </div>
    <!--品牌管理列表-->
    <div class="brandList col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <div class="jarviswidget jarviswidget-color-darken jarviswidget-sortable"
             id="wid-id-list" data-widget-editbutton="false"
             data-widget-deletebutton="false" data-widget-colorbutton="false"
             style="position: relative; opacity: 1;">
            <header role="heading">
                <div class="sb"><span>品牌列表</span></div>
            </header>
            <table id="brandListTable" ></table>
        </div>
    </div>
    <!--添加子品牌信息-->
    <jsp:include page="goods_brand_addUpdate.jsp"></jsp:include>
</div>
<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>" data-url="${pageContext.request.contextPath}">
<script src="${pageContext.request.contextPath}/goods/brand/js/goods_brand_list.js"></script>

</body>
</html>
