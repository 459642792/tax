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
<input type="hidden" id="dataUrl" value="${pageContext.request.contextPath}">
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
                                        <section class="col col-3"><label class="label">交易状态</label>
                                            <label class="select">
                                                <select id="content_tradingState">
                                                    <option value="0">全部</option>
                                                    <option value="1">待付款</option>
                                                    <option value="2">待接单</option>
                                                    <option value="3">待收货</option>
                                                    <option value="4">待评价</option>
                                                    <option value="5">已完成</option>
                                                    <option value="6">已取消</option>
                                                    <option value="7">退款申请中</option>
                                                    <option value="8">退款中</option>
                                                    <option value="9">退款失败</option>
                                                    <option value="10">退款成功</option>
                                                    <option value="11">待确认</option>
                                                </select>
                                            </label></section>

                                        <section class="col col-3"><label class="label">订单创建时间</label>
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
                                                                         placeholder="通过输入订单的酒行名称或者用户账号进行模糊查询">
                                            </label></section>

                                        <section class="col col-3"><label class="label">交易地区</label>
                                            <label class="input"> <input type="text"
                                                                         id="content_tradingArea" maxlength="15"
                                                                         placeholder="请输入交易地区,支持模糊查找">
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
                                        <td data-class="phone">订单号</td>
                                        <td data-hide="phone">订单创建时间</td>
                                        <td data-hide="phone,tablet">店铺名称</td>
                                        <td data-hide="phone,tablet">用户账号</td>
                                        <td data-hide="phone,tablet">订单金额</td>
                                        <td data-hide="phone,tablet">交易地区</td>
                                        <td data-hide="phone,tablet">订单状态</td>
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

<!-- 订单详情 -->
<div class="modal fade orderAdmin_orderDetail" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="height: 70%;overflow-y: auto">
            <div class="modal-header" style="border: none;padding-bottom: 0">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">× </span>
                </button>
                <h4 class="modal-title orderAdmin_detail_titile">订单详情</h4>
            </div>
            <div class="modal-body" style="padding-top:0 ">
                <%--订单编号信息--%>
                <div class="orderAdmin_detail_orderinfo">
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>订单号:</span>
                            <span id="orderAdmin_orderNumber"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>订单状态:</span>
                            <span id="orderAdmin_orderStatus"></span>
                        </div>
                    </div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>交易地区:</span>
                            <span id="orderAdmin_orderAddrr"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>订单来源:</span>
                            <span id="orderAdmin_orderSource"></span>
                        </div>
                    </div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>订单金额:</span>
                            <span id="orderAdmin_orderMoney"></span>
                        </div>
                    </div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>退款方式:</span>
                            <span id="orderAdmin_drawbackChannel"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>退款金额:</span>
                            <span id="orderAdmin_drawbackFee"></span>
                        </div>
                    </div>
                </div>
                <%--配送信息--%>
                <div class="orderAdmin_detail_orderinfo">
                    <div class="orderAdmin_detail_headerTitle">配送信息</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>配送方式:</span>
                            <span id="orderAdmin_deliveryType"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>送货/收货时间:</span>
                            <span id="orderAdmin_deliveryTime"></span>
                        </div>
                    </div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>送货/收货地址:</span>
                            <span id="orderAdmin_deliveryAddress"></span>
                        </div>
                    </div>
                </div>
                <%--用户信息--%>
                <div class="orderAdmin_detail_orderinfo">
                    <div class="orderAdmin_detail_headerTitle">用户信息</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>用户账号:</span>
                            <span id="orderAdmin_userId"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>联系方式:</span>
                            <span id="orderAdmin_userPhone"></span>
                        </div>
                    </div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>微信昵称:</span>
                            <span id="orderAdmin_userNickName"></span>
                        </div>
                    </div>
                </div>
                <%--酒行信息--%>
                <div class="orderAdmin_detail_orderinfo">
                    <div class="orderAdmin_detail_headerTitle">酒行信息</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>酒行账号:</span>
                            <span id="orderAdmin_shopId"></span>
                        </div>
                        <div class="orderAdmin_detail_orderinfo_listR">
                            <span>联系方式:</span>
                            <span id="orderAdmin_shopPhone"></span>
                        </div>
                    </div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>酒行名称:</span>
                            <span id="orderAdmin_shopName"></span>
                        </div>
                    </div>
                </div>
                <%--商品信息--%>
                <div class="orderAdmin_detail_orderinfo">
                    <div class="orderAdmin_detail_headerTitle">商品信息</div>
                    <div class="orderAdmin_goodsList">

                    </div>
                    <div>
                        <div class=" orderAdmin_detail_orderinfo_sum">
                            <span>商品合计费用:</span>
                            <span id="orderAdmin_totalGoodsFee"></span>
                        </div>
                        <div class=" orderAdmin_detail_orderinfo_sum">
                            <span>优惠券:</span>
                            <span id="orderAdmin_discounts">满500减50</span>
                        </div>
                        <div class=" orderAdmin_detail_orderinfo_sum">
                            <span>应付金额:</span>
                            <span id="orderAdmin_payPriceY">¥125,020.00</span>
                        </div>
                    </div>
                </div>
                <%--商品时间--%>
                <div class="orderAdmin_detail_orderinfo">
                    <div class="orderAdmin_detail_headerTitle">订单信息</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span>创建时间:</span>
                            <span id="orderAdmin_createTime"></span>
                        </div>
                    </div>
                    <div class="order_timeList">

                    </div>
                </div>
                <%--留言批注--%>
                <div class="orderAdmin_detail_orderinfo" id="remarkInfo">
                    <div class="orderAdmin_detail_headerTitle">留言批注</div>

                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span id="orderAdmin_remark"></span>
                        </div>
                    </div>

                </div>
                <%--退款理由--%>
                <div class="orderAdmin_detail_orderinfo" id="drawbackDescInfo">
                    <div class="orderAdmin_detail_headerTitle">退款理由</div>
                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span id="orderAdmin_drawbackDesc"></span>
                        </div>
                    </div>

                    <div class="orderAdmin_detail_orderinfo_list">
                        <div class="orderAdmin_detail_orderinfo_listL">
                            <span id="orderAdmin_drawbackRemark"></span>
                        </div>
                    </div>
                </div>
                <div style="text-align: center;margin-top: 20px"  id="refund_button">
                    <a type="button" class="btn btn-primary atncol_c promotion_btn1" onclick="showRefund()">手动退款</a>
                </div>

            </div>
        </div>
    </div>
</div>
<%--手动退款--%>
<div class="modal fade orderAdmin_orderDetail" id="refundAuto" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="height: 250px;width: 340px;margin-top: 50px">
            <div class="modal-header" style="border: none;padding-bottom: 0">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">× </span>
                </button>
                <h4 class="modal-title orderAdmin_detail_titile">手动退款</h4>
            </div>
            <div class="modal-body" style="padding-top:0 ">
                <div class="refundAutoCont"style="">
                    <input type="hidden" id="refundAuto_orderId">
                    <div class="refundAutoCont-list">
                        <span>退款方式</span>
                        <select id="tuikuanChannel">
                            <option value="1">微信</option>
                            <option value="2">支付宝</option>
                            <option value="3">银行卡转账</option>
                        </select>
                    </div>
                    <div class="refundAutoCont-list">
                        <span>退款账号</span>
                        <input type="text" id="tuikuanId" maxlength="30" placeholder="请输入退款账号">
                        <br>
                        <span class="redColor errorRefundAuto" >请输入退款账号</span>
                    </div>
                    <div class="refundAutoCont-list">
                        <span>退款金额</span>
                        <input type="text" id="tuikuanFee" maxlength="10" placeholder="两位小数的数字">
                        <br>
                        <span class="redColor errorRefundAuto" >请输入正取的金额</span>
                    </div>
                    <div style="text-align: center;margin-top: 20px">
                        <a type="button" class="btn btn-primary atncol_c promotion_btn1" onclick="autoRefund()">提交</a>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<script src="tradeinfo/orderadmin/js/order_admin_list.js"></script>
<script type="text/javascript">
    pageSetUp();
    var pageTable = {
        dom: $('#orderInfo_list'),//table节点
        ajaxUrl: "${pageContext.request.contextPath}/orderAdmin/listOrder",//ajax请求地址
        httpMethod: 'post',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["orderNo", "createTime", "shopName", "userId", 'payPrice', "tradeArea", "orderBusinessStateName", "",],//table要显示的列
        primaryKey: "orderNo",//主键
        diyColumn: [     //自定义列
            {
                aTargets: [8],//要显示的位置
                mData: "ID",//主键
                mRender: function (data, type, full) {//返回参数
                    var str = "<a style='text-align: center; cursor: pointer' onclick=checkDetail('" + full.orderId + "') >查看</a>"
                    return str;
                }
            }
        ],
        ajxaParam: function () {
            return {
                orderBusinessState: $('#content_tradingState').val(),
                orderNo: '',
                createFrom: $('#content_beginTime').val(),
                createTo: $('#content_endTime').val(),
                keyword: $('#content_name').val(),
                tradeArea: $('#content_tradingArea').val()
            };
        },
    }
    $(function () {
        $("#btnSearch").click(function () {
            if (pageTable) {
                pageTable.container.fnDraw();
            }
        });
        var month = (new Date().getMonth() + 1);
        month = month < 10 ? '0' + month : month;
        var minute = (new Date().getDate());
        minute = minute < 10 ? '0' + minute : minute;
        var dates = (new Date().getFullYear()) + "-" + month + "-" + minute;

        $("#content_beginTime").val($("#content_beginTime").val() == "" || $("#content_beginTime").val() == null ? dates : $("#content_beginTime").val()) ,
            $("#content_endTime").val($("#content_endTime").val() == "" || $("#content_endTime").val() == null ? dates : $("#content_endTime").val()) ,
            window.initPageTab(pageTable);
    });	//页面初始化调用

    function checkDetail(msg) {
        $.ajax({
            url: "${pageContext.request.contextPath}/orderAdmin/getOrderDetail",
            type: 'POST',
            data: {orderId: msg},
            success: function (res) {
                if (res.success) {
                    showDetial(res.data)
                } else {
                    window.simpleNotify(res.message);
                }
            }
        });
    }
</script>
</body>
</html>
