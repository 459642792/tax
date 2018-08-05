/*
//页面初始化调用
var list_pageTable;
$(function(){
	$("#content_beginTime").datepicker({
		dateFormat: "yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
		dayNamesMin: ["日", "一", "二", "三", "四", "五", "六", ],
		monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		nextText: '<i class="fa fa-chevron-right"></i>',
		prevText: '<i class="fa fa-chevron-left"></i>',
		onClose: function (dateText, inst)
		{

		},
	});

	$("#content_endTime").datepicker({
		dateFormat: "yy-mm-dd",
		changeMonth: true,
		changeYear: true,
		dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
		dayNamesMin: ["日", "一", "二", "三", "四", "五", "六", ],
		monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
		nextText: '<i class="fa fa-chevron-right"></i>',
		prevText: '<i class="fa fa-chevron-left"></i>',
		onClose: function (dateText, inst)
		{

		},
	});
});	//页面初始化调用

/!**
 * 选择框全选
 *!/
function checkboxs(obj){
	$(".checkchild").prop("checked",obj.checked);
}
/!**
 * 列表选择框
 * @param obj
 *!/
function checkChild(obj){
	if(obj.checked){
		if($(".checkchild:checked").length == $(".checkchild").length){
			$(".checkchilds").prop("checked",obj.checked);
		}
	}else{
		$(".checkchilds").removeAttr("checked");
	}
}

/!**
 * 打开结算窗口
 * @param obj
 * @returns {Boolean}
 *!/
function settlementRecords(obj){

	var checkchilds = $(".checkchild:checked");
	if(checkchilds.length > 1 ){
		window.simpleNotify("只能选择1条数据", "提示", "warn");
		$("#remoteModal").css("display", "none");
		return false;
	}	
	if(checkchilds.length == 0 ){
		window.simpleNotify("至少选择一条数据", "提示", "warn");
		$("#remoteModal").css("display", "none");
		return false;
	}	

	var ids = checkchilds[0].id;	
	$("#settlement_records_id").val(ids);
	$("#settlement_records_moneys").html($(checkchilds[0]).attr("data-money") == 0 ?"0.00":$(checkchilds[0]).attr("data-money"));
	$('#settlement_records_money').val("");
	$("#settlement_records_myModalLabel").text("结算");
	$('#settlement_records_myModal').modal();
	$.ajax({
		url:  obj+ids,
		method: "get",
		dataType: "json",
		success: function (data) {
			$("#count_counts").html("");
			$("#count_discountAmounts").html("");
			$("#count_counts").html(data.data.counts);
			$("#count_discountAmounts").html(data.data.discountAmounts);
			$("#myModalLabel").text("统计");
			$('#myModal').modal();
		}
	});
}

/!**
 * 结算保存事件2017-04-10 10:39:24
 *!/
$('#settlement_records_submit').click(function () {
	var moneys = $.trim($('#settlement_records_money').val());
	if(moneys=='')
	{
		window.simpleNotify("请输入结算金额!", "提示", "warn");
		return false;
	}
	var reg = /^[0-9]+(.[0-9]{1,2})?$/;
	if(!reg.test(moneys) || moneys < 0){
		window.simpleNotify("结算金额必须大于0！", "提示", "warn");
		return false;
	}
	if(moneys > $("#settlement_records_moneys").html()){
		window.simpleNotify("结算金额大于可结算额！", "提示", "warn");
		return false;	
	}
	
	var obj = {
			amounts:$.trim($('#settlement_records_money').val()),
			vendorInfoId:$("#settlement_records_id").val()
	};
	$.post($("#settlement_records_pageContext").val()+"/settlementRecords/insertSettlementRecords", obj, function (res) {
		
		if (res.data.result) {
			window.simpleNotify(res.data.message, '提示', "success");
			$("#settlement_records_myModal").modal("hide");
			window.location.reload();
		} else {
			window.simpleNotify(res.data.message, "提示", "warn");
			$("#remoteModal").css("display", "none");
			$(".modal-backdrop .fade .in").remove();
			$(".modal-backdrop").remove();
			$("#settlement_records_myModalLabel").text("结算");
			$('#settlement_records_myModal').modal();
			return false;	
		}
	});
});


function listSettlementRecords(obj){
	var checkchilds = $(".checkchild:checked");
	if(checkchilds.length > 1 ){
		window.simpleNotify("只能选择1条数据", "提示", "warn");
		$("#remoteModal").css("display", "none");
		return false;
	}	
	if(checkchilds.length == 0 ){
		window.simpleNotify("至少选择一条数据", "提示", "warn");
		$("#remoteModal").css("display", "none");
		return false;
	}
	var ids = checkchilds[0].id;
	 if(list_pageTable != undefined){
		 $('#list_settlement_records').DataTable().clear();//清空数据.fnClearTable();//清空数据  
		 $('#list_settlement_records').DataTable().destroy(); //还原初始化了的datatable   
	 }
	 list_pageTable = {
			dom: $('#list_settlement_records'),//table节点
			ajaxUrl: obj,//ajax请求地址
			httpMethod: 'GET',//接口的请求方式方法分为get请求和post请求
			aoColumns: ["updateTime","amounts"],//table要显示的列
			primaryKey: "id",//主键
			diyColumn: [     //自定义列
			                 {
			                	 aTargets: [1],//要显示的位置
			                	 mData: "ID",//主键
			                	 mRender: function (data, type, full) {//返回参数
			                		 
			                		 $("#list_vendor_name").html(full.name);
			                		 $("#list_vendor_addr").html(full.addr);
			                		 $("#list_vendor_moneys").html(full.moneys == 0 ?"0.00":full.moneys);
			                		 return full.updateTime==-1?"":full.updateTime;
			                	 }
			                 },
			                 {
			                	 aTargets: [2],//要显示的位置
			                	 mData: "ID",//主键
			                	 mRender: function (data, type, full) {//返回参数
			                		 return full.amounts==-1?"":full.amounts;
			                	 }
			                 }
			                 ,
			                 {
			                	 aTargets: [3],//要显示的位置
			                	 mData: "ID",//主键
			                	 mRender: function (data, type, full) {//返回参数
			                		 return full.moneyss==-1?"":full.moneyss;
			                	 }
			                 }
			                 ,
			                 {
			                	 aTargets: [4],//要显示的位置
			                	 mData: "ID",//主键
			                	 mRender: function (data, type, full) {//返回参数
			                		 return full.updateTimes==-1?"":full.updateTimes;
			                	 }
			                 }
			                 ],
			                 ajxaParam: function ()
			                 {
			                	 return {
			                		 vendorInfoId :ids,
			                	 };
			                 },
	}	
	window.initPageTab(list_pageTable);
	$("#list_settlement_records_myModalLabel").text("结算记录");
	$('#list_settlement_records_myModal').modal();
}*/
pageSetUp();
var pageTable = {
    dom: $('#settlementRecord_list'),//table节点
    ajaxUrl: $("#baseUrl").attr("data-url") + "/settlementRecords/listLimitVendorSettlementRecords",//ajax请求地址
    httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["id", "name", "tradingArea", "discountAmounts", "amounts", "updateTime", "dayAmounts", "money"],//table要显示的列
    primaryKey: "id",//主键
    diyColumn: [     //自定义列
        {
            aTargets: [1],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full, obj) {
                return obj.row + 1
            },
            "bSortable": false
        },
        {
            aTargets: [3],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                return full.tradingArea == undefined ? "" : full.tradingArea;
            }
        },
        {
            aTargets: [5],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                return full.amounts == undefined ? 0 : full.amounts;
            }
        },
        /* {
             aTargets: [6],//要显示的位置
             mData: "ID",//主键
             mRender: function (data, type, full) {//返回参数
                 var today = new Date(new Date().getFullYear()+'-'+(new Date().getMonth()+1)+'-'+new Date().getDate()).getTime();
                 var newTime=full.updateTime.split('-');
                 newTime[1] = parseInt(newTime[1])+'';
                 newTime =newTime.join('-');
                 var endTime=new Date(newTime).getTime();
                 return today==endTime?newTime:getPrevDay(endTime);
                 return getPrevDay(endTime);
             }
         },*/
        {
            aTargets: [7],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                return full.dayAmounts == undefined ? 0 : full.dayAmounts;
            }
        },
        {
            aTargets: [8],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                return full.money == undefined ? "0.00" : full.money;
            }
        },
        {
            aTargets: [9],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                return "<a href='javascript:void(0)' data-toggle='modal' onclick='getVendorInfo(" + full.id + ")' data-backdrop='static' data-target='#infoModal' class='tableBtn hidden-mobile'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/command.png' />管理</a>"
            }
        }
    ],
    ajxaParam: function () {
        return {
            tradingArea: $("#content_tradingArea").val(),
            vendorName: $("#content_name").val(),
            beginTime: $("#content_beginTime").val(),
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
        url: $("#baseUrl").attr("data-url") + "/settlementRecords/getVendorInfoSettlement",
        type: "get",
        data: {vendorInfoId: id},
        success: function (data) {
            if (data.success) {
                $("#infoImage").attr("src", data.data[0].image);
                $("#infoName").text(data.data[0].name);
                $("#infoPhone").text(data.data[0].telephone);
                $("#infoAddr").text(data.data[0].addr);
                $("#infoManagerArea").text(data.data[0].managerArea);
                $("#infoMoneys").text(data.data[0].moneys);
                $("#infoAmounts").text(data.data[0].amounts);
                $("#infoCounts").text(data.data[0].counts);
                $("#infoForTen").text(data.data[0].forTen);
                $("#alipayCardBank").text()
                switch (data.data[0].billingInfoType) {
                    case 0:
                        $("#billingInfoType").text("无")
                        break;
                    case 1:
                        $("#billingInfoType").text(data.data[0].alipayCardBank == '' ? '无' : data.data[0].alipayCardBank + "(银行卡)")
                        break;
                    case 2:
                        $("#billingInfoType").text(data.data[0].alipayCardBank == '' ? '无' : data.data[0].alipayCardBank + "(支付宝)")
                        break;
                    default:
                        break;
                }
                var endTime = getNextDay(data.data[0].endDate);
                var allowStartTime = new Date(getNextDay(data.data[0].endDate).split("-")[0], parseInt(getNextDay(data.data[0].endDate).split("-")[1]) - 1, getNextDay(data.data[0].endDate).split("-")[2]);
                $("#infoStartTime").text(endTime);

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
                    maxDate: endTime == new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate() ? '-0d' : '-1d',
                    onClose: function (dateText, inst) {

                    },

                    onSelect: function (dateText, inst) {
                        $("#addBtn").attr("data-endTime", dateText);
                        $("#infoesTable").dataTable().fnDraw();
                        $.ajax({
                            url: $("#baseUrl").attr("data-url") + "/settlementRecords/listVendorInfoForTheSettlement",
                            type: "get",
                            data: {
                                vendorInfoId: id,
                                startDate: endTime,
                                endDate: $("#infoEndTime").val(),
                                pageIndex: 1,
                                pageSize: 10
                            },
                            success: function (data) {
                                if (data.success) {
                                    $("#stillMoney").text(data.extendMessage);
                                    $("#addBtn").attr("data-money", data.extendMessage)
                                }
                            }
                        })
                    },
                }).val(isToday(endTime) ? endTime : getPrevDay(new Date().getTime()));
                window.initPageTab({
                    dom: $('#infoesTable'),//table节点
                    ajaxUrl: $("#baseUrl").attr("data-url") + "/settlementRecords/listVendorInfoForTheSettlement",//ajax请求地址
                    httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
                    aoColumns: ["id", "payTime", "orderNo", "NickName", "userTypes", "totalMoney", "discountAmount", "money"],//table要显示的列
                    primaryKey: "id",//主键
                    autoWidth: false,
                    diyColumn: [     //自定义列
                        {
                            aTargets: [1],//要显示的位置
                            mData: "ID",//主键
                            mRender: function (data, type, full, obj) {
                                return obj.row + 1
                            }
                        },
                        {
                            aTargets: [5],//要显示的位置
                            mData: "ID",//主键
                            mRender: function (data, type, full, obj) {
                                return full.userTypes == undefined ? "无" : "<p>" + full.couponInfoName + "</p><p class='userType'><span>" + userType(full.userTypes) + "</span></p>"
                            }
                        },
                        {
                            aTargets: [8],//要显示的位置
                            mData: "ID",//主键
                            mRender: function (data, type, full, obj) {
                                return full.money == undefined ? "" : full.money
                            }
                        }
                    ],
                    ajxaParam: function () {
                        return {
                            vendorInfoId: id,
                            startDate: endTime,
                            endDate: $("#infoEndTime").val()
                        };
                    }
                })
                $.ajax({
                    url: $("#baseUrl").attr("data-url") + "/settlementRecords/listVendorInfoForTheSettlement",
                    type: "get",
                    data: {
                        vendorInfoId: id,
                        startDate: endTime,
                        endDate: $("#infoEndTime").val(),
                        pageIndex: 1,
                        pageSize: 10
                    },
                    success: function (data) {
                        if (data.success) {
                            $("#stillMoney").text(data.extendMessage);
                            $("#addBtn").attr("data-money", data.extendMessage);
                        }
                    }
                })
                $("#addBtn").attr("data-id", id);
                $("#addBtn").attr("data-startTime", endTime);
                $("#addBtn").attr("data-endTime", $("#infoEndTime").val());
                /*
               window.initPageTab({
                   dom: $('#infoesTable'),//table节点
                   ajaxUrl: $("#baseUrl").attr("data-url")+"/settlementRecords/listLimitVendorSettlementRecords",//ajax请求地址
                   httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
                   aoColumns: ["id","name","tradingArea","discountAmounts","amounts","updateTime","dayAmounts"],//table要显示的列
                   primaryKey: "id",//主键
                   diyColumn: [     //自定义列
                       {
                           aTargets: [1],//要显示的位置
                           mData: "ID",//主键
                           mRender: function (data, type, full,obj) {
                               return obj.row+1
                           },
                           "bSortable": false
                       }
                   ],
                   ajxaParam: function ()
                   {
                       return {
                           tradingArea : $("#content_tradingArea").val() ,
                           vendorName :$("#content_name").val() ,
                           beginTime : $("#content_beginTime").val() ,
                           endTime : $("#content_endTime").val()
                       };
                   },
               })*/
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
    return d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();

}

function getPrevDay(d) {
    d = +d - 1000 * 60 * 60 * 24;
    d = new Date(d);
    //return d;
    //格式化
    return d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();

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
            url: $("#baseUrl").attr("data-url") + "/settlementRecords/insertSettlementRecords",
            type: 'Post',
            data: {
                vendorInfoId: $("#addBtn").attr("data-id"),
                amounts: $(_this).attr("data-money"),
                startDate: $(_this).attr("data-startTime"),
                endDate: $(_this).attr("data-endTime")
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

function getInfoDetail() {
    $('#infoesDetailTable').dataTable().fnClearTable();
    $('#infoesDetailTable').dataTable().fnDestroy();
    $("#infoDetailStartTime").datepicker('destroy');
    $("#infoDetailEndTime").datepicker('destroy');
    $.ajax({
        url: $("#baseUrl").attr("data-url") + "/settlementRecords/getSettlementDate",
        type: 'get',
        data: {vendorInfoId: $("#addBtn").attr("data-id")},
        success: function (data) {
            if (data.success) {
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
                    minDate: data.data.startDate,
                    maxDate: data.data.endDate,
                    onClose: function (dateText, inst) {

                    },
                }).val(data.data.startDate)
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
                    minDate: data.data.startDate,
                    maxDate: data.data.endDate,
                    onClose: function (dateText, inst) {

                    },
                }).val(data.data.endDate);

                window.initPageTab({
                    dom: $('#infoesDetailTable'),//table节点
                    ajaxUrl: $("#baseUrl").attr("data-url") + "/settlementRecords/listVendorInfoForTheSettlement",//ajax请求地址
                    httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
                    aoColumns: ["id", "payTime", "orderNo", "NickName", "userTypes", "totalMoney", "discountAmount", "money"],//table要显示的列
                    primaryKey: "id",//主键
                    diyColumn: [     //自定义列
                        {
                            aTargets: [1],//要显示的位置
                            mData: "ID",//主键
                            mRender: function (data, type, full, obj) {

                                return obj.row + 1
                            }
                        },
                        {
                            aTargets: [4],//要显示的位置
                            mData: "NickName",//主键
                            mRender: function (data, type, full, obj) {
                                console.log( full.NickName);
                                return full.NickName == undefined || full.NickName == null ? "" : full.NickName
                            }
                        },
                        {
                            aTargets: [5],//要显示的位置
                            mData: "ID",//主键
                            mRender: function (data, type, full, obj) {
                                return full.userTypes == undefined ? "无" : "<p>" + full.couponInfoName + "</p><p class='userType'><span>" + userType(full.userTypes) + "</span></p>"
                            }
                        },
                        {
                            aTargets: [8],//要显示的位置
                            mData: "ID",//主键
                            mRender: function (data, type, full, obj) {
                                return full.money == undefined ? "" : full.money
                            }
                        }
                    ],
                    ajxaParam: function () {
                        return {
                            vendorInfoId: $("#addBtn").attr("data-id"),
                            startDate: $("#infoDetailStartTime").val(),
                            endDate: $("#infoDetailEndTime").val()
                        };
                    },
                    fnFooterCallback: function () {
                        $.ajax({
                            url: $("#baseUrl").attr("data-url") + "/settlementRecords/listVendorInfoForTheSettlement",
                            type: 'get',
                            data: {
                                vendorInfoId: $("#addBtn").attr("data-id"),
                                startDate: $("#infoDetailStartTime").val(),
                                endDate: $("#infoDetailEndTime").val(),
                                pageIndex: 1,
                                pageSize: 10
                            },
                            success: function (result) {
                                if (result.success) {
                                    $("#stillDetailMoney").text(result.extendMessage);
                                }
                            }
                        })
                    }
                })
            }
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