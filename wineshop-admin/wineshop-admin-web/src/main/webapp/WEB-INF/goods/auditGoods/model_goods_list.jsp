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
          href="${pageContext.request.contextPath}/goods/auditGoods/css/model_goods_list.css"/>
</head>
<body>
<div id="widget-grid">
    <div id="listContainer">
        <div class="row">
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
                                        <label class="label">提交时间</label>
                                        <label class="input">
                                            <i class="icon-append fa fa-calendar"></i>
                                            <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                   id="audit_goods_startTime" placeholder="添加开始时间">
                                        </label>
                                    </section>
                                    <section class="col col-3">
                                        <label class="label">&nbsp;</label>
                                        <label class="input">
                                            <i class="icon-append fa fa-calendar"></i>
                                            <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                   id="audit_goods_endTime" placeholder="添加结束时间">
                                        </label>
                                    </section>
                                </div>
                            </fieldset>
                            <footer>
                                <button id="btnSearch" class="btn btn-primary atncol_c">查询</button>
                                <%--<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#model_goods_details" id="view_model_goods">--%>
                                <button type="button" class="btn btn-primary navbar-btn" id="aduit_goods">
                                    审核
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
                        <span>审核管理 </span>
                    </div>
                </header>
                <div role="content" style="border: 1px">
                    <div class="widget-body no-padding">
                        <div id="dt_basic_wrapper"
                             class="dataTables_wrapper form-inline no-footer"
                             style="position: relative;">
                            <table id="audit_goods_list"
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
                                    <th data-class="expand">价格</th>
                                    <th data-class="expand">提交店铺</th>
                                    <th data-class="expand">提交时间</th>
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


<div class="modal fade" id="audit_goods_detail" tabindex="-1" role="dialog"
     aria-labelledby="audit_goods_detail_modal">
    <div class="modal-dialog" role="document" style="width: 900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">商品审核</h4>
            </div>
            <div class="infoContainer">
                <div class="modal-body no-padding" id="detail_body_goods">
                    <table class="table able-striped table-bordered" id="table_audit_goods_detail">
                        <tr id="audit_goods_detail_pinpai">
                            <td id="" data-value="" name="" style="width:20%;text-align: center">品牌</td>
                            <td>
                                <select name="" type="text" disabled="disabled" id="audit_pinpai_name"></select>
                            </td>
                        </tr>
                    </table>
                </div>
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
<script src="${pageContext.request.contextPath}/goods/auditGoods/js/model_goods_list.js"></script>
</body>
</html>
