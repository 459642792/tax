//页面初始化调用

$(function () {
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
        onClose: function (dateText, inst) {

        },
    });
});	//页面初始化调用

/**
 * 选择框全选
 */
function checkboxs(obj) {
    $(".checkchild").prop("checked", obj.checked);
}

/**
 * 列表选择框
 * @param obj
 */
function checkChild(obj) {
    if (obj.checked) {
        if ($(".checkchild:checked").length == $(".checkchild").length) {
            $(".allCheckchilds").prop("checked", true);
        }
    } else {
        $(".allCheckchilds").prop("checked", false);
    }
}

function countPage(obj) {
    var checkchilds = $(".checkchild:checked");
    if (checkchilds.length <= 1) {
        window.simpleNotify("必须选择2条(含)以上数据！", "提示", "warn");
        $("#remoteModal").css("display", "none");
        return false;
    }
    var ids = "";
    $.each(checkchilds, function (key, value) {
        ids += value.id + ",";
    });
    ids = ids.substr(0, ids.length - 1);
    $.ajax({
        url: obj + ids,
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
