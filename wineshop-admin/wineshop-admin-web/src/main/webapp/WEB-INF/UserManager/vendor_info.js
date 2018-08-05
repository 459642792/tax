//店铺商品详情相关
var vendorEdiotr = "";
var vendorEditorDetails = "";
var vendorFlag = false;//不可保存
var vendorEdiotrFlag = false;

var pageTable;


function vendorList() {
    pageTable = {
        dom: $('#relevance_vendor_infoesTable'),//table节点
        ajaxUrl: $("#baseUrl").attr("data-url") + "/goods/listByUPModelGoods",//ajax请求地址
        httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["id", "name", "pinpaiName", "shopTypeName", "origin", "pack", "price", "createDate"],//table要显示的列
        primaryKey: "id",//主键
        autoWidth: false,
        diyColumn: [     //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "name",//主键
                mRender: function (data, type, full, obj) {
                    return full.name == undefined ? "" : full.name;
                }
            },
            {
                aTargets: [2],//要显示的位置
                mData: "pinpaiName",//主键
                mRender: function (data, type, full, obj) {
                    return full.pinpaiName == undefined ? "" : full.pinpaiName;
                }
            },
            {
                aTargets: [3],//要显示的位置
                mData: "shopTypeName",//主键
                mRender: function (data, type, full, obj) {
                    return full.shopTypeName == undefined ? "" : full.shopTypeName;
                }
            }
            ,
            {
                aTargets: [4],//要显示的位置
                mData: "origin",//主键
                mRender: function (data, type, full, obj) {
                    return full.origin == undefined ? "" : full.origin;
                }
            }
            ,
            {
                aTargets: [5],//要显示的位置
                mData: "origin",//主键
                mRender: function (data, type, full, obj) {
                    return full.pack == undefined ? "" : full.pack;
                }
            }
            ,
            {
                aTargets: [6],//要显示的位置
                mData: "origin",//主键
                mRender: function (data, type, full, obj) {
                    return full.price == undefined ? "" : full.price + "元";
                }
            }
            ,
            {
                aTargets: [7],//要显示的位置
                mData: "createDate",//主键
                mRender: function (data, type, full, obj) {
                    return full.createDate == undefined ? "" : getMyDate(full.createDate);
                }
            }
            ,
            {
                aTargets: [8],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full, obj) {
                    return " <input type='button' class='btn btn-primary' onclick='vendorGoodsDetail(" + full.id + ")' value='详情'/>"
                }
            }
        ],
        ajxaParam: function () {
            return {
                vendorId: $("#baseUrl").attr("data-id")
            };
        }
    }
    $("#myTab li").removeClass("active");
    $("#myTab li:first").addClass("in active");
    $("#myTabContent div").removeClass("active");
    $("#myTabContent div:first").addClass("in active");
    window.initPageTab(pageTable);
    CommonBase.hideLoading();
}


function vendorGoodsDetail(goodsId) {
    var detailsUrl = $("#baseUrl").attr("data-url") + "/goods/getByGoodId";
    $.get(detailsUrl, {goodsId: parseInt(goodsId)}, function (data) {
        // $("#vendor_goods_detail_modal").text("商品详情");
        if (data.success == false) {
            simpleNotify(data.message);
            CommonBase.hideLoading();
            return false;
        } else {
            $('#vendor_goods_detail').modal();
            var pinPais = data.data.pinpai;
            $("#save_vendor_pinpai_name").val(pinPais);
            vendorEdiotr = "";
            vendorEditorDetails = "";
            $("#save_table_vendor_goods_detail").nextAll().remove();
            UE.delEditor('vedorContainer');
            var _attribute = data.data.attribute;
            if (_attribute != undefined && _attribute != null) {
                var attribute = new Array;
                $("#save_table_vendor_goods_detail").next().nextAll().remove();
                $.each(_attribute, function (key, value) {
                    processAttributeVendor(value.attribute, value.goodsAttributeInfo, attribute);
                });
                $("#save_table_vendor_goods_detail").after(attribute.join());
                vendorEdiotr = UE.getEditor('vedorContainer');
                vendorEdiotr.ready(function () {
                    //设置编辑器的内容
                    vendorEdiotr.setContent(vendorEditorDetails);
                    if (!vendorEdiotrFlag) {
                        vendorEdiotr.setDisabled();
                    }

                });
                $("#table_vendor_goods_detail").nextAll().remove();
                if (vendorFlag) {
                    imageDetailClick();
                    var detailHtml = "<div class='modal-footer' style='margin-bottom: 50px;'>" +
                        "<button type='button' class='btn btn-default' id='down_goods' style='width: 50px;height: 32px;'>下架</button>" +
                        "<button type='button' class='btn btn-default' id='cancel_goods' style='width: 50px;height: 32px;'>取消</button>" +
                        "<button type='button' class='btn btn-primary' id='save_detail_vendor_goods' style='width: 50px;height: 32px;'>保存</button> </div>";
                    $("#table_vendor_goods_detail").after(detailHtml);
                    updateVendorModelGoods(goodsId);
                } else {
                    $("#listDetailImage").children().change = null;
                    var detailHtml = "<div class='modal-footer' style='margin-bottom: 50px;'>" +
                        "<button type='button' class='btn btn-default' id='down_goods' style='width: 50px;height: 32px;'>下架</button>" +
                        "<button type='button' class='btn btn-default' id='cancel_goods' style='width: 50px;height: 32px;'>取消</button></div>";
                    $("#table_vendor_goods_detail").after(detailHtml);
                }
                downGoods(goodsId);
            }
        }
    });
}


/*** 店铺商品 修改 根据属性列表加工属性****/
function processAttributeVendor(attributes, goodsAttributeInfo, attribute) {
    var html = "";
    switch (goodsAttributeInfo.goodsAttributeEnum) {
        case 0 :
            if (goodsAttributeInfo.goodsAttributeName == "价格") {
                html = " <tr>" +
                "<td  style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                "<td data-name='input'><input type='text'  data-id='" + goodsAttributeInfo.id + "' disabled='true' data-name = '" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "' value='" + attributes.inputBox == null ? "" : attributes.inputBox + "' /></td>" +
                    "</tr>";
            } else {
                html = " <tr>" +
                    "<td  style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                    "<td data-name='input'><input type='text' data-id='" + goodsAttributeInfo.id + "'  disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "'  data-value='" + JSON.stringify(goodsAttributeInfo) + "' value='" + attributes.inputBox + "' /></td>" +
                    "</tr>";
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
            html = "<tr>" +
                "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                "<td data-name='select'><select data-id='" + goodsAttributeInfo.id + "' disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" + values.join() + "</select></td>" +
                "</tr>";
            break;
        case 2 :
            vendorEditorDetails = attributes.inputBox;
            vendorEdiotrFlag = attributes.disabled ? false : true;
            html = "<tr>" +
                "<td style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                "<td data-name='div'>" +
                "<div data-id='" + goodsAttributeInfo.id + "'  disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "'>" +
                "<script id='vedorContainer' name='content' type='text/plain'></script>" +
                "</div>" +
                "</td>" +
                "</tr>";
            break;
        case 8 :
            html = " <tr>" +
                "<td  style='width:20%;text-align: center'>" + goodsAttributeInfo.goodsAttributeName + "</td>" +
                "<td data-name='input'><input data-id='" + goodsAttributeInfo.id + "' disabled='" + attributes.disabled + "' data-name = '" + goodsAttributeInfo.goodsAttributeName + "' data-value='" + JSON.stringify(goodsAttributeInfo) + "' type='text' value='单瓶 ' disabled ='disabled '/></td>" +
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
            }
            break;
        default:
            break;
    }
    attribute.push(html);
    if (goodsAttributeInfo.goodsAttributeName != "价格") {
        vendorFlag = attributes.disabled && !vendorFlag ? false : true;
    }
}

/**店铺商品图片 相关**/
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

function updateVendorModelGoods(goodsId) {
    $("#save_detail_vendor_goods").click(function () {
        CommonBase.showLoading();
        var attributeData = ""//属性值
        var attribute = new Object();
        var attributeInput = $("#table_vendor_goods_detail td[data-name='input'] input");
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
            var attributeSelect = $("#table_vendor_goods_detail td[data-name='select'] select");
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
            var attributeImage = $("#table_vendor_goods_detail td[data-name='image']>div");
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
            var attributeDiv = $("#table_vendor_goods_detail td[data-name='div']>div");
            $.each(attributeDiv, function (key, value) {
                var _keys = $(value).attr("data-value"),
                    _name = $(value).attr("data-name");
                var _div = vendorEdiotr.getContent();
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
                goodsId: goodsId,
                attributeData: JSON.stringify(attribute)
            }
            $.post($("#baseUrl").attr("data-url") + "/goods/updateGoods", param, function (data) {
                if (data.success == false) {
                    simpleNotify(data.message);
                    CommonBase.hideLoading();
                    return false;
                } else {
                    simpleNotify(data.message);
                    $('#vendor_goods_detail').modal('hide');
                    vendorList();
                    CommonBase.hideLoading();
                }
            });
        }
    });
}

/*************   下架  ************/
function downGoods(id) {
    $("#down_goods").click(function () {
        $("#down_model_goodsl_password_label").text("输入密码");
        $('#down_model_goods_password_model').modal();
        $("#passwordModelGoodsSubmit").click(function () {
            CommonBase.showLoading();
            var password = $("#down_model_goods_password_model #down_model_goods_password").val();
            if (password == undefined || password == null || password.length == 0) {
                simpleNotify("请输入密码");
                CommonBase.hideLoading();
                return false;
            }
            $.post($("#baseUrl").attr("data-url") + "/goods/downGoods", {
                goodsId: parseInt(id),
                password: password
            }, function (data) {
                if (data.success == false) {
                    simpleNotify(data.message);
                    CommonBase.hideLoading();
                    return false;
                } else {
                    simpleNotify(data.message);
                    vendorList();
                    $('#down_model_goods_password_model').modal('hide');
                    $('#vendor_goods_detail').modal('hide');
                    CommonBase.hideLoading();
                }
            });
        });
    });
    $("#cancel_goods").click(function () {
        $('#vendor_goods_detail').modal('hide');
    });

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