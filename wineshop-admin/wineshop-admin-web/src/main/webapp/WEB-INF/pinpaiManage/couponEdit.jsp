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
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title">修改优惠券</h4>
</div>
<div class="modal-body no-padding">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">优惠券名称</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" id="title" name="title" class="input" maxlength="15"
                                   placeholder="例:xxxxxx..."/>
                            <b class="tooltip tooltip-top-right">请输入优惠券名称</b>
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">发行地区</label>
                    <div class="col col-10">
                        <select class="input-sm" id="Province" name="Province" onchange="chg()" style="width:200px">
                        </select>
                        <select class="input-sm Town" id="city" name="city" style="width:200px">
                        </select>
                        <select class="input-sm Town" id="county" name="county" style="width:220px">
                        </select>
                        </label>
                    </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">发行面额(元)</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="number" id="money" name="money" class="input" maxlength="6"
                                   placeholder="请输入发行面额"/>
                            <b class="tooltip tooltip-top-right">请输入发行面额</b>
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">发行量(张)</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="number" id="count" name="count" class="input" maxlength="6"
                                   placeholder="请输入发行量"/>
                            <b class="tooltip tooltip-top-right">请输入发行量</b>
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">使用条件</label>
                    <div class="col col-10">
                        <input type="radio" name="term" id="term" style="width:15px;height:15px">&nbsp;无条件使用</br>
                        <input type="radio" name="term" id="term2" style="width:15px;height:15px" checked="checked">
                        满
                        <input id="costLimitMoney" name="costLimitMoney"
                               style="border-bottom:1px solid #808080;width:50px;margin-top:10px" contenteditable="true"
                               maxlength="5" onkeyup="if(isNaN(value))execCommand('undo')"
                               onafterpaste="if(isNaN(value))execCommand('undo')">&nbsp;元使用
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">生效时间</label>
                    <div class="col col-10">
                        <label class="input" id="datatimebeg">
                            <i class="icon-append fa fa-calendar"></i>
                            <input type="text" class="form-control" data-date-format="yyyy-mm-dd" id="beginTime2"
                                   name="beginTime2" placeholder="优惠券开始时间">
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">失效时间</label>
                    <div class="col col-10">
                        <label class="input" id="datatimeend">
                            <i class="icon-append fa fa-calendar"></i>
                            <input type="text" class="form-control" data-date-format="yyyy-mm-dd" id="endTime2"
                                   name="endTime2" placeholder="优惠券结束时间">
                        </label>
                    </div>
                </div>
            </section>
        </fieldset>
        <footer>
            <button type="submit" class="btn btn-primary" id="submit">
                <i class="fa fa-save"></i>
                保存
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
                取消
            </button>
        </footer>
    </form>
</div>
<script type="text/javascript">
    var state = 1;
    var CityCode = '';
    var couponId = 0;
    var totalCount = 0;
    var dates = '';
    var couponState = '';
    $(function () {
        //修改初始化
        $.ajax({
            type: "get",
            url: "${pageContext.request.contextPath}/couponMain/couponDetail",
            data: {
                couponId: '${Id}'
            },
            success: function (result) {
                $('#title').val(result.data.title);
                $('#money').val(result.data.money);
                $('#count').val(result.data.count);
                $('#beginTime2').val(result.data.beginTime.substring(0, 10));//生效时间
                $('#endTime2').val(result.data.endTime.substring(0, 10));//失效时间
                dates = result.data.endTime;
                totalCount = result.data.count;
                couponId = result.data.id;
                couponState = result.data.statusStr;
                if (result.data.condition == null || result.data.condition == "") {
                    $("#term2").attr("checked", "checked");
                    state = 1;
                    $("#costLimitMoney").val(result.data.costLimitMoney == null || (result.data.costLimitMoney == "") ? 0 : result.data.costLimitMoney);
                }
                else {
                    $("#term").attr("checked", "checked");
                    state = 0;
                }
                if (result.data.statusStr == "进行中") {
                    $("#title").attr("disabled", true);
                    $("#beginTime2").attr("disabled", true);
                    $("#money").attr("disabled", true);
                    $("#Province").attr("disabled", true);
                    $("#city").attr("disabled", true);
                    $("#county").attr("disabled", true);
                    $("#term").attr("disabled", true);
                    $("#term2").attr("disabled", true);
                    $("#costLimitMoney").attr("disabled", true);
                }
                if (result.data.statusStr == "已过期" || result.data.statusStr == "已停止") {
                    $("#title").attr("disabled", true);
                    $("#beginTime2").attr("disabled", true);
                    $("#money").attr("disabled", true);
                    $("#Province").attr("disabled", true);
                    $("#city").attr("disabled", true);
                    $("#county").attr("disabled", true);
                    $("#term").attr("disabled", true);
                    $("#term2").attr("disabled", true);
                    $("#costLimitMoney").attr("disabled", true);
                    $("#count").attr("disabled", true);
                    $("#endTime2").attr("disabled", true);
                }
                CityCode = result.data.cityCode;
                //表示选择的全国
                if (result.data.cityCode == null || result.data.cityCode == "") {
                    $.ajax({
                        type: "get",
                        url: "${pageContext.request.contextPath}/couponMain/ProvinceList",
                        success: function (result) {
                            for (var i = 0; i < result.data.length; i++) {
                                if (result.data[i].code == 0) {
                                    $('#Province').append("<option value=" + result.data[i].code + " selected='selected'>" + result.data[i].name + "</option>");
                                } else {
                                    $('#Province').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                                }

                            }
                        }
                    })
                }
                else {
                    //选择不是全国
                    //正则匹配
                    var count = 0;//计算下划线的个数
                    for (var i = 0; i < result.data.cityCode.length; i++) {
                        if (result.data.cityCode[i] == "_") {
                            count++;
                        }
                    }
                    if (count == 0) {
                        //表示只有省，省下面选择的全市
                        $.ajax({
                            type: "get",
                            url: "${pageContext.request.contextPath}/couponMain/ProvinceList",
                            success: function (result) {
                                for (var i = 0; i < result.data.length; i++) {
                                    if (result.data[i].code == CityCode.split("_")[0]) {
                                        $('#Province').append("<option value=" + result.data[i].code + " selected='selected'>" + result.data[i].name + "</option>");
                                    } else {
                                        $('#Province').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                                    }

                                }
                            }
                        })
                    }
                    else if (count == 1) {
                        //选择市
                        $.ajax({
                            type: "get",
                            url: "${pageContext.request.contextPath}/couponMain/ProvinceList",
                            success: function (result) {
                                for (var i = 0; i < result.data.length; i++) {
                                    if (result.data[i].code == CityCode.split("_")[0]) {
                                        $('#Province').append("<option value=" + result.data[i].code + " selected='selected'>" + result.data[i].name + "</option>");
                                    } else {
                                        $('#Province').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                                    }

                                }
                            }
                        })

                        //绑市级数据列表
                        $.ajax({
                            type: "get",
                            url: "${pageContext.request.contextPath}/couponMain/cityList",
                            data: {
                                parent: CityCode.split("_")[0]
                            },
                            success: function (result) {
                                for (var i = 0; i < result.data.length; i++) {
                                    if (result.data[i].code == CityCode) {
                                        $('#city').append("<option value=" + result.data[i].code + " selected='selected'>" + result.data[i].name + "</option>");
                                    } else {
                                        $('#county').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                                    }

                                }
                            }
                        })

                    }
                    else {
                        //选择市
                        $.ajax({
                            type: "get",
                            url: "${pageContext.request.contextPath}/couponMain/ProvinceList",
                            success: function (result) {
                                for (var i = 0; i < result.data.length; i++) {
                                    if (result.data[i].code == CityCode.split("_")[0]) {
                                        $('#Province').append("<option value=" + result.data[i].code + " selected='selected'>" + result.data[i].name + "</option>");
                                    } else {
                                        $('#Province').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                                    }

                                }
                            }
                        })

                        //绑市级数据列表
                        $.ajax({
                            type: "get",
                            url: "${pageContext.request.contextPath}/couponMain/cityList",
                            data: {
                                parent: CityCode.split("_")[0]
                            },
                            success: function (result) {
                                for (var i = 0; i < result.data.length; i++) {
                                    if (result.data[i].code == (CityCode.split("_")[0] + "_" + CityCode.split("_")[1])) {
                                        $('#city').append("<option value=" + result.data[i].code + " selected='selected'>" + result.data[i].name + "</option>");
                                    } else {
                                        $('#city').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                                    }

                                }
                            }
                        })

                        //区域级数据列表
                        $.ajax({
                            type: "get",
                            url: "${pageContext.request.contextPath}/couponMain/countyList",
                            data: {
                                parent: CityCode.split("_")[0] + "_" + CityCode.split("_")[1]
                            },
                            success: function (result) {
                                for (var i = 0; i < result.data.length; i++) {
                                    if (result.data[i].code == CityCode) {
                                        $('#county').append("<option value=" + result.data[i].code + " selected='selected'>" + result.data[i].name + "</option>");
                                    } else {
                                        $('#county').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                                    }

                                }
                            }
                        })
                    }
                }
            }
        })

        //日期控件进行操作
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

        //初始化调用省份
        /* $.ajax({
             type:"get",
             url:"
        ${pageContext.request.contextPath}/couponMain/ProvinceList",
             success: function (result) {
            	 for(var i=0;i<result.data.length;i++)
                 {
                	 $('#Province').append("<option value="+ result.data[i].code+">"+ result.data[i].name+"</option>"); 
                 }
             }
    	 }) */

        //省市联动数据调用
        $('#Province').change(function () {
            $("#city").empty();
            $("#county").empty();
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/couponMain/cityList",
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
                url: "${pageContext.request.contextPath}/couponMain/countyList",
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

        //修改保存操作
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
            if (totalCount > $('#count').val().trim()) {
                simpleNotify("发行量不能少于原本的发行量");
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
            var oDate3 = new Date(dates);
            if (oDate1.getTime() > oDate2.getTime()) {
                simpleNotify("生效时间不能晚于失效时间");
                return false;
            }
            if (couponState == "进行中") {
                if (oDate3.getTime() > oDate2.getTime()) {
                    simpleNotify("失效时间不能晚于原本的失效时间");
                    return false;
                }
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
                    Id: couponId,
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
                    Id: couponId,
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
            $.post("${pageContext.request.contextPath}/couponMain/couponUpdate", obj, function (data) {
                if (data.success) {
                    window.simpleNotify("保存成功", '提示', "success");
                    $("#remoteModal").modal("hide");
                    if (window.model && typeof (window.model.refreshTable) == 'function') {
                        window.model.refreshTable();
                        // window.location.reload();
                    }
                }
                else {
                    window.simpleNotify("保存失败。", "提示", "error");
                    return false;
                }
            })
        })
    })
</script>
</body>
</html>
