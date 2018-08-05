
pageSetUp();
var pageTable = {
    dom: $('#settlementRecord_list'),//table节点
    ajaxUrl: $("#baseUrl").attr("data-url") + "/settlement/pageQuerySettlementSum",//ajax请求地址
    httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["vendorId", "vendorName", "region", "allAmounts", "allBalancedAmounts", "createTime", "balancedAmounts", "allUnbalancedAmounts"],//table要显示的列
    primaryKey: "vendorId",//主键
    diyColumn: [     //自定义列
        {
            aTargets: [1],//要显示的位置
            mData: "vendorId",//主键
            mRender: function (data, type, full, obj) {
                return obj.row + 1
            },
            "bSortable": false
        },
        {
            aTargets: [3],//要显示的位置
            mData: "vendorId",//主键
            mRender: function (data, type, full) {//返回参数
                return full.region == undefined ? "" : full.region;
            }
        },
        {
            aTargets: [4],//要显示的位置
            mData: "vendorId",//主键
            mRender: function (data, type, full) {//返回参数
                return full.allAmounts == undefined ? 0 : full.allAmounts;
            }
        },
         {
             aTargets: [6],//要显示的位置
             mData: "vendorId",//主键
             mRender: function (data, type, full) {//返回参数
                 return getFromatDay(new Date(data));
             }
         },
        {
            aTargets: [7],//要显示的位置
            mData: "vendorId",//主键
            mRender: function (data, type, full) {//返回参数
            	var lastDay=getFromatDay(new Date(full.createTime));
            	var toDay=getFromatDay(new Date());
            	if(toDay==lastDay){
            		 return full.balancedAmounts == undefined ? '0.00' : full.balancedAmounts;
            	}
            	return '0.00';
            }
        },
        {
            aTargets: [8],//要显示的位置
            mData: "vendorId",//主键
            mRender: function (data, type, full) {//返回参数
                return full.allUnbalancedAmounts == undefined ? "0.00" : full.allUnbalancedAmounts;
            }
        },
        {
            aTargets: [9],//要显示的位置
            mData: "vendorId",//主键
            mRender: function (data, type, full) {//返回参数
                return "<a href='#' data-toggle='modal' onclick='getVendorInfo(" + full.vendorId + ")' data-backdrop='static' data-target='#infoModal' class='tableBtn hidden-mobile'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/command.png' />管理</a>"
            }
        }
    ],
    ajxaParam: function () {
        return {
        	region: $("#content_tradingArea").val(),
            vendorName: $("#content_name").val(),
            startTime: $("#content_beginTime").val(),
            endTime: $("#content_endTime").val()
        };
    },
}
$(function () {
    $("#btnSearch").click(function () {
        if (pageTable) {
            pageTable.container.fnDraw();
        }
    });
    $("#detailBtn").on("click", function () {
        var stratTime = new Date($("#infoDetailStartTime").val()).getTime();
        var endTime = new Date($("#infoDetailEndTime").val()).getTime();
        if (endTime < stratTime) {
            simpleNotify("查询结束日期不能早于开始日期");
            return false;
        }
        $("#infoesDetailTable").dataTable().fnDraw();
    })
    window.initPageTab(pageTable);
    $("#content_beginTime").datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        nextText: '<i class="fa fa-chevron-right"></i>',
        prevText: '<i class="fa fa-chevron-left"></i>',
        maxDate: '-0d',
        onClose: function (dateText, inst) {

        },
    });

    $("#content_endTime").datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        nextText: '<i class="fa fa-chevron-right"></i>',
        prevText: '<i class="fa fa-chevron-left"></i>',
        maxDate: '-0d',
        onClose: function (dateText, inst) {

        },
    });
});	//页面初始化调用
function getVendorInfo(id) {
    $('#infoesTable').dataTable().fnDestroy();
    $("#infoEndTime").datepicker('destroy');
    $.ajax({
        url: $("#baseUrl").attr("data-url") + "/settlement/getSettlementSumInfo",
        type: "get",
        data: {vendorId: id},
        success: function (data) {
            if (data.success) {
                $("#infoImage").attr("src", data.data.image);
                $("#infoName").text(data.data.vendorName);
                $("#infoPhone").text(data.data.phone);
                $("#infoAddr").text(data.data.address);
                $("#infoManagerArea").text(data.data.region);
                $("#infoMoneys").text(data.data.allAmounts);
                $("#infoCounts").text(data.data.allCount);
                $("#infoAmounts").text(data.data.allBalancedAmounts);
                $("#infoForTen").text(data.data.allUnbalancedAmounts);
                $("#alipayCardBank").text();
                var lastDealTime= new Date(data.data.lastTime);
                lastDealTime=lastDealTime.setDate(lastDealTime.getDate()+1);
                data.data.lastTime= new Date(lastDealTime);
//                switch (data.data.billingInfoType) {
//                    case 0:
//                        $("#billingInfoType").text("无")
//                        break;
//                    case 1:
//                        $("#billingInfoType").text(data.data.alipayCardBank == '' ? '无' : data.data.alipayCardBank + "(银行卡)")
//                        break;
//                    case 2:
//                        $("#billingInfoType").text(data.data.alipayCardBank == '' ? '无' : data.data.alipayCardBank + "(支付宝)")
//                        break;
//                    default:
//                        break;
//                }
                //var endTime = getNextDay(data.data.lastTime);
                var endTime =getFromatDay(data.data.lastTime)
                var allowStartTime = new Date(endTime.split("-")[0], parseInt(endTime.split("-")[1]) - 1,endTime.split("-")[2]);
                $("#infoStartTime").text(getFromatDay(data.data.lastTime));
                $("#infoEndTime").datepicker({
                    dateFormat: "yy-mm-dd",
                    changeMonth: true,
                    changeYear: true,
                    dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
                    dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
                    monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    nextText: '<i class="fa fa-chevron-right"></i>',
                    prevText: '<i class="fa fa-chevron-left"></i>',
                    minDate: allowStartTime,
                    maxDate: endTime == new Date().getFullYear() + '-' +
                    ((new Date().getMonth() + 1)<10?'0'+(new Date().getMonth() + 1):(new Date().getMonth() + 1)) + '-'
                    + (new Date().getDate()<10?'0'+new Date().getDate():new Date().getDate()) ? '-0d' : '-1d',
                    onClose: function (dateText, inst) {

                    },

                    onSelect: function (dateText, inst) {
                        $("#addBtn").attr("data-endTime", dateText);
                        $("#infoesTable").dataTable().fnDraw();
                        $.ajax({
                            url: $("#baseUrl").attr("data-url") + "/settlement/pageNotSettlementOrder",
                            type: "get",
                            data: {
                                vendorId: id,
                                startTime: endTime,
                                endTime: $("#infoEndTime").val(),
                                pageIndex: 1,
                                pageSize: 10
                            },
                            success: function (data) {
                                if (data.success) {
                                    $("#stillMoney").text(data.extendMessage?data.extendMessage:0);
                                    $("#addBtn").attr("data-money", data.extendMessage?data.extendMessage:0)
                                }
                            }
                        })
                    },
                }).val(isToday(endTime) ? endTime : getFromatDay(new Date())/*getPrevDay(new Date().getTime())*/);
                window.initPageTab({
                    dom: $('#infoesTable'),//table节点
                    ajaxUrl: $("#baseUrl").attr("data-url") + "/settlement/pageNotSettlementOrder",//ajax请求地址
                    httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
                    aoColumns: ["orderId", "completeTime", "orderNo", "userName",  "couponAmount","originalPrice", "couponAmount", "payPrice"],//table要显示的列
                    primaryKey: "orderId",//主键
                    autoWidth: false,
                    diyColumn: [     //自定义列
                        {
                            aTargets: [1],//要显示的位置
                            mData: "orderId",//主键
                            mRender: function (data, type, full, obj) {
                                return obj.row + 1
                            }
                        },
                        {
                            aTargets: [2],//要显示的位置
                            mData: "orderId",//主键
                            mRender: function (data, type, full, obj) {
                                return getFromatDay(new Date(data));
                            }
                        },
                        {
                            aTargets: [5],//要显示的位置
                            mData: "orderId",//主键
                            mRender: function (data, type, full, obj) {
                                return full.couponAmount==null ? "0" : "<p>" + full.couponAmount + "</p>"
                            }
                        },
                        {
                            aTargets: [7],//要显示的位置
                            mData: "orderId",//主键
                            mRender: function (data, type, full, obj) {
                                return full.payPrice == undefined ? "" : full.payPrice
                            }
                        }
                    ],
                    ajxaParam: function () {
                        return {
                            vendorId: id,
                            startTime: endTime,
                            endTime: $("#infoEndTime").val()
                        };
                    }
                })
                $.ajax({
                    url: $("#baseUrl").attr("data-url") + "/settlement/pageVendorSettlementHistory",
                    type: "get",
                    data: {
                        vendorId: id,
                        startTime: endTime,
                        endTime: $("#infoEndTime").val(),
                        pageIndex: 1,
                        pageSize: 10
                    },
                    success: function (data) {
                        if (data.success) {
                            $("#stillMoney").text(data.extendMessage?data.extendMessage:0);
                            $("#addBtn").attr("data-money", data.extendMessage?data.extendMessage:0);
                        }
                    }
                })
                $("#addBtn").attr("data-id", id);
                $("#addBtn").attr("data-startTime", endTime);
                $("#addBtn").attr("data-endTime", $("#infoEndTime").val());
            }
        }
    })
}

function getNextDay(d) {
    d = new Date(d);
    var new_d = new Date() - 1000 * 60 * 60 * 24;
    d = +d + 1000 * 60 * 60 * 24;
    d = new Date(d);
    //return d;
    //格式化
    //return d.getFullYear() + "-" + ((d.getMonth() + 1)) + "-" + d.getDate();
    return getFromatDay(d)
}

function getPrevDay(d) {
    d = +d - 1000 * 60 * 60 * 24;
    d = new Date(d);
    //return d;
    //格式化
   // return d.getFullYear() + "-" + ((d.getMonth() + 1)<10?'0'+(d.getMonth() + 1):(d.getMonth() + 1)) + "-" + (d.getDate()<10?'0'+d.getDate():d.getDate());
    return getFromatDay(d)

}
function getFromatDay(d) {
	//格式化

	return d.getFullYear() + "-" + ((d.getMonth() + 1)<10?'0'+(d.getMonth() + 1):(d.getMonth() + 1)) + "-" + (d.getDate()<10?'0'+d.getDate():d.getDate());
	
}

function addInfo(_this) {
    $("#addSubmit").unbind("click");
    var getDate = (new Date($(_this).attr("data-endTime") + '').getTime() - new Date($(_this).attr("data-startTime") + '').getTime()) / 1000 / 60 / 60 / 24;
    var time = $(_this).attr("data-startTime") + "<span class='infoEndTime'>至</span>" + $(_this).attr("data-endTime") + "<span class='infoEndTime'>共" + Math.floor(getDate + 1) + "天</span>";
    $("#addInfoDate").html(time)
    $("#addInfoMoney").text($(_this).attr("data-money"));
    $("#addSubmit").on("click", function () {
        if (isToday($(_this).attr("data-endTime"))) {
            simpleNotify("截至目前，需结算的交易金额已结算成功。")
            return false;
        }
        if ($(_this).attr("data-money") <= 0) {
            simpleNotify("当前选择日期，没有可结算的金额。")
            return false;
        }
        CommonBase.showLoading();
        $.ajax({
            url: $("#baseUrl").attr("data-url") + "/settlement/settleOrder",
            type: 'GET',
            data: {
                vendorId: $("#addBtn").attr("data-id"),
                balenceAcounts: $.trim($("#addInfoMoney").text()),
                startTime: $(_this).attr("data-startTime"),
                endTime: $(_this).attr("data-endTime")
            },
            success: function (data) {
                CommonBase.hideLoading();
                if (data.success) {
                    if (data.data.result) {
                        simpleNotify("交易金额已结算成功。");
                        $("#addInfoModal").modal('hide');
                        getVendorInfo($("#addBtn").attr("data-id"));
                    } else {

                        simpleNotify(data.data.message);
                    }

                }
            }
        })
    })
}
function getSettlementInfoById(vendorId,id){
    $('#infoOrderHistoryListTable').dataTable().fnDestroy();
    window.initPageTab({
        dom: $('#infoOrderHistoryListTable'),//table节点
        ajaxUrl: $("#baseUrl").attr("data-url") + "/settlement/pageSettlementedOrder",//ajax请求地址
        httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["orderId", "completeTime", "orderNo", "userName",  "couponAmount","originalPrice", "couponAmount", "payPrice"],//table要显示的列
        primaryKey: "orderId",//主键
        autoWidth: false,
        diyColumn: [     //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "orderId",//主键
                mRender: function (data, type, full, obj) {
                    return obj.row + 1
                }
            },
            {
                aTargets: [2],//要显示的位置
                mData: "orderId",//主键
                mRender: function (data, type, full, obj) {
                    return getFromatDay(new Date(data));
                }
            },
            {
                aTargets: [5],//要显示的位置
                mData: "orderId",//主键
                mRender: function (data, type, full, obj) {
                    return full.couponAmount==null ? "0" : "<p>" + full.couponAmount + "</p>"
                }
            },
            {
                aTargets: [7],//要显示的位置
                mData: "orderId",//主键
                mRender: function (data, type, full, obj) {
                    return full.payPrice == undefined ? "" : full.payPrice
                }
            }
        ],
        ajxaParam: function () {
            return {
            	  vendorId: vendorId,
            	  settlementId: id
            };
        }
    })
}
function getInfoDetail() {
    $('#infoesDetailTable').dataTable().fnClearTable();
    $('#infoesDetailTable').dataTable().fnDestroy();
    $("#infoDetailStartTime").datepicker('destroy');
    $("#infoDetailEndTime").datepicker('destroy');
    var  startDay=$("#infoStartTime").text();
    var  today=getFromatDay(new Date());
    $("#infoDetailStartTime").datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        nextText: '<i class="fa fa-chevron-right"></i>',
        prevText: '<i class="fa fa-chevron-left"></i>',
        minDate: startDay,
        maxDate: today,
        onClose: function (dateText, inst) {

        },
    }).val(startDay)
    $("#infoDetailEndTime").datepicker({
        dateFormat: "yy-mm-dd",
        changeMonth: true,
        changeYear: true,
        dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
        dayNamesMin: ["日", "一", "二", "三", "四", "五", "六",],
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        nextText: '<i class="fa fa-chevron-right"></i>',
        prevText: '<i class="fa fa-chevron-left"></i>',
        minDate: startDay,
        maxDate: today,
        onClose: function (dateText, inst) {

        },
    }).val(today);

    window.initPageTab({
        dom: $('#infoesDetailTable'),//table节点
        ajaxUrl: $("#baseUrl").attr("data-url") + "/settlement/pageVendorSettlementHistory",//ajax请求地址
        httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["id", "startTime", "balancedAmounts", "createUsername", "createTime"],//table要显示的列
        primaryKey: "id",//主键
        diyColumn: [     //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                    return obj.row + 1
                }
            },
            {
                aTargets: [2],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                	var startTime=new Date(full.startTime);
                	var endTime=new Date(full.endTime);
                    return  getFromatDay(startTime)+' 至 '+getFromatDay(endTime);
                }
            } ,
            {
                aTargets: [5],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                    return  getFromatDay(new Date(full.createTime));
                }
            },
            {
                aTargets: [6],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full) {//返回参数
                    return "<a href='#' data-toggle='modal' onclick='getSettlementInfoById(" +full.vendorId+","+ full.id + ")' data-backdrop='static' data-target='#historyListModal' class='tableBtn hidden-mobile'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/command.png' />查看</a>"
                }
            }
        ],
        ajxaParam: function () {
            return {
                vendorId: $("#addBtn").attr("data-id"),
                startTime: $("#infoDetailStartTime").val(),
                endTime: $("#infoDetailEndTime").val()
            };
        },
        fnFooterCallback: function () {
            $.ajax({
                url: $("#baseUrl").attr("data-url") + "/settlement/pageVendorSettlementHistory",
                type: 'get',
                data: {
                    vendorId: $("#addBtn").attr("data-id"),
                    startTime : $("#infoDetailStartTime").val(),
                    endTime: $("#infoDetailEndTime").val(),
                    pageIndex: 1,
                    pageSize: 10
                },
                success: function (result) {
                    if (result.success) {
                        $("#stillDetailMoney").text(result.extendMessage?result.extendMessage:0);
                    }
                }
            })
        }
    })

}

function userType(obj) {
    switch (obj) {
        case 'CREATE_COUPON_CODE_VENDOR_ADMIN':
            return "平台";
            break;
        case  'CREATE_COUPON_CODE':
            return "商家";
            break;
        case "CREATE_COUPON_CODE_VENDOR_CARRIER":
            return "运营商";
            break;
        default:
            return "其它";
    }
}

function isToday(time) {
    var _time = new Date(time).getTime();
    var _today = new Date(new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate()).getTime();
    return _time == _today;
}