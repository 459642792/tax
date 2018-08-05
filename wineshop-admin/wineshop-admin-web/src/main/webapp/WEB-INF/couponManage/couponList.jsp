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
<body>
<div id="widget-grid">
    <div id="listContainer">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class='jarviswidget jarviswidget-color-white jarviswidget-sortable'
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
                        <div class="">
                            <div class="widget-body no-padding">
                                <form id="search-form" class="smart-form" novalidate="novalidate">
                                    <fieldset>
                                        <div class="row" style="margin-top: -22px">
                                            <section class="col col-3">
                                                <label class="label">发布人</label>
                                                <label class="input">
                                                    <input type="text" id="names" name="names"
                                                           placeholder="请输入发布人,支持模糊查找">
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="label">可用地区</label>
                                                <label class="input">
                                                    <input type="text" id="addr" name="addr"
                                                           placeholder="请输入可用地区,支持模糊查找">
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="label">开始时间</label>
                                                <label class="input">
                                                    <i class="icon-append fa fa-calendar"></i>
                                                    <input type="text" class="form-control"
                                                           data-date-format="yyyy-mm-dd" id="beginTime" name="beginTime"
                                                           placeholder="优惠券开始时间">
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="label">结束时间</label>
                                                <label class="input">
                                                    <i class="icon-append fa fa-calendar"></i>
                                                    <input type="text" class="form-control"
                                                           data-date-format="yyyy-mm-dd" id="endTime" name="endTime"
                                                           placeholder="优惠券结束时间">
                                                </label>
                                            </section>
                                        </div>
                                    </fieldset>
                                    <footer>
                                        <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                        <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                           href="${pageContext.request.contextPath}/couponMain/AddOrEdit?Id=0"
                                           data-toggle="modal" data-backdrop='static' data-target="#remoteModal">新增</a>
                                    </footer>
                                </form>
                            </div>
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
                            <span>优惠券列表 </span>
                        </div>
                    </header>
                    <div role="content" style="border:1px">
                        <div class="">
                            <div id="dt_basic_wrapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic" class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th data-class="expand">优惠券名称</th>
                                        <th data-hide="phone,tablet">发布人</th>
                                        <th data-hide="phone,tablet">可用地区</th>
                                        <th data-hide="phone,tablet">面额(元)</th>
                                        <th data-hide="phone,tablet">发行张数</th>
                                        <th data-hide="phone,tablet">使用张数</th>
                                        <th data-hide="phone,tablet">有效时间</th>
                                        <th data-hide="phone,tablet">状态</th>
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
    <script type="text/javascript">
        //页面初始化调用
        pageSetUp();

        var pageTable = {
            dom: $('#dt_basic'),//table节点
            ajaxUrl: "${pageContext.request.contextPath}/couponMain/pageMain",//ajax请求地址
            httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
            aoColumns: ["title", "updateBy", "addr", "money", "count", "usedCont", "beginTime", "statusStr"],//table要显示的列
            primaryKey: "id",
            diyColumn: [
                {
                    aTargets: [3],//要显示的位置
                    mData: "id",//主键
                    mRender: function (data, type, full) {
                        return full.addr == undefined ? "" : full.addr;
                    }
                },
                {
                    aTargets: [7],//要显示的位置
                    mData: "id",//主键
                    mRender: function (data, type, full) {
                        return full.beginTime.substring(0, 10) + "-" + full.endTime.substring(0, 10);
                    }
                },
                {
                    aTargets: [9],//要显示的位置
                    mData: "id",//主键
                    mRender: function (data, type, full) {
                        var links = [];
                        if (full.statusStr != "已过期" && full.statusStr != "已停止") {
                            links.push("<a class='tableBtn hidden-mobile' href='${pageContext.request.contextPath}/couponMain/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/修改</a>");
                            links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0)' onclick='defineField(" + full.id + ")' data-toggle='modal' data-logout-msg='sss' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/jieshu.png' />结束</a>");
                        }
                        return links.join(" ");
                    }
                }
            ],
            ajxaParam: function () {
                return {
                    Name: $.trim($('#names').val()),
                    Addr: $.trim($('#addr').val()),
                    BeginTime: $.trim($('#beginTime').val()),
                    EndTime: $.trim($('#endTime').val())
                };
            },
        };

        //前端执行结束操作
        function defineField(id) {
            window.showConfirm("提示", "您确认要提前结束发放吗！", function (flag) {
                $.get("${pageContext.request.contextPath}/couponMain/endCoupon", {couponId: id}, function (data) {
                    if (data.success) {
                        window.location.reload();
                    } else {
                        alert(data.message);
                        return false;
                    }
                });
            }, function (result) {
                $(".modal-backdrop").remove();
                $("#remoteModal").css("display", "none");
            });
        }


        (function () {
            $("#beginTime").datepicker({
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                changeYear: true,
                dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
                dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
                monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                nextText: '<i class="fa fa-chevron-right"></i>',
                prevText: '<i class="fa fa-chevron-left"></i>',
                onClose: function (dateText, inst) {

                },
            });

            $("#endTime").datepicker({
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                changeYear: true,
                dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
                dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
                monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                nextText: '<i class="fa fa-chevron-right"></i>',
                prevText: '<i class="fa fa-chevron-left"></i>',
                onClose: function (dateText, inst) {

                },
            });
            $("#btnSearch").click(function () {
                if (pageTable) {
                    pageTable.container.fnDraw();
                }
            });

            window.initPageTab(pageTable);
        })()
    </script>
</body>
</html>
