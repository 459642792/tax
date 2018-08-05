/**
 * Created by Administrator on 2018/01/15.
 */
var dataUrl = $('#baseUrl').attr('dataurl');
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
        minDate: new Date()
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
        minDate: '#F{$dp.$D(\'content_beginTime\')}'
    });
});	//页面初始化调用
$('#content_name').focus(function () {
    $('#ad_shops ul').html('')
    $('.ad_displayNon').eq(0).show();
    $('.ad_displayNon').eq(1).hide()
})
$('#adGoods_name').focus(function () {
    $('#ad_goods ul').html('')
    $('.ad_displayNon').eq(1).show()
    $('.ad_displayNon').eq(0).hide();
})
$('#content_beginTime,#content_endTime,#content_Index').focus(function () {
    $('.ad_displayNon').hide();
})
function upLoadChange(e) {
    var fileName = "fjjh_head_img/fjjh_admin_brandList_logo";
    window.upLoadOssImage(event, fileName, function (result) {
        //图片信息地址
        var url = window.ossImageUrl + result.name;
        $("#brandLogo").attr("src", url).show();
        $('.editAdList_imgAdd').hide();
    })
}
//获取活动列表
function getCatagory() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "get",
            url: dataUrl + "/promotion/catagory/list",
            success: function (res) {
                if (res.success) {
                    var str = '';
                    $('#listPromotionCatagoryId').val(res.data[0].promotionCatagoryId);
                    $('#ad_textRight').text('活动信息：' + res.data[0].name)
                    $.each(res.data, function (k, v) {
                        var time = v.updateTime ? v.updateTime : v.createTime,
                            nameInfo = {
                                promotionCatagoryId: v.promotionCatagoryId,
                                name: v.name,
                                summary:v.summary?v.summary:'',
                                img:v.banner?v.banner:'',
                            };
                        str += '<tr onclick=checkType(' + JSON.stringify(nameInfo) + ')>' +
                            '<td>' + v.name + '</td>' +
                            '<td>' + formateDateTime(time, 1) + '</td>' +
                            '<td>' + v.updateStaffName + '</td>' +
                            '<td>' + (v.model == 0 ? "小" : "大") + '</td>' +
                            '<td><a onclick=editCatagory(' + JSON.stringify(nameInfo) + ')>编辑</a></td>'
                    })
                    $('.links_content').html(str)
                    resolve(res)
                }
            }
        })
    });
}
//编辑活动
function editCatagory(msg, e) {
    window.event ? window.event.cancelBubble = true : e.stopPropagation();
    $('#editAdlist').modal({
        backdrop: 'static',
        keyboard: false,
        show: true
    });
    $('#promotionCatagoryId').val(msg.promotionCatagoryId);
    $('#editAdlist_title').text('【' + msg.name + '】文案图片编辑')
    $('#editAD_listText').val(msg.summary);
   if(msg.img!=''){
       $("#brandLogo").attr("src", msg.img).show();
       $('.editAdList_imgAdd').hide();
   }else {
       $("#brandLogo").attr("src", '').hide();
       $('.editAdList_imgAdd').show();
   }

    $('#editAdlist .errorText,#editAdlist .errorImg').hide();

}
//保存编辑活动
function editCatagorySAve() {
    var summary = $.trim($('#editAD_listText').val()),
        imgUrl = $('.editAdList_img img').attr('src'),
        promotionCatagoryId = $('#promotionCatagoryId').val();
    if (summary == '') {
        $('#editAdlist .errorText').show();
        return false
    }
    if (!imgUrl) {
        $('#editAdlist .errorImg').show();
        return false
    }
    summary = summary.replace(/\n/g, '_@').replace(/\s/g, '_#');
    $.ajax({
        type: "post",
        url: dataUrl + "/promotion/catagory/update",
        data: {
            promotionCatagoryId: promotionCatagoryId,
            summary: summary,
            banner: imgUrl
        },
        success: function (res) {
            if (res.success) {
                window.simpleNotify('操作成功', '提示', 'success');
                $('#editAdlist').modal('hide');
                getCatagory();
            } else {
                window.simpleNotify(res.message);
            }
        },
        error: function (res) {
            window.simpleNotify('更新活动失败，请稍后重试');
        }
    })
}
var contPromotion = {
    greater0: 0,
    greater5: 0
};//记录权重个数
function getWegit() {
    contPromotion = {
        greater0: 0,
        greater5: 0
    }
    var obj = {
        promotionCatagoryId: $('#listPromotionCatagoryId').val(),
        cityCode: $('#city').val(),
        status: $('#ad_state').val() == 2 ? null : $('#ad_state').val(),
    }
    $.ajax({
        type: "get",
        url: dataUrl + "/promotion/list",
        data: obj,
        success: function (res) {
            if (res.success) {
                var greater0 = 0, greater5 = 0
                if (res.data.length > 0) {
                    $.each(res.data, function (k, v) {
                        if (v.status == 1) {
                            if (v.weight == 0) {
                                greater0 = greater0 + 1
                            }
                            if (v.weight > 5) {
                                greater5 = greater5 + 1
                            }
                        }
                    })
                    contPromotion = {
                        greater0: greater0,
                        greater5: greater5
                    }

                }
                contPromotion = {
                    greater0: greater0,
                    greater5: greater5
                }
            }
        },
        error: function (res) {

            contPromotion = {
                greater0: 0,
                greater5: 0
            }
        }
    })

}
var pageTableTwo = {
    dom: $('#adshop_list'),//table节点
    ajaxUrl: dataUrl + "/promotion/list",//ajax请求地址
    httpMethod: 'get',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["vendorName", "goodsName", 'visits', "startTime", "endTime", 'status', 'weight'],//table要显示的列
    primaryKey: "vendorName",//主键
    diyColumn: [     //自定义列
        {
            aTargets: [4],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                var str = formateDateTime(full.startTime, 1)
                return str;
            }
        },
        {
            aTargets: [5],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                var str = formateDateTime(full.endTime, 1)
                return str;
            }
        },
        {
            aTargets: [6],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                var str = full.status == 0 ? '过期' : '正常';

                return str;
            }
        },
        {
            aTargets: [8],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                full.startTime = full.startTime.split(' ')[0]
                full.endTime = full.endTime.split(' ')[0]
                var str = "<a style='text-align: center; cursor: pointer;padding-right: 10px; ' onclick=deleteGoogs('" + full.promotionInfoId + "') >删除</a>" +
                    "<a style='text-align: center; cursor: pointer' onclick=editGoogs('" + JSON.stringify(full) + "') >编辑</a>"
                return str;
            }
        }
    ],
    ajxaParam: function () {
        return {
            promotionCatagoryId: $('#listPromotionCatagoryId').val(),
            cityCode: $('#city').val(),
            status: $('#ad_state').val() == 2 ? null : $('#ad_state').val(),

        };
    },
}
//获取参加活动的商品列表
function getPromotionList() {
    window.initPageTab(pageTableTwo);
}
//切换列表
function checkType(msg) {
    $('#ad_textRight').text('活动信息：' + msg.name)
    $('#listPromotionCatagoryId').val(msg.promotionCatagoryId);
    getPromotionList();
}

/*添加活动商品*/
$('#addGoods').click(function () {
    $('.ad_displayNon').hide();
    $('.editError').hide();
    $('#DetailCity').removeAttr('disabled');
    $('#DetailProvince').removeAttr('disabled');
    $('#content_name').removeAttr('disabled').val('');
    $('#adGoods_name').removeAttr('disabled').val('');
    $('#content_beginTime').val('');
    $('#content_endTime').val('');
    $('#content_Index').val('');
    $('#vendorId').val('');
    $('#vendorName').val('');
    $('#goodsId').val('');
    $('#goodsName').val('');
    $('#addActivity').modal({
            backdrop: 'static',
            keyboard: false,
            show: true
        }
    );
    getShops();
})
//获取地取下的商家
var shopListInfo = [];
function getShops() {
    shopListInfo = [];
    $.ajax({
        type: "get",
        url: dataUrl + "/api/vendor/getAreaVendors",
        data: {
            cityCode: $('#DetailCity').val()
        },
        success: function (res) {
            if (res.success) {
                if (res.data.length > 0) {
                    $.each(res.data, function (k, v) {
                        var a = {
                            text: v.name + '(' + v.id + ')',
                            shopId: v.id,
                            shopName: v.name
                        };
                        shopListInfo.push(a)
                    })
                }
            } else {
                window.simpleNotify(res.message);
            }
        },
        error: function (res) {

            window.simpleNotify('获取数据失败，请稍后重试');
        }
    })
}
$('#content_name').keyup(function () {
    $('#vendorId').val('');
    $('#vendorName').val('');
    var textValue = $.trim($('#content_name').val());
    $('#ad_shops ul').html('')
    if (shopListInfo.length == 0) {
        return
    }
    var str=''
    for (var i = 0; i < shopListInfo.length; i++) {
        if (shopListInfo[i].text.indexOf(textValue) > -1) {
             str += ' <li onclick=checkText(' + i + ')>' + shopListInfo[i].text + '</li>'
        }
    }
    $('#ad_shops ul').html(str)
})
//选中输入商家
function checkText(msg) {
    $('#content_name').val(shopListInfo[msg].text);
    $('.ad_displayNon').eq(0).hide();
    $('#vendorId').val(shopListInfo[msg].shopId);
    $('#vendorName').val(shopListInfo[msg].shopName);
    getInputShops(shopListInfo[msg].shopId)
}
var shopGoogs = [];//商家在售商品
function getInputShops(msg) {
    shopGoogs = []
    $.ajax({
        type: "get",
        url: dataUrl + "/promotion/getVendorGoods",
        data: {
            vendorId: msg
        },
        success: function (res) {
            if (res.success) {
                if (res.data.length > 0) {
                    $.each(res.data, function (k, v) {
                        var a = {
                            text: v.goodsName + '(' + v.goodsId + ')',
                            goodsId: v.goodsId,
                            goodsName: v.goodsName
                        };
                        shopGoogs.push(a)
                    })

                }
            } else {
                window.simpleNotify(res.message);
            }
        },
        error: function (res) {

            window.simpleNotify('获取数据失败，请稍后重试');
        }
    })
}
$('#adGoods_name').keyup(function () {
    $('#goodsId').val('');
    $('#goodsName').val('');
    var textValue = $.trim($('#adGoods_name').val());
    $('#ad_goods ul').html('')
    if (shopGoogs.length == 0) {
        return
    }
    var str=''
    for (var i = 0; i < shopGoogs.length; i++) {
        if (shopGoogs[i].text.indexOf(textValue) > -1) {
            str += ' <li onclick=checkinputText(' + i + ')>' + shopGoogs[i].text + '</li>'

        }
    }
    $('#ad_goods ul').html(str)
})
//点击空白处关闭商品和商家提示框
//选中输入商品
function checkinputText(msg) {
    $('#adGoods_name').val(shopGoogs[msg].text);
    $('#goodsId').val(shopGoogs[msg].goodsId);
    $('#goodsName').val(shopGoogs[msg].goodsName);
    $('.ad_displayNon').eq(1).hide()
}
//更新活动商品
function saveEdit() {
    var promotionCatagoryId = $('#listPromotionCatagoryId').val(),
        promotionInfoId=$('#promotionInfoId').val();
    var vendorId = $('#vendorId').val(),
        vendorName = $('#vendorName').val(),
        goodsId = $('#goodsId').val(),
        goodsName = $('#goodsName').val(),
        cityCode = $('#DetailCity').val(),
        startTime = $('#content_beginTime').val(),
        endTime = $('#content_endTime').val(),
        weight = $.trim($('#content_Index').val());


    $.ajax({
        type: "POST",
        url: dataUrl + "/promotion/update",
        data: {
            promotionInfoId:promotionInfoId,
            promotionCatagoryId:promotionCatagoryId,
            vendorId: vendorId,
            vendorName: vendorName,
            goodsId: goodsId,
            goodsName: goodsName,
            cityCode: cityCode,
            startTime: startTime+' 00:00:00',
            endTime: endTime+' 23:59:59',
            weight: weight
        },
        success: function (res) {
            if (res.success) {
                window.simpleNotify('编辑活动商品成功');
                $('#addActivity').modal('hide');
                getPromotionList();
            } else {
                window.simpleNotify(res.message);
            }
        },
        error: function (res) {
            window.simpleNotify('编辑活动商品失败，请稍后重试');
        }
    })

}
//新增活动商品
function saveAdd() {
    var promotionCatagoryId = $('#listPromotionCatagoryId').val();
    var vendorId = $('#vendorId').val(),
        vendorName = $('#vendorName').val(),
        goodsId = $('#goodsId').val(),
        goodsName = $('#goodsName').val(),
        cityCode = $('#DetailCity').val(),
        startTime = $('#content_beginTime').val(),
        endTime = $('#content_endTime').val(),
        weight = $.trim($('#content_Index').val());
    $.ajax({
        type: "POST",
        url: dataUrl + "/promotion/add",
        data: {
            promotionCatagoryId: promotionCatagoryId,
            vendorId: vendorId,
            vendorName: vendorName,
            goodsId: goodsId,
            goodsName: goodsName,
            cityCode: cityCode,
            startTime: startTime + ' 00:00:00',
            endTime: endTime + ' 23:59:59',
            weight: weight
        },
        success: function (res) {
            if (res.success) {
                window.simpleNotify('编辑活动商品成功');
                $('#addActivity').modal('hide');
                getPromotionList();
            } else {
                window.simpleNotify(res.message);
            }
        },
        error: function (res) {
            window.simpleNotify('编辑活动商品失败，请稍后重试');
        }
    })

}
//编辑商品
function editGoogs(msg) {
    $('.editError').hide();
    $('#ADsubmit').attr('addType', 1);
    var info = JSON.parse(msg);
    $('#addActivity').modal({
            backdrop: 'static',
            keyboard: false,
            show: true
        }
    );
    $('#promotionInfoId').val(info.promotionInfoId)
    $('#DetailCity').html($('#city').html()).attr('disabled', 'disabled').val($('#city').val())
    $('#DetailProvince').attr('disabled', 'disabled').val($('#Province').val());
    $('#content_name').val(info.vendorName).attr('disabled', 'disabled');
    $('#adGoods_name').val(info.goodsName).attr('disabled', 'disabled');
    $('#content_beginTime').val(formateDateTime(info.startTime, 1));
    $('#content_endTime').val(formateDateTime(info.endTime, 1));
    $('#content_Index').val(info.weight);
    $('#vendorId').val(info.vendorId);
    $('#vendorName').val(info.vendorName);
    $('#goodsId').val(info.goodsId);
    $('#goodsName').val(info.goodsName);

}
//提交保存活动商品
$('#ADsubmit').click(function () {
    var me = $(this);
    //编辑
    if (errorShow() == false) {
        return false
    }

    if (me.attr('addType')) {
        saveEdit()
    } else {
        //新增
        saveAdd()
    }
})
function errorShow() {
    var isError = null;
    var vendorId = $('#vendorId').val(),
        vendorName = $('#vendorName').val(),
        goodsId = $('#goodsId').val(),
        goodsName = $('#goodsName').val(),
        cityCode = $('#DetailCity').val(),
        startTime = $('#content_beginTime').val(),
        endTime = $('#content_endTime').val(),
        weight = $.trim($('#content_Index').val());
    if (vendorId == '') {
        isError = false;
        $('.vendorNameError').show();
        return false
    } else {
        isError = true;
        $('.vendorNameError').hide();
    }
    if (goodsId == '') {
        isError = false;
        $('.goodsNameError').show();
        return false
    } else {
        isError = true;
        $('.goodsNameError').hide();
    }
    if (startTime == '' || endTime == '') {
        isError = false;
        $('.goodsTimeError').show().text('请选择开始或结束时间');
        return false
    } else {
        var sTime = new Date().getTime(),
            eTimen = new Date().getTime();
        if (eTimen - sTime < 0) {
            isError = false;
            $('.goodsTimeError').show().text('开始时间不能大于结束时间');
            return false
        } else {
            isError = true;
            $('.goodsTimeError').hide();
        }
    }
    if (weight == 0) {
        if (contPromotion.greater0 >= 20) {
            isError = false;
            $('.goodsWeightError').show().text('权重为0的数据只能为20条');
            return false
        } else {
            isError = true;
            $('.goodsWeightError').hide();
        }

    } else {
        if (contPromotion.greater5 >= 5) {
            isError = false;
            $('.goodsWeightError').show().text('权重为6-10的数据只能为5条');
            return false
        } else {
            isError = true;
            $('.goodsWeightError').hide();
        }
    }
    return isError;
}


//删除活动商品
function deleteGoogs(msg) {
    $.ajax({
        type: "post",
        url: dataUrl + "/promotion/delete",
        data: {
            promotionInfoId: msg,
        },
        success: function (res) {
            if (res.success) {
                window.simpleNotify('删除活动商品成功', '提示', 'success');
                getPromotionList();
            } else {
                window.simpleNotify(res.message);
            }
        },
        error: function (res) {
            window.simpleNotify('删除活动商品失败，请稍后重试');
        }
    })
}
//添加活动商家城市code
function detailGetCity(msg) {
    $("#DetailCity").empty();
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "get",
            url: dataUrl + "/city/getCityByParent",
            data: {
                parent: msg ? msg : $('#DetailProvince').val()
            },
            success: function (result) {
                resolve();
                for (var i = 0; i < result.data.length; i++) {
                    $('#DetailCity').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                }
            }
        })
    })
}


//刷新
function refresh() {
    //getCatagory()
    getPromotionList()
}
function formateDateTime(dateTime, type) {
    var date = new Date(dateTime),
        year = date.getFullYear(),
        month = date.getMonth() + 1,
        monthDay = date.getDate(),
        hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours(),
        minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes(),
        second = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
    if (type) {
        var yyyy_mm_dd = year + '-' + (month.toString().length == 2 ? month : '0' + month) + '-' + (monthDay.toString().length == 2 ? monthDay : '0' + monthDay);
    } else {
        var yyyy_mm_dd = year + '.' + (month.toString().length == 2 ? month : '0' + month) + '.' + (monthDay.toString().length == 2 ? monthDay : '0' + monthDay);
    }
    return yyyy_mm_dd;
}
