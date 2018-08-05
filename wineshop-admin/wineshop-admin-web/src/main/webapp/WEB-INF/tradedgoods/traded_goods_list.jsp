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
    <link rel="stylesheet" href="tradedgoods/css/traded_goods_list.css">
    <style>
        #dt_basic_processing {
            display: none !important;
        }
    </style>
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
                                            <label class="label">商品名称</label>
                                            <label class="input">
                                                <input type="text" id="productName" name="productName" maxlength="30"
                                                       placeholder="请输入标题,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-4">
                                            <label class="label">品牌</label>
                                            <label class="input">
                                                <input type="text" id="brande" name="brande" maxlength="30"
                                                       placeholder="请输入类型,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-4">
                                            <label class="label">状态</label>
                                            <label class="input">
                                                <select id="productStatus" name="productStatus" class="form-control">
                                                    <option selected="selected">全部</option>
                                                    <option value="1">上架</option>
                                                    <option value="0">下架</option>
                                                </select>
                                            </label>
                                        </section>
                                    </div>
                                </fieldset>
                                <footer>
                                    <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                    <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                       href="javascript:;" data-toggle="modal" onclick="initForm()"
                                       data-backdrop='static' data-target="#remoteModal">新增商品</a>
                                </footer>
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
                            <span>商品管理列表 </span>
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
                                        <th width="10%" data-class="expand"></th>
                                        <th width="10%" data-class="expand">图片</th>
                                        <th width="10%" data-class="expand">商品名称</th>
                                        <th width="10%" data-class="expand">品牌</th>
                                        <th width="10%" data-class="expand">酒币</th>
                                        <th width="10%" data-class="expand">兑换记录（笔）</th>
                                        <th width="10%" data-class="expand">状态</th>
                                        <th width="30%" data-class="expand">操作</th>
                                    </tr>
                                    </thead>
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
                            <h4 class="modal-title"><span class="titleType"></span>商品</h4>
                        </div>
                        <div class="modal-body no-padding">
                            <form class="smart-form">
                                <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
                                    <section>
                                        <div class="row">
                                            <label class="label col col-2">商品名称</label>
                                            <div class="col col-10">
                                                <label class="input">
                                                    <i class="icon-append fa fa-fw fa-info-circle"></i>
                                                    <input type="text" id="addTitle" name="title" class="input"
                                                           maxlength="35" placeholder="请输入商品名"/>
                                                    <b class="tooltip tooltip-top-right">请输入商品名</b>
                                                </label>
                                            </div>
                                        </div>
                                    </section>
                                    <section>
                                        <div class="row">
                                            <label class="label col col-2">品牌</label>
                                            <div class="col col-10">
                                                <label class="input">
                                                    <i class="icon-append fa fa-fw fa-info-circle"></i>
                                                    <input type="text" id="addBrand" name="title" class="input"
                                                           maxlength="35" placeholder="请输入品牌名"/>
                                                    <b class="tooltip tooltip-top-right">请输入品牌名</b>
                                                </label>
                                            </div>
                                        </div>
                                    </section>
                                    <section>
                                        <div class="row">
                                            <label class="label col col-2">酒币</label>
                                            <div class="col col-10">
                                                <label class="input">
                                                    <i class="icon-append fa fa-fw fa-info-circle"></i>
                                                    <input type="text" id="addGoodsPrice" name="title" class="input"
                                                           maxlength="8" placeholder="请输入酒币"/>
                                                    <b class="tooltip tooltip-top-right">请输入酒币</b>
                                                </label>
                                            </div>
                                        </div>
                                    </section>
                                    <section>
                                        <div class="row">
                                            <label class="label col col-2">商品头图</label>
                                            <div class="col col-10">
                                                <label class="input">
                                                    <div style="float:left;list-style: none;margin-right: 20px"
                                                         id="coverImg">
                                                        <img style="z-index:0;width: 100px;height: 100px;"
                                                             src="Content/themes/smart/img/loadImg.png" alt="">
                                                        <input type="file" name="Upload" value=""
                                                               accept="image/jpg,image/jpeg,image/png" class="fileQj"
                                                               style="z-index:1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;"/>
                                                    </div>
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
            <%--查看详情--%>
            <div class="modal fade" id="countModal" role="dialog" tabindex="-1" aria-labelledby="countModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="width:800px; margin-left:-100px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title">兑换记录</h4>
                        </div>
                        <div class="modal-body no-padding">
                            <div role="content" style="border:1px">
                                <section style="padding:20px">

                                    <div class="widget-body no-padding">
                                        <div class="dataTables_wrapper form-inline no-footer"
                                             style="position:relative;">
                                            <table id="dt_count" class="table table-striped table-bordered table-hover "
                                                   style="width: 100%;table-layout: fixed">
                                                <thead>
                                                <tr>
                                                    <th></th>
                                                    <th width="135px style=" text-align: center;
                                                    ">订单号</th>
                                                    <th width="135px" style="text-align: center;">兑换商家</th>
                                                    <th width="50px" style="text-align: center;">数量</th>
                                                    <th width="auto" style="text-align: center;">收货地址</th>
                                                    <th width="115px" style="text-align: center;">兑换时间</th>
                                                    <th width="70px" style="text-align: center;">状态</th>
                                                </tr>
                                                </thead>
                                                <tbody>

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </section>
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
<%--<script type="text/javascript" src=" Scripts/uEdiotr/ueditor.config.js"></script>
<script type="text/javascript" src="Scripts/uEdiotr/ueditor.all.js"></script>--%>
<script type="text/javascript" src="tradedgoods/js/traded_goods_list.js"></script>
</body>
</html>
