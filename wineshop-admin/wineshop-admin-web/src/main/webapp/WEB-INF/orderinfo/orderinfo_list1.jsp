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
</head>
<title>交易数据1</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/orderinfo/js/orderinfo_list.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/orderinfo/css/orderinfo_list.css"/>
<script src="${pageContext.request.contextPath}/Scripts/libs/jquery-ui-1.10.3.js"></script>
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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
                            <form id="search-form" class="smart-form" novalidate="novalidate">
                                <fieldset>
                                    <div class="row">
                                        <section class="col col-3">
                                            <label class="label">标题</label>
                                            <label class="input">
                                                <input type="text" id="title" name="title">
                                            </label>
                                        </section>
                                        <section class="col col-3">
                                            <label class="label">审核状态</label>
                                            <label class="input">
                                                <label class="select">
                                                    <select name="status" id="status">
                                                        <option value="0">全部</option>
                                                        <option value="1">等待审核</option>
                                                        <option value="2">审核通过</option>
                                                        <option value="3">审核未通过</option>
                                                    </select> <i></i>
                                                </label>
                                            </label>
                                        </section>
                                        <section class="col col-3">
                                            <label class="label">添加时间</label>
                                            <label class="input">
                                                <i class="icon-append fa fa-calendar"></i>
                                                <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                       id="startTime" placeholder="开始时间">
                                            </label>
                                        </section>
                                        <section class="col col-3">
                                            <label class="label">&nbsp;</label>
                                            <label class="input">
                                                <i class="icon-append fa fa-calendar"></i>
                                                <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                       id="endTime" placeholder="结束时间">
                                            </label>
                                        </section>
                                    </div>
                                </fieldset>
                                <footer>
                                    <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                    <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a text-left"
                                       style="float: left" href="/ZhaoPin/TrcruitAddOrEdit?id=0" data-toggle="modal"
                                       data-backdrop='static' data-target="#remoteModal">统计</a>
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
                            <span>产品列表 </span>
                        </div>
                    </header>
                    <div class="row">
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 center-block" style="text-align: center;"><span
                                style="font-size: 18px;">交易次数:</span>&nbsp;<span
                                style="color: red;font-size: 18px;">${counts}</span></div>
                        <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" style="text-align: center;">
                            <div><span style="font-size: 18px;">交易总额（元）:</span>&nbsp;<span
                                    style="color: red;font-size: 18px;">${discountAmounts == null ? 0 :discountAmounts }</span>
                            </div>
                            <div>（除店铺优惠券抵扣金额外的全部订单总额）</div>
                        </div>
                    </div>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic" class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th data-class="expand">订单号</th>
                                        <th>所属系列</th>
                                        <th data-hide="phone">产品类型</th>
                                        <th data-hide="phone,tablet">浏览量</th>
                                        <th data-hide="phone,tablet">收藏数</th>
                                        <th data-hide="phone,tablet">添加时间</th>
                                        <th data-hide="phone,tablet"> 修改时间</th>
                                        <th data-hide="phone,tablet">有效标识</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
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
        </div>
    </div>
</div>
</body>
</html>