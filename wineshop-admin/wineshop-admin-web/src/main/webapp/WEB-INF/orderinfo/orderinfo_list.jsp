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
                                        <section class="col col-3"><label class="label">交易地区</label>
                                            <label class="input"> <input type="text"
                                                                         id="content_tradingArea" maxlength="15"
                                                                         placeholder="请输入交易地区,支持模糊查找">
                                            </label></section>
                                        <section class="col col-3"><label class="label">交易时间</label>
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
                                            <label class="input"> <input type="text"
                                                                         id="content_name" maxlength="15"
                                                                         placeholder="请输入店铺名称,支持模糊查找">
                                            </label></section>
                                        <section class="col col-3"><label class="label">交易状态</label>
                                            <label class="select">
                                                <select id="content_tradingState" >
                                                    <option value="0">全部</option>
                                                    <option value="100">交易成功</option>
                                                    <option value="10">退款成功</option>
                                                    <option value="9">退款失败</option>
                                                </select>
                                            </label></section>
                                        <section class="col col-3"><label class="label">交易来源</label>
                                            <label class="select">
                                                <select id="content_tradingSource" >
                                                    <option value="0">全部</option>
                                                    <option value="2">店内付款</option>
                                                    <option value="1">订单支付</option>
                                                </select>
                                            </label></section>
                                    </div>
                                </fieldset>
                                <footer><a class="btn btn-primary atncol_c"
                                           id="btnSearch">查询</a> <a
                                        class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                        href="#" data-toggle="modal" style="display: none"
                                        onclick="countPage('${pageContext.request.contextPath}/orderInfo/countPrice?ids=')"
                                        data-backdrop='static'>统计</a></footer>
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
                            <span>交易数据 </span>
                        </div>
                    </header>
                    <div class="row orderInfoList" style="display: flex">
                        <div class="orderInfoCon" >
                            <span>交易次数:</span>&nbsp;<span
                                style="color: red; font-size: 18px;" id="adminTradeListVOList_counts"></span>
                        </div>
                        <div class="orderInfoCon" >
                            <div>
                                <span>平台优惠券金额(元):</span>&nbsp;<span
                                    style="color: red; font-size: 18px;" id="adminTradeListVOList_platformFee"></span>
                            </div>

                            <div>（退款订单的优惠券不计入）</div>
                        </div>
                        <div class="orderInfoCon">
                            <span >商家优惠券(元):</span>&nbsp;<span
                                style="color: red; font-size: 18px;" id="adminTradeListVOList_vendorFee"></span>
                            <div>（退款订单的优惠券不计入）</div>
                        </div>
                        <div class="orderInfoCon">
                            <span >退款金额(元):</span>&nbsp;<span
                                style="color: red; font-size: 18px;" id="adminTradeListVOList_refundFee"></span>
                        </div>
                        <div class="orderInfoCon">
                            <div>
                                <span >交易总额（元）:</span>&nbsp;<span
                                    style="color: red; font-size: 18px;"
                                    id="adminTradeListVOList_totalFee"></span>
                            </div>
                            <div>（除店铺优惠券抵扣金额外的全部订单总额）</div>
                        </div>
                        <div class="orderInfoCon">
                            <span >流水金额(元):</span>&nbsp;<span
                                style="color: red; font-size: 18px;" id="adminTradeListVOList_statementFee"></span>
                            <div>（全部资金流水）</div>
                        </div>
                    </div>
                    <div role="content" style="border: 1px">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper"
                                 class="dataTables_wrapper form-inline no-footer"
                                 style="position: relative;">
                                <table id="orderInfo_list"
                                       class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <td>ID</td>
                                        <td data-class="expand" style="border-left:0px;width: 10px;"><input
                                                type='checkbox'
                                                class='allCheckchilds' onclick="checkboxs(this)"/></td>
                                        <td data-class="phone">订单号</td>
                                        <td data-hide="phone">交易时间</td>
                                        <td data-hide="phone,tablet">付款人</td>
                                        <td data-hide="phone,tablet">交易来源</td>
                                        <td data-hide="phone,tablet">收款店铺</td>
                                        <td data-hide="phone,tablet">交易地区</td>
                                        <td data-hide="phone,tablet">订单金额（元）</td>
                                        <td data-hide="phone,tablet">优惠券抵扣金额（元）</td>
                                        <td data-hide="phone,tablet">实付金额（元）</td>
                                        <td data-hide="phone,tablet">交易状态</td>
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">统计</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="txt_departmentname">交易总量</label> <span
                        id="count_counts" class="form-control">${counts}</span>
                </div>
                <div class="form-group">
                    <label for="txt_parentdepartment">交易总额(元)</label> <span
                        id="count_discountAmounts" class="form-control">${discountAmounts}</span>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭
                </button>
            </div>
        </div>
    </div>
</div>
<script src="orderinfo/js/orderinfo_list.js"></script>

<script type="text/javascript">
    pageSetUp();
    var pageTable = {
        dom: $('#orderInfo_list'),//table节点
        //ajaxUrl: "${pageContext.request.contextPath}/orderInfo/listLimitOrderInfo",//ajax请求地址
        ajaxUrl: "${pageContext.request.contextPath}/tradeAdmin/getTradeInfo",//ajax请求地址
        httpMethod: 'post',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["orderNo", "orderNo", "tradeTime", "userId", 'orderSource',"shopName", "tradeArea", "originalPrice", "couponFee", "payPrice",'orderBusinessStateName'],//table要显示的列
        primaryKey: "orderNo",//主键
        diyColumn: [     //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "ID",//主键
                mRender: function (data, type, full) {
                    $(".checkchilds").removeAttr("checked");
                    return "<input type='checkbox'  class='checkchild' style='text-align: center;' onclick='checkChild(this)' id='" + full.id + "' />";
                },
                "bSortable": false
            },
            {
                aTargets: [5],//要显示的位置
                mData: "ID",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.orderSource == 1 ? "普通订单" : '面对面付款订单';

                }
            }
        ],
        ajxaParam: function () {
            return {
                tradeArea: $("#content_tradingArea").val(),
                shopName: $("#content_name").val(),
                tradeTimeFrom: $("#content_beginTime").val(),
                tradeTimeTo: $("#content_endTime").val(),
                orderBusinessState: $("#content_tradingState").val()==0?null:$("#content_tradingState").val(),
                tradeSource: $("#content_tradingSource").val()==0?null:$("#content_tradingSource").val(),
            };
        },
    }
    $(function () {
        $("#btnSearch").click(function () {
            if (pageTable) {
                pageTable.container.fnDraw();
            }
            /*//计算交易次数
            var params = {
                "tradingArea": $("#content_tradingArea").val(),
                "vendorName": $("#content_name").val(),
                "beginTime": $("#content_beginTime").val(),
                "endTime": $("#content_endTime").val(),
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/orderInfo/countPrices",
                method: "get",
                dataType: "json",
                data: params,
                success: function (data) {
                    $("#select_counts").html("");
                    $("#select_discountAmounts").html("");
                    $("#select_counts").html(data.data.counts == null || data.data.counts == undefined ? 0 : data.data.counts);
                    $("#select_discountAmounts").html(data.data.DiscountAmounts == null || data.data.DiscountAmounts == undefined ? 0 : data.data.DiscountAmounts);
                }
            });*/
        });
        var month = (new Date().getMonth() + 1);
        switch (month) {
            case 12:
            case 11:
            case 10:
                month = month;
                break;
            default:
                month = '0' + month;
                break;
        }
        var minute = (new Date().getDate());
        if (minute < 10) {
            minute = '0' + minute;
        }
        var dates = (new Date().getFullYear()) + "-" + month + "-" + minute;

        $("#content_beginTime").val($("#content_beginTime").val() == "" || $("#content_beginTime").val() == null ? dates : $("#content_beginTime").val()) ,
            $("#content_endTime").val($("#content_endTime").val() == "" || $("#content_endTime").val() == null ? dates : $("#content_endTime").val()) ,
            window.initPageTab(pageTable);
    });	//页面初始化调用

</script>
</body>
</html>
