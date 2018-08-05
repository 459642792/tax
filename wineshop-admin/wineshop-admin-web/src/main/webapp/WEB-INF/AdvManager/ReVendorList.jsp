<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <link rel="stylesheet" href="AdvManager/css/ReVendorList.css">
</head>
<body>
<div id="widget-grid">
    <div id="listContainer">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class='jarviswidget jarviswidget-color-white jarviswidget-sortable' id="wid-id-search"
                     data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                     style="position: relative; opacity: 1; margin-bottom:10px;"
                     data-widget-collapsed='@(HttpUtil.IsFromMobile(Request)?"true":"false")'>
                    <header role="heading">
                        <div class="sb">
                            <span>查询条件</span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <form id="search-form" class="smart-form" novalidate="novalidate">
                                <fieldset>
                                    <div class="row" style="margin-top:-22px">
                                        <section class="col col-4">
                                            <label class="label">商家名称</label>
                                            <label class="input">
                                                <input type="text" id="productName" name="productName" maxlength="30"
                                                       placeholder="请输入商家名称,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-4">
                                            <label class="label">所在地区</label>
                                            <label class="input">
                                                <input type="text" id="brande" name="brande" maxlength="30"
                                                       placeholder="请输入所在地区,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-4">
                                            <label class="label"></label>
                                            <label class="input">
                                                <footer>
                                                    <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                                    <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                                       href="javascript:;" data-toggle="modal" onclick="initForm()"
                                                       data-backdrop='static' data-target="#remoteModal">新增</a>
                                                </footer>
                                            </label>
                                        </section>
                                    </div>
                                </fieldset>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class="jarviswidget jarviswidget-color-darken jarviswidget-sortable" id="wid-id-list"
                     data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                     style="position: relative; opacity: 1;">
                    <header role="heading">
                        <div class="sb">
                            <span>推荐店铺列表 </span>
                        </div>
                    </header>
                    <div role="content" style="border:1px">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%"
                                       style="table-layout: fixed">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th width="5%" data-class="expand">序号</th>
                                        <th width="15%" data-class="expand">商家名</th>
                                        <th width="10%" data-class="expand">所在地区</th>
                                        <th width="15%" data-class="expand">运营商</th>
                                        <th width="10%" data-class="expand">发布时间</th>
                                        <th width="10%" data-class="expand">区域排序</th>
                                        <th width="10%" data-class="expand">点击量</th>
                                        <th width="10%" data-class="expand">发布人</th>
                                        <th data-class="expand">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--新增模块--%>
            <div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="width:800px; margin-left:-100px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title"><span class="titleType"></span>新增推荐店铺</h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="smart-form">
                                <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
                                    <section>
                                        <div class="row">
                                            <label class="label col col-2">所在地区</label>
                                            <div class="col col-10">
                                                <label class="input">
                                                    <select class="input-sm" id="Province" name="Province"
                                                            style="width:150px">
                                                    </select>
                                                    <select class="input-sm Town" id="city" name="city"
                                                            style="display: none;width:150px">
                                                    </select>
                                                    <select class="input-sm Town" id="county" name="county"
                                                            style="display: none;width:150px">
                                                    </select>
                                                </label>
                                            </div>
                                        </div>
                                    </section>
                                    <section>
                                        <div class="row">
                                            <label class="label col col-2">商家名</label>
                                            <div class="col col-10">
                                                <label class="input">
                                                    <select class="input-sm Town" id="vendorName" name="county"
                                                            style="width:463px" disabled="disabled">
                                                        <option value="0">--请选择商家--</option>
                                                    </select>
                                                </label>
                                            </div>
                                        </div>
                                    </section>
                                    <%-- <section>
                                         <div class="row">
                                             <label class="label col col-2">商品详情</label>
                                             <div class="col col-10">
                                                 <!-- 加载编辑器的容器 -->
                                                 <script id="container" name="content" type="text/plain">

                                                 </script>
                                             </div>
                                         </div>
                                     </section>--%>
                                </fieldset>
                                <footer>
                                    <button type="button" data-id="" class="btn btn-primary" id="addSubmit">
                                        <i class="fa fa-save"></i>
                                        保存
                                    </button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                        取消
                                    </button>
                                </footer>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <%--编辑模块--%>
            <div class="modal fade" id="countModal" role="dialog" tabindex="-1" aria-labelledby="countModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="width:800px; margin-left:-100px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title">推荐管理</h4>
                        </div>
                        <div class="modal-body " style="padding: 0">
                            <div role="content" class="smart-form" style="border:1px">
                                <section style="padding:0 20px">

                                    <div class="widget-body">
                                        <div class="dataTables_wrapper form-inline no-footer"
                                             style="position:relative;">
                                            <h5 style="padding: 10px 0">
                                                当前区域已存在推荐店铺，您可以进行排序和推荐管理。
                                            </h5>
                                            <table id="dt_count" class="table table-bordered"
                                                   style="width: 100%;table-layout: fixed">
                                                <thead>
                                                <tr>
                                                    <th width="100px" style="text-align: center;">排序</th>
                                                    <th style="text-align: center;">商家名</th>
                                                    <th width="150px" style="text-align: center;">操作</th>
                                                </tr>
                                                </thead>
                                                <tbody>

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </section>
                                <footer>
                                    <button type="button" data-id="" class="btn btn-primary" id="sortSub">
                                        <i class="fa fa-save"></i>
                                        保存
                                    </button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">
                                        取消
                                    </button>
                                </footer>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>"
       data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src="AdvManager/js/ReVendorList.js"></script>
</body>
</html>
