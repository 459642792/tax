//页面初始化调用
pageSetUp();

var pageTable = {
    dom: $('#dt_basic'),//table节点
    ajaxUrl: $("#baseUrl").attr("data-url") + "/goodsCashRecord/listGoodsCashRecord",//ajax请求地址
    httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["id", "orderNumber", "goodsName", "cashPrice", "counts", "vendorInfoName", "carriersInfoName", "createDate", "address", 'status'],//table要显示的列
    primaryKey: "id",
    autoWidth: false,
    "bProcessing": false, // 是否显示取数据时的那个等待提示
    diyColumn: [
        {
            aTargets: [1],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return obj.row + 1;
            }
        },
        {
            aTargets: [7],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return full.carriersInfoName == undefined ? "无" : full.carriersInfoName
            }
        },
        {
            aTargets: [10],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return full.status == 0 ? "未发货" : "已发货";
            }
        },
        {
            aTargets: [11],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                var links = []
                if (full.status == 0) {
                    links.push("<a class='tableBtn hidden-mobile' href='####' data-toggle='modal' data-backdrop='static' data-target='#expressGoodsModal' onclick='getId(" + full.id + ")' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/fahuo.png' /><span class='statusText'>发货</span></a>")
                    links.push("<a class='tableBtn hidden-mobile' href='####' onclick='cashDetail(" + full.id + ")' data-toggle='modal' data-backdrop='static' data-target='#getGoodsCashModal' onclick='getId(" + full.id + ")' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/jilu.png' /><span class='statusText'>查看</span></a>")
                } else {
                    links.push("<a class='tableBtn hidden-mobile' href='####' onclick='cashDetail(" + full.id + ")' data-toggle='modal' data-backdrop='static' data-target='#getGoodsCashModal' onclick='getId(" + full.id + ")'  ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/jilu.png' /><span class='statusText'>查看</span></a>")
                }
                return links.join(" ");
            }
        }
    ],
    ajxaParam: function () {
        return {
            cashStatus: $("#cashStatus option:selected").val(),
            orderNumber: $("#orderNumber").val().trim(),
            vendorInfoName: $("#vendorInfoName").val().trim(),
            beginTime: $.trim($('#beginTime').val()),
            endTime: $.trim($('#endTime').val())
        };
    },
};

/*获取ID*/
function getId(id) {
    $("#modalId").attr("data-id", id)
}

/*查看*/
function cashDetail(id) {
    $.ajax({
        url: $("#baseUrl").attr("data-url") + "/goodsCashRecord/getGoodsCashRecord",
        type: "Get",
        data: {goodsCashRecordId: id},
        success: function (data) {
            if (data.success) {
                $("#cashOrderNumber").text(data.data.orderNumber);
                $("#productName").text(data.data.goodsName);                 //???
                $("#cashCashPrice").text(data.data.cashPrice);
                $("#cashCounts").text(data.data.counts);
                $("#cashShop").text(data.data.vendorInfoName);                                 //???
                $("#caseUserAdress").text(data.data.address);                      //??
                $("#cashCreateDate").text(data.data.createDate);
                $("#cashExpressDate").text(data.data.expressDate);
                $("#cashExpressInfo").text(data.data.expressCompany + " " + data.data.expressNumbers);

            } else {
                simpleNotify("查看失败");
            }
        }
    })
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
        var reg = /^(?:(?:(?:(?:(?:1[6-9]|[2-9][0-9])?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))([-/.])(?:0?2\1(?:29)))|(?:(?:(?:1[6-9]|[2-9][0-9])?[0-9]{2})([-/.])(?:(?:(?:0?[13578]|1[02])\2(?:31))|(?:(?:0?[13-9]|1[0-2])\2(?:29|30))|(?:(?:0?[1-9])|(?:1[0-2]))\2(?:0?[1-9]|1[0-9]|2[0-8]))))$/;
        if (!reg.test($("#beginTime").val())) {
            if ($("#beginTime").val() != '') {
                simpleNotify("请选择正确的开始日期");
                return false;
            }
        }
        if (!reg.test($("#endTime").val())) {
            if ($("#endTime").val() != '') {
                simpleNotify("请选择正确的结束日期");
                return false;
            }
        }
        if (pageTable) {
            pageTable.container.fnDraw();
        }
    });
    /*发货方法*/
    $("#expressSubmit").on("click", function () {
        if ($("#expressCompany").val() == "") {
            simpleNotify("请输入快递公司");
            return false;
        }
        if (!/^[A-Za-z0-9]+$/.test($("#expressNumbers").val())) {
            simpleNotify("请输入正确的快递单号");
            return false;
        }
        var obj = {
            expressCompany: $("#expressCompany").val().trim(),
            expressNumbers: $("#expressNumbers").val().trim(),
            goodsCashRecordId: $("#modalId").attr("data-id")
        }
        $.ajax({
            url: $("#baseUrl").attr("data-url") + "/goodsCashRecord/expressGoods",
            type: "POST",
            data: obj,
            success: function (data) {
                if (data.success) {
                    simpleNotify("发货成功");
                    window.location.reload();
                } else {
                    simpleNotify("发货失败");
                }
            }
        })
    })
    window.initPageTab(pageTable);
})()