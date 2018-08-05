<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <link rel="stylesheet" href="goodsCashRecord/css/goods_cash_record.css">
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
                            <span>查询条件 </span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <div class="">
                                <form id="search-form" class="smart-form" novalidate="novalidate">
                                    <fieldset>
                                        <div class="row" style="margin-top: -22px">
                                            <section class="col col-2">
                                                <label class="label">订单号</label>
                                                <label class="input">
                                                    <input type="text" id="orderNumber" name="orderNumber"
                                                           placeholder="请输入订单号,支持模糊查找">
                                                </label>
                                            </section>
                                            <section class="col col-2">
                                                <label class="label">兑换商家</label>
                                                <label class="input">
                                                    <input type="text" id="vendorInfoName" name="vendorInfoName"
                                                           placeholder="请输入兑换商家,支持模糊查找">
                                                </label>
                                            </section>
                                            <section class="col col-2">
                                                <label class="label">状态</label>
                                                <label class="input">
                                                    <select style="height: 30px;" name="discoverStatus"
                                                            class="form-control" name="cashStatus" id="cashStatus">
                                                        <option selected="selected" value=9>全部</option>
                                                        <option value=0>未发货</option>
                                                        <option value=1>已发货</option>
                                                    </select>
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="label">开始时间</label>
                                                <label class="input">
                                                    <i class="icon-append fa fa-calendar"></i>
                                                    <input type="text" class="form-control"
                                                           data-date-format="yyyy-mm-dd" id="beginTime" name="beginTime"
                                                           placeholder="兑换记录开始时间">
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="label">结束时间</label>
                                                <label class="input">
                                                    <i class="icon-append fa fa-calendar"></i>
                                                    <input type="text" class="form-control"
                                                           data-date-format="yyyy-mm-dd" id="endTime" name="endTime"
                                                           placeholder="兑换记录结束时间">
                                                </label>
                                            </section>
                                        </div>
                                    </fieldset>
                                    <footer>
                                        <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                    </footer>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="">
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div class="jarviswidget jarviswidget-color-darken jarviswidget-sortable" id="wid-id-list"
                         data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                         style="position: relative; opacity: 1;">
                        <header role="heading">
                            <div class="sb">
                                <span>兑换记录列表 </span>
                            </div>
                        </header>
                        <div role="content" style="border:1px">
                            <div class="widget-body no-padding">
                                <div id="dt_basic_wrapper" class="dataTables_wrapper form-inline no-footer"
                                     style="position:relative;">
                                    <table id="dt_basic" class="table table-striped table-bordered table-hover"
                                           width="100%">
                                        <thead>
                                        <tr>
                                            <th data-class="expand"></th>
                                            <th data-class="expand" width="2%"></th>
                                            <th data-class="expand" width="10%">订单号</th>
                                            <th data-hide="phone,tablet" width="8%">商品名称</th>
                                            <th data-hide="phone,tablet" width="4%">酒币</th>
                                            <th data-hide="phone,tablet" width="4%">数量</th>
                                            <th data-hide="phone,tablet" width="13%">兑换商家</th>
                                            <th data-hide="phone,tablet" width="13%">所在地区运营商</th>
                                            <th data-hide="phone,tablet" width="10%">兑换时间</th>
                                            <th data-hide="phone,tablet">收货地址</th>
                                            <th data-hide="phone,tablet" width="5%">状态</th>
                                            <th width="10%">操作</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--发货--%>
                <div class="modal fade" id="expressGoodsModal" role="dialog" tabindex="-1"
                     aria-labelledby="expressGoodsModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content" style="width:800px; margin-left:-100px;">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                    &times;
                                </button>
                                <h4 class="modal-title">发货</h4>
                            </div>
                            <div class="modal-body no-padding">
                                <form class="smart-form">
                                    <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
                                        <section>
                                            <div class="row">
                                                <h3 style="text-align: center">请输入物流信息</h3>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <div class="col col-5">
                                                    <label class="input">
                                                        <input type="text" id="expressCompany"
                                                               style="width: 200px;float: right" name="title"
                                                               class="input" maxlength="35" placeholder="请输入快递公司"/>
                                                        <b class="tooltip tooltip-top-right">请输入快递公司</b>
                                                    </label>
                                                </div>
                                                <div class="col col-7">
                                                    <label class="input">
                                                        <input type="text" id="expressNumbers" name="title"
                                                               style="width: 400px;float: left" class="input"
                                                               maxlength="35" placeholder="请输入快递单号"/>
                                                        <b class="tooltip tooltip-top-right">请输入快递单号</b>
                                                    </label>
                                                </div>
                                            </div>
                                        </section>
                                    </fieldset>
                                    <footer>
                                        <button type="button" data-id="" class="btn btn-primary" id="expressSubmit">
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
                <%--查看--%>
                <input type="hidden" id="modalId">
                <div class="modal fade" id="getGoodsCashModal" role="dialog" tabindex="-1"
                     aria-labelledby="getGoodsCashModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content" style="width:800px; margin-left:-100px;">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                    &times;
                                </button>
                                <h4 class="modal-title">兑换记录</h4>
                            </div>
                            <div class="modal-body no-padding">
                                <form class="smart-form">
                                    <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">订单号</label>
                                                <label class="col col-10" id="cashOrderNumber"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">商品名称</label>
                                                <label class="col col-10" id="productName"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">酒币</label>
                                                <label class="col col-10" id="cashCashPrice"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">数量</label>
                                                <label class="col col-10" id="cashCounts"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">兑换商家</label>
                                                <label class="col col-10" id="cashShop"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">收货地址</label>
                                                <label class="col col-10" id="caseUserAdress"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">兑换时间</label>
                                                <label class="col col-10" id="cashCreateDate"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">发货时间</label>
                                                <label class="col col-10" id="cashExpressDate"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">物流信息</label>
                                                <label class="col col-10" id="cashExpressInfo"
                                                       style="padding-top: 7px;"></label>
                                            </div>
                                        </section>
                                    </fieldset>
                                    <footer>
                                        <button type="button" class="btn btn-default" data-dismiss="modal">
                                            关闭
                                        </button>
                                    </footer>
                                </form>
                            </div>
                        </div>
                    </div>
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
<input type="hidden" id="baseUrl" data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src="goodsCashRecord/js/goods_cash_record.js"></script>
</body>
</html>
