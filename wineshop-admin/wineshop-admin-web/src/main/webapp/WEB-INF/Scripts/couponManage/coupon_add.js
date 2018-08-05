var state = 1;
var addr = '';
var cityCode = '';
$(function () {
    $("#beginTime2").datepicker({
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

    $("#endTime2").datepicker({
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

    //初始化调用省份
    $.ajax({
        type: "get",
        url: basePath + "/couponMain/ProvinceList",
        success: function (result) {
            for (var i = 0; i < result.data.length; i++) {
                $('#Province').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
            }
        }
    })

    //省市联动数据调用
    $('#Province').change(function () {
        $("#city").empty();
        $("#county").empty();
        $.ajax({
            type: "get",
            url: basePath + "/couponMain/cityList",
            data: {
                parent: $('#Province').val()
            },
            success: function (result) {
                for (var i = 0; i < result.data.length; i++) {
                    $('#city').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                }
            }
        })
    })

    //区域联动数据调用
    $('#city').change(function () {
        $("#county").empty();
        $.ajax({
            type: "get",
            url: basePath + "/couponMain/countyList",
            data: {
                parent: $('#city').val()
            },
            success: function (result) {
                for (var i = 0; i < result.data.length; i++) {
                    $('#county').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                }
            }
        })
    })

    //无条件使用的点击事件
    $("#term").click(function () {
        state = 0;
        $('#costLimitMoney').attr("disabled", true);
    })

    //满多少元点击事件
    $("#term2").click(function () {
        state = 1;
        $('#costLimitMoney').attr("disabled", false);
    })

    //保存事件操作
    $('#submit').click(function () {
        if ($('#title').val().trim() == '') {
            simpleNotify("优惠券名称不能为空");
            return false;
        }
        if ($('#Province').val().trim() == '') {
            simpleNotify("请选择发行地区");
            return false;
        }
        if ($('#money').val().trim() == '') {
            simpleNotify("面额不能为空");
            return false;
        }
        var re = /^[1-9]+[0-9]*]*$/;
        if ($('#money').val().trim() <= 0) {
            simpleNotify("面额必须大于0");
            return false;
        }
        if ($('#count').val().trim() == '') {
            simpleNotify("发行量不能为空");
            return false;
        }
        if (!re.test($('#count').val().trim())) {
            simpleNotify("发行量只能为正整数");
            return false;
        }
        if (state == 1) {
            if ($('#costLimitMoney').val().trim() == '') {
                simpleNotify("限制金额不能为空");
                return false;
            }
        }
        if ($('#beginTime2').val().trim() == '') {
            simpleNotify("生效时间不能为空");
            return false;
        }
        if ($('#endTime2').val().trim() == '') {
            simpleNotify("失效时间不能为空");
            return false;
        }
        var oDate1 = new Date($('#beginTime2').val().trim());
        var oDate2 = new Date($('#endTime2').val().trim());
        if (oDate1.getTime() > oDate2.getTime()) {
            simpleNotify("生效时间不能晚于失效时间");
            return false;
        }
        if ($('#city').val() == 0) {
            addr = $("#Province").find("option:selected").text() + "省";
            cityCode = $('#Province').val();
        }
        if ($('#city').val() != 0 && $('#county').val() == 0) {
            addr = $("#Province").find("option:selected").text() + "省" + $("#city").find("option:selected").text();
            cityCode = $('#city').val();
        }
        if ($('#city').val() != 0 && $('#county').val() != 0) {
            addr = $("#Province").find("option:selected").text() + "省" + $("#city").find("option:selected").text() + $("#county").find("option:selected").text();
            cityCode = $('#county').val();
        }

        if (state == 0) {
            var obj = {
                Money: $('#money').val().trim(),
                BeginTime: $('#beginTime2').val().trim(),
                EndTime: $('#endTime2').val().trim(),
                Count: $('#count').val().trim(),
                Title: $('#title').val().trim(),
                Condition: "无条件使用",
                Addr: addr,
                CityCode: cityCode

            };
        } else {
            var obj = {
                Money: $('#money').val().trim(),
                BeginTime: $('#beginTime2').val().trim(),
                EndTime: $('#endTime2').val().trim(),
                Count: $('#count').val().trim(),
                Title: $('#title').val().trim(),
                CostLimitMoney: $('#costLimitMoney').val().trim(),
                Addr: addr,
                CityCode: cityCode
            };
        }
        $.post(basePath + "/couponMain/couponAdd", obj, function (data) {
            if (data.success) {
                window.simpleNotify("保存成功", '提示', "success");
                $("#remoteModal").modal("hide");
                if (window.model && typeof (window.model.refreshTable) == 'function') {
                    window.model.refreshTable();
                    window.location.reload();
                }
            }
            else {
                window.simpleNotify("保存失败。", "提示", "error");
                return false;
            }
        })
    })
})
