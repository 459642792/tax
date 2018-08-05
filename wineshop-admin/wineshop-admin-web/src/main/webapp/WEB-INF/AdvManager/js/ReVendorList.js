var img;
//页面初始化调用
pageSetUp();
/*render主表格*/
var pageTable = {
    dom: $('#dt_basic'),//table节点
    ajaxUrl: $("#baseUrl").attr("data-url") + "/recommVendor/reVendorList",//ajax请求地址
    httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["id", 'vendorName', 'tradingArea', 'carriersName', 'createDate', 'orderField', 'clickCount', 'createBy'],//table要显示的列
    primaryKey: "id",
    bProcessing: false,
    diyColumn: [
        {
            aTargets: [1],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return obj.row + 1;
            }
        },
        {
            aTargets: [5],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full, obj) {
                return full.createDate.split(":")[0] + ":" + full.createDate.split(":")[1];
            }
        },
        {
            /*操作按钮组*/
            aTargets: [9],//要显示的位置
            mData: "id",//主键
            mRender: function (data, type, full) {
                var links = [];
                links.push("<a style='display: inline-block'  class='tableBtn hidden-mobile' href='JavaScript:void(0)' onclick='getCtorl(" + full.vendorId + ")' data-toggle='modal' data-backdrop='static' data-target='#countModal'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/edit.png' />编辑</a>");
                links.push("<a style='display: inline-block'  class='tableBtn hidden-mobile' href='JavaScript:void(0)' onclick='delShop(" + full.id + ")' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/del.png' />删除</a>");
                return links.join(" ");
            }
        }

    ],
    ajxaParam: function () {
        return {
            VendorName: $("#productName").val().trim(),
            TradingArea: $("#brande").val().trim()
        };
    }
};
(function () {
    var url = $("#baseUrl").attr("data-url");
    $("#btnSearch").click(function () {
        if (pageTable) {
            pageTable.container.fnDraw();
        }
    });
    window.initPageTab(pageTable);
    getProvinceList($('#Province'));
    $("#Province").on("change", function () {
        var val = $(this).find("option:selected").val();
        if (val == 0) {
            $("#city").hide().empty();
            $("#county").hide().empty();
            $('#vendorName').attr("disabled", "disabled").val('0');
        } else {
            $('#vendorName').attr("disabled", "disabled").val('0');
            $("#city").empty();
            $("#county").empty();
            $.ajax({
                type: "get",
                url: $("#baseUrl").attr("data-url") + "/couponMain/cityList",
                data: {
                    parent: val
                },
                success: function (result) {
                    var str = '';
                    for (var i = 0; i < result.data.length; i++) {
                        str += "<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>";
                    }
                    $("#city").show().append(str);
                    $("#county").hide()
                }
            });
        }
    })
    $("#city").on("change", function () {
        var val = $(this).find("option:selected").val();
        if (val == 0) {
            $("#county").empty().hide();
            $('#vendorName').attr("disabled", "disabled").val('0');
        } else {
            $('#vendorName').attr("disabled", "disabled").val('0');
            $("#county").empty();
            $.ajax({
                type: "get",
                url: $("#baseUrl").attr("data-url") + "/couponMain/countyList",
                data: {
                    parent: val
                },
                success: function (result) {
                    var str = '';
                    for (var i = 0; i < result.data.length; i++) {
                        str += "<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>";
                    }
                    $('#county').show().append(str)
                }
            })
        }
    });
    $("#county").on("change", function () {
        var val = $(this).find("option:selected").val();
        if (val == 0) {
            $('#vendorName').attr("disabled", "disabled").val('0');
        } else {
            $.ajax({
                type: "get",
                url: $("#baseUrl").attr("data-url") + "/discover/ReVendorList",
                data: {
                    cityCode: val
                },
                success: function (result) {
                    if (result.success) {
                        $('#vendorName').empty();
                        var str = ' <option value="0">--请选择商家--</option>';
                        for (var i = 0; i < result.data.length; i++) {
                            str += "<option value=" + result.data[i].id + " data-area=" + result.data[i].tradingArea + ">" + result.data[i].name + "</option>";
                        }
                        $('#vendorName').removeAttr("disabled").append(str)
                    }
                }
            })
        }
    })
    /*新增*/
    $("#addSubmit").on("click", function () {
        if ($("#county").val() == 0 || $("#county").val() == '') {
            simpleNotify("请选择完整的地区");
            return false;
        }
        if ($('#vendorName').val() == 0 || $('#vendorName').val() == '') {
            simpleNotify("请选择店铺");
            return false;
        }
        var obj = {
            AreaAddr: $('#county').val(),
            VendorName: $('#vendorName option:selected').text(),
            TradingArea: $('#Province option:selected').text() + $('#city option:selected').text() + $('#county option:selected').text(),
            vendorId: $('#vendorName option:selected').val()
        };
        CommonBase.showLoading();
        $.ajax({
            url: $("#baseUrl").attr("data-url") + "/recommVendor/reVendorAdd",
            type: "Post",
            data: obj,
            success: function (data) {

                if (data.success) {
                    CommonBase.hideLoading();
                    simpleNotify("新增成功");
                    if (data.count > 1) {
                        $("#countModal").modal({backdrop: false});
                        $("#remoteModal").modal('hide');
                        getCtorl(data.data);
                    }
                    $("#remoteModal").modal('hide');
                    $("#dt_basic").data
                    window.location.reload();
                    //window.location.reload();
                }
            }
        })
    })
    /*排序*/
    $("#sortSub").on("click", function () {
        var idItem = []
        var allId = $("#dt_count tbody .myNum");
        for (var i = 0; i < allId.length; i++) {
            idItem.push(allId.eq(i).attr("data-id"));
        }
        ;
        $.ajax({
            url: $("#baseUrl").attr("data-url") + "/recommVendor/reVendorSort",
            type: "get",
            data: {LstSortId: JSON.stringify(idItem)},
            success: function (data) {
                if (data.success) {
                    simpleNotify("排序成功");
                    window.location.reload();
                }
            }
        })
    })
})();

function getProvinceList(dom) {
    //初始化调用省份
    $.ajax({
        type: "get",
        url: $("#baseUrl").attr("data-url") + "/couponMain/ProvinceList",
        success: function (result) {
            var str = '';
            for (var i = 0; i < result.data.length; i++) {
                str += "<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>";
            }
            dom.append(str)
        }
    })
}

function delShop(id, _this) {
    $.confirm({
        msg: "是否确认删除？",
        confirm: function () {
            $.ajax({
                url: $("#baseUrl").attr("data-url") + "/recommVendor/reVendorDelete",
                type: "get",
                data: {Id: id},
                success: function (data) {
                    if (data.success) {
                        simpleNotify("删除成功");
                        if (_this) {
                            $(_this).parent().parent().remove();
                            getReNum($("#dt_count tbody .myNum"));
                        }
                        pageTable.container.fnDraw();
                    }
                }
            })
        },
        cancel: function () {
            return false;
        }
    });

}

function getCtorl(id) {
    $.ajax({
        url: $("#baseUrl").attr("data-url") + "/recommVendor/cityVendorList",
        type: "get",
        data: {Id: id},
        success: function (data) {
            if (data.success) {
                $("#dt_count tbody").empty();
                var str = ''
                for (var i = 0; i < data.data.length; i++) {
                    str += "<tr><td class='myNum' data-id='" + data.data[i].vendorId + "'>" + (i + 1) + "</td><td>" + data.data[i].vendorName + "</td><td>";
                    if (data.data[i].vendorId == id) {
                        if (i != 0) {
                            str += "<a href='javascript:void(0)' onclick='sortNum(this)' class='tableBtn hidden-mobile'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/sort.png' />升序</a>"
                        }
                        str += "<a href='javascript:void(0)' onclick='delShop(" + data.data[i].id + ",this);' class='tableBtn hidden-mobile'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/del.png' />删除</a>"
                    }
                    str += "</td></tr>"
                }
                $("#dt_count tbody").append(str);
            }
        }
    })
};

function sortNum(_this) {
    var _tr = $(_this).parent().parent();
    if (_tr.index() > 1) {
        _tr.prev().before("<tr>" + _tr.html() + "</tr>");
        _tr.remove();
        getReNum($("#dt_count tbody .myNum"));
        $(_this).remove();

    } else if (_tr.index() == 1) {
        $(_this).remove();
        _tr.prev().before("<tr>" + _tr.html() + "</tr>");
        _tr.remove();
        getReNum($("#dt_count tbody .myNum"));
    }
}

function getReNum(arr) {
    for (var i = 0; i < arr.length; i++) {
        arr.eq(i).text(i + 1);
    }
}

function initForm() {
    $("#Province").val(0);
    $("#city").hide();
    $("#county").hide();
    $('#vendorName').attr("disabled", "disabled").val('0');
}