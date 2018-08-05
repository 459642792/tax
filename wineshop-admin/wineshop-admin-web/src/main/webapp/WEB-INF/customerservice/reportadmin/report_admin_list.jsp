<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="orderinfo/css/orderinfo_list.css"/>
</head>
<body>
<input id="baseUrl" type="hidden" dataurl="${pageContext.request.contextPath}">
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


                                        <section class="col col-3"><label class="label">举报时间</label>
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

                                        <section class="col col-3"><label class="label">关键字搜索</label>
                                            <label class="input"> <input type="text"
                                                                         id="content_name" maxlength="15"
                                                                         placeholder="请输入店铺名称、用户账号,支持模糊查询">
                                            </label></section>
                                        <section class="col col-3"><label class="label">举报状态</label>
                                            <label class="select">
                                                <select id="content_tradingState">
                                                    <option value="10">全部</option>
                                                    <option value="0">未处理</option>
                                                    <option value="1">处理中</option>
                                                    <option value="2">已处理</option>
                                                </select>
                                            </label></section>

                                        <section class="col col-3"><label class="label">举报内容</label>
                                            <label class="input"> <input type="text"
                                                                         id="content_tradingArea" maxlength="15"
                                                                         placeholder="请输入举报内容,支持模糊查询">
                                            </label></section>
                                    </div>
                                </fieldset>
                                <footer><a class="btn btn-primary atncol_c"
                                           id="btnSearch">查询</a></footer>
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
                            <span>订单数据 </span>
                        </div>
                    </header>
                    <div role="content" style="border: 1px">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper"
                                 class="dataTables_wrapper form-inline no-footer"
                                 style="position: relative;">
                                <table id="orderInfo_list"
                                       class="table table-striped table-bordered table-hover"
                                       width="100%" style="font-size: 13px">
                                    <thead>
                                    <tr>
                                        <td>ID</td>
                                        <td data-class="phone">举报时间</td>
                                        <td data-hide="phone">订单编号</td>
                                        <td data-hide="phone,tablet">店铺名称</td>
                                        <td data-hide="phone,tablet">用户账号</td>
                                        <td data-hide="phone,tablet">举报内容</td>
                                        <td data-hide="phone,tablet">处理状态</td>
                                        <td data-hide="phone,tablet">操作</td>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="remoteModal" role="dialog" tabindex="-1"
                 aria-labelledby="remoteModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content"
                         style="width: 800px; margin-left: -100px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 统计 -->
<div class="modal fade orderAdmin_orderDetail" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="height: 70%;overflow-y: auto">
            <div class="modal-header" style="border: none;padding-bottom: 0">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">× </span>
                </button>
                <h4 class="modal-title orderAdmin_detail_titile">举报详情</h4>
            </div>
            <div class="modal-body" style="padding-top:0 ">
                <%--订单信息--%>
                <div class="orderAdmin_detail_orderinfo">
                    <div class="orderAdmin_detail_headerTitle">订单信息</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>订单编号:</span>
                            <span id="orderAdmin_orderNumber"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>订单金额:</span>
                            <span id="orderAdmin_orderStatus"></span>
                        </div>
                    </div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>店铺名称:</span>
                            <span id="orderAdmin_orderAddrr"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>用户账号:</span>
                            <span id="orderAdmin_orderSource"></span>
                        </div>
                    </div>

                </div>
                <%--举报内容--%>
                <div class="orderAdmin_detail_orderinfo" id="jb_text">
                    <div class="orderAdmin_detail_headerTitle">举报内容</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span class="reportAdmin_listText"></span>
                        </div>
                    </div>
                </div>
                <%--举报图片--%>
                <div class="orderAdmin_detail_orderinfo" id="jb_imgs">
                    <div class="orderAdmin_detail_headerTitle">举报图片</div>
                    <div class="reportAdmin_listIAmge">
                        <a target="_blank" href="./Content/Home/image/daiwei.jpg">
                            <img src="./Content/Home/image/daiwei.jpg">
                        </a>
                        <a target="_blank" href="./Content/Home/image/daiwei.jpg">
                            <img src="./Content/Home/image/daiwei.jpg">
                        </a>
                    </div>
                </div>
                <%--客服回复--%>
                <div class="orderAdmin_detail_orderinfo" id="kf_text">
                    <div class="orderAdmin_detail_headerTitle">客服回复</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span class="reportAdmin_servicereply"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <textarea id="kfhf_textarea" maxlength="50" placeholder="50字内回复"></textarea>
                            <span class="redColor"></span>
                        </div>
                    </div>
                </div>
                <%--处理结果--%>
                <div class="orderAdmin_detail_orderinfo" id="jg_text">
                    <div class="orderAdmin_detail_headerTitle">处理结果</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span class="reportAdmin_serviceresult"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <textarea id="cljg_textarea" maxlength="50" placeholder="请描述本次处理结果,将有助于后期核实"></textarea>
                            <span class="redColor"></span>
                        </div>
                    </div>
                </div>

                <button class="btn btn-primary atncol_c" id="reportAdmin_button">处理</button>
            </div>
        </div>
    </div>
</div>

<script src="customerservice/reportadmin/js/report_admin_list.js"></script>
<script type="text/javascript">
    pageSetUp();
    $(function () {
        window.initPageTab(pageTable);
    });	//页面初始化调用

</script>
</body>
</html>
