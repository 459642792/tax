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
          href="${pageContext.request.contextPath}/goods/modelGoods/css/model_goods_up_list.css"/>
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
                            <form id="search-form" class="smart-form"
                                  novalidate="novalidate">
                                <fieldset>
                                    <div class="row" style="margin-top: -22px">
                                        <section class="col col-3">
                                            <label class="label">品牌名</label>
                                            <label class="input">
                                                <input type="text" id="pinpai_name" maxlength="10"
                                                       placeholder="请输入品牌名,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-3">
                                            <label class="label">商品名</label>
                                            <label class="input">
                                                <input type="text" id="goods_name" maxlength="10"
                                                       placeholder="请输入商品名,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-3">
                                            <label class="label">添加时间</label>
                                            <label class="input">
                                                <i class="icon-append fa fa-calendar"></i>
                                                <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                       id="model_goods_startTime" placeholder="添加开始时间">
                                            </label>
                                        </section>
                                        <section class="col col-3">
                                            <label class="label">&nbsp;</label>
                                            <label class="input">
                                                <i class="icon-append fa fa-calendar"></i>
                                                <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                       id="model_goods_endTime" placeholder="添加结束时间">
                                            </label>
                                        </section>
                                    </div>
                                </fieldset>
                                <footer>
                                    <button id="btnSearch" class="btn btn-primary atncol_c">查询</button>
                                    <button type="button" class="btn btn-primary navbar-btn" id="add_model_goods">
                                        添加
                                    </button>
                                    <%--<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#model_goods_details" id="view_model_goods">--%>
                                    <button class="btn btn-primary navbar-btn" data-toggle="modal"
                                            id="view_model_goods">
                                        查看
                                    </button>
                                    <button type="button" class="btn btn-default navbar-btn" id="down_model_goods">
                                        下架
                                    </button>
                                </footer>
                            </form>
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
                            <span>上架管理 </span>
                        </div>
                    </header>
                    <div role="content" style="border: 1px">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper"
                                 class="dataTables_wrapper form-inline no-footer"
                                 style="position: relative;">
                                <table id="model_goods_up_list"
                                       class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th data-class="expand"></th>
                                        <th data-class="expand">商品名</th>
                                        <th data-class="expand">品牌
                                        </td>
                                        <th data-class="expand">类别</th>
                                        <th data-class="expand">产地
                                        </td>
                                        <th data-class="expand">包装
                                        </td>
                                        <th data-class="expand">厂家指导价（元）</th>
                                        <th data-class="expand">匹配店铺数</th>
                                        <th data-class="expand">添加时间</th>
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


<div class="modal fade" id="save_model_goods" tabindex="-1" role="dialog"
     aria-labelledby="add_moodel_goods_myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content smart-form" style="width: 900px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">创建商品</h4>
            </div>
            <div class="modal-body no-padding">
                <table class="table able-striped table-bordered" id="table_save_tr">
                    <tr>
                        <td data-value="" name="" style="width:20%;text-align: center">类别</td>
                        <td style='width:20%'>
                            <select id="save_shop_type_name" style=" min-width: 180px;" onchange="selectShopType(this)">
                            </select>
                        </td>
                    </tr>
                    <tr id="save_model_goods_table">
                        <td id="" data-value="" name="" style="width:20%;text-align: center">品牌</td>
                        <td>
                            <select id="save_pinpai_name" style=" min-width: 180px;"> </select>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" style="width: 50px;height: 32px;">
                    取消
                </button>
                <button type="button" class="btn btn-primary" id="addDictionarySubmit"
                        style="width: 50px;height: 32px;">确定
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="model_goods_details" tabindex="-1" role="dialog" aria-labelledby="model_goods_details_Label"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 900px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">商品详情</h4>
            </div>
            <div class="modal-body" style="padding: 0px;">
                <div class="container" style="width: 100%;padding: 0px;">
                    <div class="row" style="width: 100%;">
                        <div class="span3">
                            <ul id="myTab" class="nav nav-tabs">
                                <li class="in active" style="margin-top: 5px;"><a href="#model_goods_definite_details"
                                                                                  data-toggle="tab"
                                                                                  style=" width: 100px;margin-left: 380px">详情</a>
                                </li>
                                <li style="margin-top: 5px;"><a href="#relevance_vendor" data-toggle="tab"
                                                                style=" width: 100px;">关联店铺</a></li>
                            </ul>
                        </div>
                    </div>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane fade in active" id="model_goods_definite_details" style="width: 100%">
                            <div>
                                <div class="modal-body no-padding">
                                    <table class="table able-striped table-bordered" id="table_details_tr">
                                        <tr>
                                            <td data-value="" name="" style="width:20%;text-align: center">类别</td>
                                            <td style='width:20%;'>
                                                <select id="details_shop_type_name" disabled="true"
                                                        style="background: #ddd; min-width: 180px;"
                                                        onchange="selectShopType(this)">
                                                </select>
                                            </td>
                                        </tr>
                                        <tr id="details_model_goods_table">
                                            <td id="" data-value="" name="" style="width:20%;text-align: center">所属品牌
                                            </td>
                                            <td>
                                                <select id="details_pinpai_name"
                                                        style="    min-width: 180px;"> </select>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal"
                                            style="width: 50px;height: 32px;">取消
                                    </button>
                                    <button type="button" class="btn btn-primary" id="updateModelGoodsSubmit"
                                            style="width: 50px;height: 32px;">确定
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="relevance_vendor" style="width: 100%">
                            <div class="relevance_vendor_Table" id="infoTable">
                                <table id="relevance_vendor_infoesTable" style="width: 100%"
                                       class="table table-bordered" style="table-layout: fixed">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th style="width: 20px;text-align: center"></th>
                                        <th style="width: 200px;text-align: center" data-class="expand">店铺名</th>
                                        <th style="width: 80px;text-align: center" width="80px" data-class="expand">
                                            联系电话
                                        </th>
                                        <th style="width: 200px;text-align: center" data-class="expand">所在地区</th>
                                        <th style="width: 160px;text-align: center" data-class="expand">添加时间</th>
                                        <th style="width:60px;text-align:  data-class=" expand
                                        " >操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="modelGoodsId">
                </div>
            </div>
            <%--<div class="modal-footer">--%>
            <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>--%>
            <%--<button type="button" class="btn btn-primary">提交更改</button>--%>
            <%--</div>--%>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<div class="modal fade" id="vendor_goods_detail" tabindex="-1" role="dialog"
     aria-labelledby="vendor_goods_detail_modal">
    <div class="modal-dialog" role="document" style="width: 1100px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">商品详情</h4>
            </div>
            <div class="infoContainer">
                <div class="modal-body no-padding" id="detail_body_goods">
                    <table class="table able-striped table-bordered" id="table_vendor_goods_detail">
                        <tr id="save_table_vendor_goods_detail">
                            <td id="" data-value="" name="" style="width:20%;text-align: center">品牌</td>
                            <td>
                                <input type="text" id="save_vendor_pinpai_name" disabled="disabled"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="down_model_goods_model" tabindex="-1" role="dialog"
     aria-labelledby="down_model_goodsl_label">
    <div class="modal-dialog" role="document" style="width: 700px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">下架商品</h4>
            </div>
            <div class="infoContainer">
                <div class="modal-body no-padding" id="down_model_goods_body">

                    <table class="table able-striped table-bordered" id="table_down_model_goods">
                        <tr>
                            <td colspan="2">下架后所有店铺都将下架该商品，是否继续？</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td width="width:30%">商品名&nbsp;&nbsp;:</td>
                            <td id="down_model_goods_name">
                            </td>
                        </tr>
                        <tr>
                            <td>所属品牌:</td>
                            <td id="down_model_goods_pinpai">

                            </td>
                        </tr>
                        <tr>
                            <td>条形编码:</td>

                            <td id="down_model_goods_shape_code">

                            </td>
                        </tr>
                        <tr>
                            <td>关联店铺:</td>
                            <td id="down_model_goods_vendor">

                            </td>
                        </tr>

                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            style="width: 50px;height: 32px;">取消
                    </button>
                    <button type="button" class="btn btn-primary" id="downModelGoodsSubmit"
                            style="width: 50px;height: 32px;">确定
                    </button>
                </div>
                <input type="hidden" id="downGoodsId">
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="down_model_goods_password_model" tabindex="-1" role="dialog"
     aria-labelledby="down_model_goodsl_password_label">
    <div class="modal-dialog" role="document" style="width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">输入密码</h4>
            </div>
            <div class="infoContainer">
                <div class="modal-body no-padding">
                    <table class="table able-striped table-bordered" id="table_down_model_goods_password">
                        <tr>
                            <td>密码：</td>
                            <td>
                                <input type="password" id="down_model_goods_password">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            style="width: 50px;height: 32px;">取消
                    </button>
                    <button type="button" class="btn btn-primary" id="passwordModelGoodsSubmit"
                            style="width: 50px;height: 32px;">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px; margin-left:-100px;"></div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>"
       data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src=" Scripts/uEdiotr/ueditor.config.js"></script>
<script type="text/javascript" src="Scripts/uEdiotr/ueditor.all.min.js"></script>
<script src="${pageContext.request.contextPath}/goods/modelGoods/js/model_goods_up_list.js"></script>
</body>
</html>
