var dataUrl = $("#baseUrl").attr("data-url") + "/auditGoods/";
var pageTable;
var ediotr;
var editorDetails;
var auditFlag = false;//状态 代表全部可以编辑

var auditEdiotrFlag = false;//文本框不可编辑

var auditEditorDetails = "";
$(function () {
    pageSetUp();
    listAuditGoods();
    //查询
    $("#btnSearch").click(function () {
        // if (pageTable) {
        //     pageTable.container.fnDraw();
        // }
        listAuditGoods();
    });
    $("#audit_goods_startTime").datepicker({
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

    $("#audit_goods_endTime").datepicker({
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

    //查看
    $("#aduit_goods").click(function () {
        $("#audit_goods_detail_modal").text("商品审核");
        CommonBase.showLoading();
        var id = $("input:radio[name='model_goods_id']:checked").attr("id");
        if (id == null || id == "") {
            simpleNotify("请选择一条数据")
            CommonBase.hideLoading();
            return false;
        }
        $.get(dataUrl + "getByAuditGoodsId", {auditGoodsId: parseInt(id)}, function (data) {
            if (data.success == false) {
                simpleNotify(data.message);
                CommonBase.hideLoading();
                return false;
            } else {
                $('#audit_goods_detail').modal();
                var pinPais = data.data.pingpai.pingpai;
                auditFlag = data.data.pingpai.disabled;
                if (pinPais != undefined && pinPais.length > 0) {
                    var pPs = new Array();
                    $.each(pinPais, function (key, value) {
                        if (value.selected) {
                            pPs.push("<option id=" + value.pinPaiId + " selected ='selected'>" + value.pinPaiName + "</option>")
                        } else {
                            pPs.push("<option id=" + value.pinPaiId + ">" + value.pinPaiName + "</option>")
                        }
                    });
                    $("#audit_pinpai_name").html(pPs.join());
                }
                ediotr = "";
                editorDetails = "";
                $("#audit_goods_detail_pinpai").nextAll().remove();
                UE.delEditor('container');
                var _attribute = data.data.attribute;
                if (_attribute != undefined && _attribute != null) {
                    var attribute = new Array;
                    $("#audit_goods_detail_pinpai").next().nextAll().remove();
                    $.each(_attribute, function (key, value) {
                        processAttributeAudit(value.attribute, value.goodsAttributeInfo, attribute);
                    });
                    $("#audit_goods_detail_pinpai").after(attribute.join());
                    ediotr = UE.getEditor('container');
                    ediotr.ready(function () {
                        //设置编辑器的内容
                        ediotr.setContent(editorDetails);
                    });
                    if (!auditEdiotrFlag) {
                        ediotr.setDisabled();
                    }
                    imageDetailClick();
                    $("#table_audit_goods_detail").nextAll().remove();
                    if (auditFlag) {
                        var detailHtml = "<div class='modal-footer'>" +
                            "<button type='button' class='btn btn-default' id='reject_audit_goods' style='width: 200px;height: 32px;'>拒绝</button>" +
                            "<button type='button' class='btn btn-primary' id='save_detail_vendor_goods' style='width: 50px;height: 32px;'>通过</button> </div>";
                        $("#table_audit_goods_detail").after(detailHtml);
                        // updateVendorModelGoods(goodsId);
                    } else {
                        var detailHtml = "<div class='modal-footer'>" +
                            "<button type='button' class='btn btn-default' id='reject_audit_goods' style='width: 200px;height: 32px;'>拒绝</button>" +
                            "<button type='button' class='btn btn-primary' id='save_detail_vendor_goods' style='width: 200px;height: 32px;'>通过并添加到库</button> </div>";
                        $("#table_audit_goods_detail").after(detailHtml);
                    }
                    saveAuditGoods(id);
                    // updateModelGoods();
                }
            }
        });
        window.initPageTab({
            dom: $('#relevance_vendor_infoesTable'),//table节点
            ajaxUrl: dataUrl + "/listByModeGoodIdVendor",//ajax请求地址
            httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
            aoColumns: ["id", "vendorName", "phone", "addr", "createDate"],//table要显示的列
            primaryKey: "id",//主键
            autoWidth: false,
            diyColumn: [     //自定义列
                {
                    aTargets: [1],//要显示的位置
                    mData: "id",//主键
                    mRender: function (data, type, full, obj) {
                        return obj.row + 1;
                    }
                },
                {
                    aTargets: [2],//要显示的位置
                    mData: "vendorName",//主键
                    mRender: function (data, type, full, obj) {
                        return full.vendorName == undefined ? "" : full.vendorName;
                    }
                },
                {
                    aTargets: [3],//要显示的位置
                    mData: "phone",//主键
                    mRender: function (data, type, full, obj) {
                        return full.phone == undefined ? "" : full.phone;
                    }
                }
                ,
                {
                    aTargets: [4],//要显示的位置
                    mData: "addr",//主键
                    mRender: function (data, type, full, obj) {
                        return full.addr == undefined ? "" : full.addr;
                    }
                }
                ,
                {
                    aTargets: [5],//要显示的位置
                    mData: "createDate",//主键
                    mRender: function (data, type, full, obj) {
                        return full.createDate == undefined ? "" : getMyDate(full.createDate);
                    }
                }
                ,
                {
                    aTargets: [6],//要显示的位置
                    mData: "id",//主键
                    mRender: function (data, type, full, obj) {
                        return " <input type='button' class='btn btn-primary' onclick='vendorGoodsDetail(" + full.id + ")' value='详情'/>"
                    }
                }
            ],
            ajxaParam: function () {
                return {
                    modelGoodsId: id,
                };
            }
        })
        $("#myTab li").removeClass("active");
        $("#myTab li:first").addClass("in active");
        $("#myTabContent div").removeClass("active");
        $("#myTabContent div:first").addClass("in active");
        CommonBase.hideLoading();
    });


});	//页面初始化调用


function listAuditGoods() {
    pageTable = {
        dom: $('#audit_goods_list'),//table节点
        ajaxUrl: dataUrl + "listByAuditGoods",//ajax请求地址
        httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["id", "name", "pinpaiName", "shopTypeName", "origin", "pack", "price", "vendorName", "createDate",],//table要显示的列
        primaryKey: "id",//主键
        diyColumn: [     //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                    $(".checkchilds").removeAttr("checked");
                    return "<input type='radio'  class='checkchild' style='text-align: center;' name='model_goods_id' onclick='checkChild(this)' id='" + full.id + "' />";
                },
                "bSortable": false
            },
            {
                aTargets: [2],//要显示的位置
                mData: "name",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.name == undefined ? "" : full.name;
                }
            },
            {
                aTargets: [3],//要显示的位置
                mData: "pinpaiName",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.pinpaiName == undefined ? 0 : full.pinpaiName;
                }
            },
            {
                aTargets: [4],//要显示的位置
                mData: "shopTypeName",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.shopTypeName == undefined ? 0 : full.shopTypeName;
                }
            },
            {
                aTargets: [5],//要显示的位置
                mData: "origin",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.origin == undefined ? "0.00" : full.origin;
                }
            },
            {
                aTargets: [6],//要显示的位置
                mData: "pack",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.pack == undefined ? "0.00" : full.pack;
                }
            },
            {
                aTargets: [7],//要显示的位置
                mData: "price",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.price == undefined ? "" : full.price + "元";
                }
            },
            {
                aTargets: [8],//要显示的位置
                mData: "vendorName",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.vendorName == undefined ? 0 : full.vendorName;
                }
            },
            {
                aTargets: [9],//要显示的位置
                mData: "updateDate",//主键
                mRender: function (data, type, full) {//返回参数
                    return full.createDate == undefined ? "" : getMyDate(full.createDate);
                }
            },

        ],
        ajxaParam: function () {
            return {
                pinPaiName: $("#pinpai_name").val(),
                goodsName: $("#goods_name").val(),
                beginDate: $("#audit_goods_startTime").val(),
                endDate: $("#audit_goods_endTime").val()
            };
        },
    }
    window.initPageTab(pageTable);
}


function processAttributeAudit(attributes, goodsAttributeInfo, attribute) {
    var html = "";
    switch (goodsAttributeInfo.goodsAttributeEnum) {
        case 0 :
            var inputBox = attributes.inputBox == null ? '' : attributes.inputBox;
            if (goodsAttributeInfo.goodsAttributeName == "价格") {
                html = " <tr>" +
                    "<td  style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                    "<td data-name='input'><input type='text'  disabled='true'  style='min-width: 180px;' data-id='" + goodsAttributeInfo.id + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "' value='" + inputBox + "'/></td>" +
                    "</tr>";
            } else {
                if (auditFlag) {
                    html = " <tr>" +
                        "<td  style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                        "<td data-name='input'><input type='text'   style='min-width: 180px;' data-id='" + goodsAttributeInfo.id + "' disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "' value='" + inputBox + "' /></td>" +
                        "</tr>";
                } else {
                    html = " <tr>" +
                        "<td  style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                        "<td data-name='input'><input type='text'    style='min-width: 180px;' data-id='" + goodsAttributeInfo.id + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "' value='" + inputBox + "' /></td>" +
                        "</tr>";
                }
            }
            break;
        case 1 :
            var values = new Array();
            if (attributes.selectBox != undefined && attributes.selectBox != null && attributes.selectBox.length > 0) {
                $.each(attributes.selectBox, function (key, value) {
                    if (value.selected == undefined || value.selected == "" || value.selected == null) {
                        values.push("<option id='" + value.dictionaryId + "' >" + value.dictionaryName + "</option>")
                    } else {
                        if (value.selected) {
                            values.push("<option id='" + value.dictionaryId + "'  selected ='selected'>" + value.dictionaryName + "</option>")
                        } else {
                            values.push("<option id='" + value.dictionaryId + "' >" + value.dictionaryName + "</option>")
                        }
                    }
                })
            }
            if (auditFlag) {
                html = "<tr>" +
                    "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                    "<td data-name='select'><select data-id='" + goodsAttributeInfo.id + "'  style='min-width: 180px;' disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" + values.join() + "</select></td>" +
                    "</tr>";
            } else {
                html = "<tr>" +
                    "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                    "<td data-name='select'><select data-id='" + goodsAttributeInfo.id + "'   style='min-width: 180px;' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" + values.join() + "</select></td>" +
                    "</tr>";
            }
            break;
        case 2 :
            auditEditorDetails = attributes.inputBox;
            auditEdiotrFlag = auditFlag ? true : !attributes.disabled;
            html = "<tr>" +
                "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                "<td data-name='div'>" +
                "<div data-id='" + goodsAttributeInfo.id + "'  disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" +
                "<script id='container' name='content' type='text/plain'></script>" +
                "</div>" +
                "</td>" +
                "</tr>";
            break;
        case 8 :
            html = " <tr>" +
                "<td  style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                "<td data-name='input'><input data-id='" + goodsAttributeInfo.id + "'  style='min-width: 180px;' disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "' type='text' value='单瓶 ' disabled ='disabled '/></td>" +
                "</tr>";
            break;
        case 9 :
            var htmls = "<tr>" +
                "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                "<td data-name='image'>" +
                "<div data-id='" + goodsAttributeInfo.id + "'  disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" +
                "<div id='listDetailImage' ><ul style='padding: 10px 10px'>" +
                "<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                "<img style=' z-index: 0;width: 100px;height: 100px;' src='Content/themes/smart/img/loadImg.png' alt=''>" +
                "<input type='file' name='Upload' value='' accept='image/jpg,image/jpeg,image/png' class='fileQj' style=' z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;'/> </li>" +
                "</ul></div>"
            "</div>" +
            "</td>" +
            "</tr>";
            if (attributes.imageBox == undefined || attributes.imageBox == "" || attributes.imageBox == null) {
                html = htmls;
            } else {
                var values = new Array();
                if (auditFlag) {
                    if (attributes.disabled) {
                        $.each(attributes.imageBox, function (key, value) {
                            var newHtml = "<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                                "<img style=' z-index: 0;width: 100px;height: 100px;' src='" + value.imageUrl + "' alt=''></li>"
                            values.push(newHtml);
                        });
                        html = "<tr>" +
                            "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                            "<td data-name='image'>" +
                            "<div data-id='" + goodsAttributeInfo.id + "'  disabled='" + attributes.disabled + "'data-name ='" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" +
                            "<div id='listDetailImage'>" +
                            "<ul style='padding: 10px 10px'>" + values.join("") + "</ul></div>"
                        "</div>" +
                        "</td>" +
                        "</tr>";
                    } else {
                        $.each(attributes.imageBox, function (key, value) {
                            var newHtml = "<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                                "<img style=' z-index: 0;width: 100px;height: 100px;' src='" + value.imageUrl + "' alt=''>" +
                                "<span class='imgDelete' style=' z-index:999;position: absolute;width: 20px;border-radius:50%;right: -10px;top: -10px;height: 20px;line-height: 20px;text-align: center;line-height: 20px;background-color: red;color: #ffffff'>X</span>" +
                                "<input type='file' name='Upload' value='' accept='image/jpg,image/jpeg,image/png' class='fileQj' style=' z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;'/> </li>"
                            values.push(newHtml);
                        });
                        if (values.length < 10) {
                            values.push("<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                                "<img style=' z-index: 0;width: 100px;height: 100px;' src='Content/themes/smart/img/loadImg.png' alt=''>" +
                                "<input type='file' name='Upload' value='' accept='image/jpg,image/jpeg,image/png' class='fileQj' style=' z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;'/> </li>");
                        }
                        html = "<tr>" +
                            "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                            "<td data-name='image'>" +
                            "<div data-id='" + goodsAttributeInfo.id + "'  disabled='" + attributes.disabled + "'data-name ='" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" +
                            "<div id='listDetailImage'>" +
                            "<ul style='padding: 10px 10px'>" + values.join("") + "</ul></div>"
                        "</div>" +
                        "</td>" +
                        "</tr>";
                    }
                } else {
                    $.each(attributes.imageBox, function (key, value) {
                        var newHtml = "<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                            "<img style=' z-index: 0;width: 100px;height: 100px;' src='" + value.imageUrl + "' alt=''>" +
                            "<span class='imgDelete' style=' z-index:999;position: absolute;width: 20px;border-radius:50%;right: -10px;top: -10px;height: 20px;line-height: 20px;text-align: center;line-height: 20px;background-color: red;color: #ffffff'>X</span>" +
                            "<input type='file' name='Upload' value='' accept='image/jpg,image/jpeg,image/png' class='fileQj' style=' z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;'/> </li>"
                        values.push(newHtml);
                    });
                    if (values.length < 10) {
                        values.push("<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                            "<img style=' z-index: 0;width: 100px;height: 100px;' src='Content/themes/smart/img/loadImg.png' alt=''>" +
                            "<input type='file' name='Upload' value='' accept='image/jpg,image/jpeg,image/png' class='fileQj' style=' z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;'/> </li>");
                    }
                    html = "<tr>" +
                        "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                        "<td data-name='image'>" +
                        "<div data-id='" + goodsAttributeInfo.id + "'  disabled='" + attributes.disabled + "'data-name ='" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" +
                        "<div id='listDetailImage'>" +
                        "<ul style='padding: 10px 10px'>" + values.join("") + "</ul></div>"
                    "</div>" +
                    "</td>" +
                    "</tr>";
                }
            }
            break;
        default:
            break;
    }
    attribute.push(html);
}


function imageDetailClick() {
    /*图片上传*/
    $("#listDetailImage").on("change", "li", function () {
        var that = this;
        var maxsize = 2 * 1024 * 1024;//2M
        var img = event.target.files[0];
        if (!img) return false;
        if (!img.type.indexOf('image') == 0 && img.type && /\.(?:jpg|png|gif)$/.test(img.name)) return false
        if (img.size > maxsize) {
            etao
            simpleNotify('图片上传最大不能超过2M');
            return false;
        } else {
            var reader = new FileReader()
            reader.readAsDataURL(img);
            reader.onload = function (e) {
                $.ajax({
                    url: $("#baseUrl").attr("data-img-url"),
                    type: 'POST',
                    data: {img: e.target.result, folfer: "mobile"},
                    success: function (data) {
                        if (data.IsSucceed == true) {
                            $(that).find("img").attr("src", data.Data);
                            $(that).append("<span class='imgDelete' style=' z-index:999;position: absolute;width: 20px;border-radius:50%;right: -10px;top: -10px;height: 20px;line-height: 20px;text-align: center;line-height: 20px;background-color: red;color: #ffffff'>X</span>");
                            if ($("#listImage li").length < 10) {
                                $(that).parent().append("<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                                    "<img style=' z-index: 0;width: 100px;height: 100px;' src='Content/themes/smart/img/loadImg.png' alt=''>" +
                                    "<input type='file' name='Upload' value='' accept='image/jpg,image/jpeg,image/png' class='fileQj' style=' z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;'/> </li>");
                            }
                        }
                    },
                    error: function (data) {
                        console.log(data.message)
                    }
                })
            }
        }
    })
    /*图片删除*/
    $("#listDetailImage").on("click", ".imgDelete", function (e) {
        e.stopPropagation();
        if ($("#listImage li:last").find("img").attr("src") == 'Content/themes/smart/img/loadImg.png') {
            $(this).parent().remove();
            $(this).remove();
        } else {
            $(this).parent().remove();
            $(this).remove();
            if ($("#listImage li").length < 10) {
                $("#listImage li:last").after("<li style='cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative'>" +
                    "<img style=' z-index: 0;width: 100px;height: 100px;' src='Content/themes/smart/img/loadImg.png' alt=''>" +
                    "<input type='file' name='Upload' value='' accept='image/jpg,image/jpeg,image/png' class='fileQj' style=' z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;'/> </li>");
            }
        }
        return false;
    })

}

function saveAuditGoods(id) {
    $("#save_detail_vendor_goods").unbind("click");
    $("#save_detail_vendor_goods").bind("click", id, saveDetailVendorGoodsClick);
    $("#reject_audit_goods").unbind("click");
    $("#reject_audit_goods").bind("click", id, rejectAuditGoodsClick);
}

var rejectAuditGoodsClick = function (event) {
    var id = event.data;
    CommonBase.showLoading();
    var param = {
        auditGoodsId: id
    }
    $.post(dataUrl + "updateAuditGoodsId", param, function (data) {
        if (data.success == false) {
            simpleNotify(data.message);
            CommonBase.hideLoading();
            return false;
        } else {
            simpleNotify(data.message);
            $('#audit_goods_detail').modal('hide');
            listAuditGoods();
            CommonBase.hideLoading();
        }
    });
}
var saveDetailVendorGoodsClick = function (event) {
    var id = event.data;
    CommonBase.showLoading();
    var attributeData = ""//属性值
    var shapeCode = "";//条形码
    var shapeCodeId = "";//条形码id
    var pinpaiId = $("#audit_pinpai_name").find("option:selected").attr("id");//类目id
    var attribute = new Object();
    var attributeInput = $("#table_audit_goods_detail td[data-name='input'] input");
    var flag = true;
    $.each(attributeInput, function (key, value) {
        var _keys = $(value).attr("data-value"),
            _value = $(value).val();
        var _name = $(value).attr("data-name");
        if (_value == null || _value == "" || _value.length < 1) {
            simpleNotify(_name + "为空！");
            flag = false;
            CommonBase.hideLoading();
            return false;
        }
        debugger
        if (_name == "条形码") {

            shapeCode = _value;
            shapeCodeId = $(value).attr("data-id");
        }
        var _attribute = new Object();
        _attribute.goodsAttributeInfo = _keys;
        _attribute.attribute = _value;
        attribute[_name] = JSON.stringify(_attribute);
    });
    if (flag) {
        var attributeSelect = $("#table_audit_goods_detail td[data-name='select'] select");
        $.each(attributeSelect, function (key, value) {
            var _keys = $(value).attr("data-value"),
                _value = $(value).find("option:selected").attr("id"),
                _valusse = $(value).find("option:selected").val(),
                _name = $(value).attr("data-name");
            var selectAttribute = new Object();
            selectAttribute.id = _value;
            var _attribute = new Object();
            _attribute.goodsAttributeInfo = _keys;
            _attribute.attribute = JSON.stringify(selectAttribute);
            attribute[_name] = JSON.stringify(_attribute);
        });
    }
    if (flag) {
        var attributeImage = $("#table_audit_goods_detail td[data-name='image']>div");
        $.each(attributeImage, function (key, value) {
            var _keys = $(value).attr("data-value"),
                _name = $(value).attr("data-name");
            var images = $(value).find("img");
            if ($(images[0]).attr("src") == 'Content/themes/smart/img/loadImg.png') {
                simpleNotify(_name + '至少上传一张');
                flag = false;
                CommonBase.hideLoading();
                return false;
            }
            var listImage = new Array();
            $.each(images, function (_key, _value) {
                var _image = $(_value).attr("src");
                if (_image != 'Content/themes/smart/img/loadImg.png') {
                    listImage.push(_image);
                }
            });
            var _attribute = new Object();
            _attribute.goodsAttributeInfo = _keys;
            _attribute.attribute = JSON.stringify(listImage);
            attribute[_name] = JSON.stringify(_attribute);
        });
    }
    if (flag) {
        var attributeDiv = $("#table_audit_goods_detail td[data-name='div']>div");
        $.each(attributeDiv, function (key, value) {
            var _keys = $(value).attr("data-value"),
                _name = $(value).attr("data-name");
            var _div = ediotr.getContent();
            if (_div == null || _div == "") {
                simpleNotify(_name + '简介不能为空');
                flag = false;
                CommonBase.hideLoading();
                return false;
            }
            var _attribute = new Object();
            _attribute.goodsAttributeInfo = _keys;
            _attribute.attribute = _div;
            attribute[_name] = JSON.stringify(_attribute);
        });
    }
    if (flag) {
        var param = {
            auditGoodsId: id,
            attributeData: JSON.stringify(attribute),
            shapeCode: shapeCode,
            shapeCodeId: shapeCodeId,
            pinpaiId: pinpaiId
        }
        $.post(dataUrl + "updateAuditGoodsId", param, function (data) {
            if (data.success == false) {
                simpleNotify(data.message);
                CommonBase.hideLoading();
                return false;
            } else {
                simpleNotify(data.message);
                $('#audit_goods_detail').modal('hide');
                listAuditGoods();
                CommonBase.hideLoading();
            }
        });
    }
}


/************************  other ********************************************/
function isToday(time) {
    var _time = new Date(time).getTime();
    var _today = new Date(new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate()).getTime();
    return _time == _today;
}

function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin);//最后拼接时间
    return oTime;
};

function getMyDates(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oHour = oDate.getHours(),
        oMin = oDate.getMinutes(),
        oSen = oDate.getSeconds(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay);//最后拼接时间
    return oTime;
};

//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}

/*****当选按钮***/
function checkChild(obj) {
    $(".checkchild").prop("checked", false);
    $(obj).prop("checked", true);
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








