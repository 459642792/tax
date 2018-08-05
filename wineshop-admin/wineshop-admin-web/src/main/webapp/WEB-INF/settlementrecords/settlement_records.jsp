<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="settlementrecords/css/settlement_records.css"/>
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
                                        <section class="col col-3"><label class="label">商家地区</label>
                                            <label class="input"> <input type="text" maxlength="15"
                                                                         id="content_tradingArea"
                                                                         placeholder="请输入活动名称,支持模糊查找">
                                            </label></section>
                                        <section class="col col-3"><label class="label">最后结算日期:</label>
                                            <label class="input"> <i
                                                    class="icon-append fa fa-calendar"></i> <input type="text"
                                                                                                   class="form-control"
                                                                                                   data-date-format="yyyy-mm-dd"
                                                                                                   id="content_beginTime"
                                                                                                   placeholder="开始时间">
                                            </label></section>
                                        <section class="col col-3"><label class="label">&nbsp;</label>
                                            <label class="input"> <i
                                                    class="icon-append fa fa-calendar"></i> <input type="text"
                                                                                                   class="form-control"
                                                                                                   data-date-format="yyyy-mm-dd"
                                                                                                   id="content_endTime"
                                                                                                   placeholder="结束时间">
                                            </label></section>

                                        <section class="col col-3"><label class="label">店铺名称</label>
                                            <label class="input"> <input type="text" maxlength="15"
                                                                         id="content_name" placeholder="请输入店铺名称,支持模糊查找">
                                            </label></section>
                                    </div>
                                </fieldset>
                                <footer><a class="btn btn-primary atncol_c"
                                           id="btnSearch">查询</a>
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
                            <span>结算 </span>
                        </div>
                    </header>
                    <div role="content" style="border: 1px">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper"
                                 class="dataTables_wrapper form-inline no-footer"
                                 style="position: relative;">
                                <table id="settlementRecord_list"
                                       class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th data-class="expand"></th>
                                        <th data-class="expand">商家</th>
                                        <th data-class="expand">所在地
                                        </td>
                                        <th data-class="expand">交易总额(元)</th>
                                        <th data-class="expand">已结算总额(元)
                                        </td>
                                        <th data-class="expand">最后结算日期
                                        </td>
                                        <th data-class="expand">今日已结算（元）</th>
                                        <th data-class="expand">结算剩余（元）</th>
                                        <th data-class="expand">操作</th>
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

<!-- 结算 begin -->
<div class="modal fade" id="infoModal" tabindex="-1" role="dialog"
     aria-labelledby="infoModal">
    <div class="modal-dialog" role="document" style="width:1200px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">商家详情</h4>
            </div>
            <div class="infoContainer">
                <div class="infoHeadLeft">
                    <img src="" id="infoImage" class="faceImg" alt="">
                    <p class="infoCase" id="infoName">长宏烟酒行</p>
                    <p class="infoCase" id="infoAddr">四川省成都市双流区华阳镇太平街1号</p>
                    <p class="infoCase" id="infoManagerArea">双流区运营商</p>
                    <p class="infoCase" id="infoPhone">13281128970/18996339633</p>
                </div>
                <div class="line"></div>
                <div class="infoHeadRight">
                    <div class="allInfoCase">
                        <p class="infoCase">
                            <span class="infoTitle">店铺流水总额：</span><span id="infoMoneys"></span>
                        </p>
                        <p class="infoCase">
                            <span class="infoTitle">总成交单数：</span><span id="infoCounts"></span>
                        </p>
                        <p class="infoCase">
                            <span class="infoTitle">已结算总额：</span><span id="infoAmounts"></span>
                        </p>
                        <p class="infoCase">
                            <span class="infoTitle">待结算总额：</span><span id="infoForTen"></span>
                        </p>
                        <p class="infoCase">
                            <span class="infoTitle">结算账号：</span><span id="billingInfoType"></span>
                        </p>
                    </div>
                    <div class="go_info">
                        <a href="javascript:void(0)" data-backdrop='static' onclick="getInfoDetail()"
                           data-toggle='modal' data-target='#infoDetailModal'>查看交易流水</a>
                    </div>
                </div>
            </div>
            <div class="infoContainer">
                <div class="search">
                    <section class="col col-3 smart-form">
                        当前待结算日期

                        <span id="infoStartTime" class="infoStartTime">2017-01-01</span>

                        至<label class="input infoEndTime" style="display: inline-block;width: 200px;"><i
                            class="icon-append fa fa-calendar"></i> <input type="text"
                                                                           class="form-control"
                                                                           data-date-format="yyyy-mm-dd"
                                                                           id="infoEndTime" placeholder="结束时间">
                    </label>当前待结算金额<span id="stillMoney" class="stillMoney">29,812.07</span>元
                        <a href="javascript:void(0)" id="addBtn" data-backdrop='static' data-toggle='modal'
                           onclick="addInfo(this)" data-target='#addInfoModal'>添加结算记录</a></section>
                </div>
                <div class="infoTableTile">
                    <p>待结算流水</p>
                </div>
                <div class="infoTable" id="infoTable">
                    <table id="infoesTable" style="width: 100%" class="table table-bordered"
                           style="table-layout: fixed">
                        <thead>
                        <tr>
                            <th></th>
                            <th width="40px"></th>
                            <th width="200px" data-class="expand">时间</th>
                            <th width="300px" data-class="expand">订单编号</th>
                            <th width="150px" data-class="expand">用户</th>
                            <th width="auto" data-class="expand">优惠券</th>
                            <th width="120px" data-class="expand">交易总额(元)</th>
                            <th width="120px" data-class="expand">实付金额(元)</th>
                            <th width="120px" data-class="expand">结算金额(元)</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 结算 end -->


<!-- 添加结算记录 begin -->
<div class="modal fade" id="addInfoModal" tabindex="-1" role="dialog"
     aria-labelledby="list_settlement_records_myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content smart-form" style="width: 700px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">添加结算记录</h4>
            </div>
            <div class="addInfoContainer">
                <p class="infoCase"><span class="infoTitle">结算日期：</span><span id="addInfoDate"></span></p>
                <p class="infoCase"><span class="infoTitle">结算金额：</span><span id="addInfoMoney"></span></p>
                <footer class="addBtns">
                    <button type="button" data-id="" class="btn btn-primary" id="addSubmit">
                        <i class="fa fa-save"></i>
                        确定
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        取消
                    </button>
                </footer>
            </div>
        </div>
    </div>
</div>
<!-- 添加结算记录 end -->
<!-- 总账单流水 begin -->
<div class="modal fade" id="infoDetailModal" tabindex="-1" role="dialog"
     aria-labelledby="infoDetailModal">
    <div class="modal-dialog" role="document" style="width: 1100px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">总账单流水</h4>
            </div>
            <div class="infoContainer">
                <div class="search">
                    <section class="col col-3 smart-form">
                        已结算时间<label class="input infoEndTime" style="display: inline-block;width: 200px;"><i
                            class="icon-append fa fa-calendar"></i> <input type="text"
                                                                           class="form-control"
                                                                           data-date-format="yyyy-mm-dd"
                                                                           id="infoDetailStartTime" placeholder="结束时间">
                    </label>
                        至<label class="input infoEndTime" style="display: inline-block;width: 200px;"><i
                            class="icon-append fa fa-calendar"></i><input type="text"
                                                                          class="form-control"
                                                                          data-date-format="yyyy-mm-dd"
                                                                          id="infoDetailEndTime" placeholder="结束时间">
                    </label>已结算金额<span id="stillDetailMoney" class="stillMoney">29,812.07</span>元
                        <a href="javascript:void(0)" id="detailBtn">查询</a></section>
                </div>
                <div class="infoTableTile">
                    <p>交易流水</p>
                </div>
                <div class="infoTable" id="infoDetailTable">
                    <table id="infoesDetailTable" style="width: 100%" class="table table-bordered">
                        <thead>
                        <tr>
                            <th></th>
                            <th></th>
                            <th data-class="expand">时间</th>
                            <th data-class="expand">订单编号</th>
                            <th data-class="expand">用户</th>
                            <th data-class="expand">优惠券</th>
                            <th data-class="expand">交易总额(元)</th>
                            <th data-class="expand">实付金额(元)</th>
                            <th data-class="expand">结算金额(元)</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 总账单流水 end -->
<div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px; margin-left:-100px;"></div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-url="${pageContext.request.contextPath}">
<script src="settlementrecords/js/settlement_records.js"></script>
</body>
</html>
