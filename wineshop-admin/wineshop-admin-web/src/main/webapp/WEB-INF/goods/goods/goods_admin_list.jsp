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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/goods/goods/css/goods_admin_list.css"/>
</head>
<body>
<div id="widget-grid">
    <div id="listContainer">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div
                        class='jarviswidget jarviswidget-color-white jarviswidget-sortable'
                        id="wid-id-search" data-widget-editbutton="false"
                        data-widget-deletebutton="false" data-widget-colorbutton="false"
                        style="position: relative; opacity: 1; margin-bottom: 10px;"
                        data-widget-collapsed='@(HttpUtil.IsFromMobile(Request)?"true":"false")'>
                    <header role="heading">
                        <div class="sb">
                            <span>查询条件 </span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <div id="search-form" class="smart-form" novalidate="novalidate">
                                <fieldset>
                                    <div id="adminTypes" class="row" style="margin-top: -22px">
                                        <!--品类-->
                                        <section class="col col-3">
                                            <div class="brandType">
                                                <label class="label title">品类</label>
                                                <label id="adminType"></label>
                                            </div>
                                        </section>
                                        <!--知道价格-->
                                        <section class="col col-3">
                                            <div class="searchPrice">
                                                <label class="label title">指导价</label>
                                                <label class="input">
                                                    <input type="text" class="form-control" placeholder="请输入开始价格" id="suggestPriceFrom" >
                                                    <input type="text" class="form-control" placeholder="请收入结束价格" id="suggestPriceTo" >
                                                </label>
                                            </div>
                                        </section>
                                        <section class="col col-3">
                                            <div class="inputSearch">
                                                <label class="label title">商品名称、品牌、条形码</label>
                                                <label class="input">
                                                    <input type="text"  id="search_value" placeholder="输入品牌、商品名或条形码">
                                                </label>
                                            </div>
                                        </section>
                                    </div>
                                </fieldset>
                                <footer>
                                    <button onclick="searchProductList()" class="btn btn-primary atncol_c">查询</button>
                                    <button type="button" class="btn btn-primary navbar-btn" id="add_model_goods" onclick="addProducts()">
                                        添加商品
                                    </button>
                                </footer>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div
                        class="jarviswidget jarviswidget-color-darken jarviswidget-sortable"
                        id="wid-id-list" data-widget-editbutton="false"
                        data-widget-deletebutton="false" data-widget-colorbutton="false"
                        style="position: relative; opacity: 1;">
                    <header role="heading">
                        <div class="sb">
                            <span>商品列表 </span>
                        </div>
                    </header>
                    <div role="content" style="border: 1px">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper"
                                 class="dataTables_wrapper form-inline no-footer"
                                 style="position: relative;">
                                <table id="goods_admin_list"
                                       class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th width="20%">名称</th>
                                        <th width="10%">品牌</td>
                                        <th width="10%">子品牌</th>
                                        <th width="10">条形码</td>
                                        <th width="5%">酒精度</th>
                                        <th width="5%">规格</td>
                                        <th width="5%">包装</td>
                                        <th width="5%">品类</th>
                                        <th width="10%" class="clickSort"  onclick="goodsBySort('suggestPriceTag',this)">指导价</th>
                                        <th width="5%" class="clickSort"   onclick="goodsBySort('goodsStateTag',this)">状态</th>
                                        <th width="10%">操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--商品新增模块-->
<div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px; margin-left:-100px;" id="a">
            <jsp:include page="goods_admin_add.jsp"></jsp:include>
        </div>
    </div>
</div>
<!--商品编辑模块-->
<div class="modal fade" id="remoteEdit" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px; margin-left:-100px;">
            <jsp:include page="goods_admin_edit.jsp"></jsp:include>
        </div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>"
       data-url="${pageContext.request.contextPath}">
<script src="${pageContext.request.contextPath}/goods/goods/js/goods_admin_list.js"></script>

</body>
</html>
