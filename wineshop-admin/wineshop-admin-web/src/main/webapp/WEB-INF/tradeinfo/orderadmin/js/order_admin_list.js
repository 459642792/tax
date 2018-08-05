/**
 * Created by Administrator on 2018/01/08.
 */
//页面初始化调用
var dataUrl = $('#dataUrl').val()
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
function showDetial(msg) {
    var info = msg,
        drawbackName = '系统退款',
        orderBusinessStateName = null;
    //是否存在退款
    if (info.drawbackType) {
        drawbackName = info.drawbackType == 1 ? '系统退款' : '客服手动退款';
        orderBusinessStateName = info.orderBusinessStateName + '(' + drawbackName + ')'
    } else {
        orderBusinessStateName = info.orderBusinessStateName;
    }
    $('#orderAdmin_orderNumber').text(info.orderNo)
    $('#orderAdmin_orderStatus').text(orderBusinessStateName)
    $('#orderAdmin_orderAddrr').text(info.tradeArea)
    $('#orderAdmin_orderSource').text(info.orderChannelName)
    $('#orderAdmin_orderMoney').text(info.payPrice)
    $('#orderAdmin_deliveryType').text(info.deliveryType == 1 ? '配送' : '自提')
    $('#orderAdmin_deliveryTime').text(info.deliveryTime)
    $('#orderAdmin_deliveryAddress').text(info.deliveryAddress)
    $('#orderAdmin_userId').text(info.userId)
    $('#orderAdmin_userPhone').text(info.userPhone)
    $('#orderAdmin_userNickName').text(info.userNickName)
    $('#orderAdmin_shopId').text(info.shopId)
    $('#orderAdmin_shopPhone').text(info.shopPhone)
    $('#orderAdmin_totalGoodsFee').text('¥' + info.totalGoodsFee)
    $('#orderAdmin_discounts').text('¥' + (info.totalGoodsFee - info.payPrice).toFixed(2))
    $('#orderAdmin_payPriceY').text('¥' + info.payPrice)
    $('#orderAdmin_createTime').text(info.createTime)
    $('#orderAdmin_remark').text(info.remark);
    $('#orderAdmin_shopName').text(info.shopName);
    $('#refundAuto_orderId').val(info.orderId);
    //退款金额
    if(info.drawbackFee){
        $('#orderAdmin_drawbackFee').text(info.drawbackFee);
        var chanl='';
        if(info.drawbackChannel==1){
            chanl='微信'
        }
        else if(info.drawbackChannel==2){
            chanl='支付宝'
        }
        else if(info.drawbackChannel==3){
            chanl='银行卡转账'
        }
        $('#orderAdmin_drawbackChannel').text(chanl+' '+info.drawbackReceiveId)
    }
    //是否退款
    if(info.showManualRefund==1){
     $('#refund_button').show();
     }else {
     $('#refund_button').hide();
     }
    if (info.totalGoodsFee - info.payPrice > 0) {
        $('#orderAdmin_discounts').parent().show()
    } else {
        $('#orderAdmin_discounts').parent().hide()
    }
    /*订单时间循环*/
    var timeListStr = '';
    var timeList=[];
    pushTime();
    function pushTime() {
        if (info.payTime) {
            var a={
                time:info.payTime,
                getTime:new Date(info.payTime).getTime(),
                name:'支付时间'
            };
            timeList.push(a)
        }
        if (info.promiseTime) {
            var a={
                time:info.promiseTime,
                getTime:new Date(info.promiseTime).getTime(),
                name:'接单时间'
            };
            timeList.push(a)
        }
        if (info.receiveTime) {
            var a={
                time:info.receiveTime,
                getTime:new Date(info.receiveTime).getTime(),
                name:'收货时间'
            };
            timeList.push(a)
        }
        if (info.commentTime) {
            var a={
                time:info.commentTime,
                getTime:new Date(info.commentTime).getTime(),
                name:'评价时间'
            };
            timeList.push(a)
        }
        if (info.cancelTime) {
            var a={
                time:info.cancelTime,
                getTime:new Date(info.cancelTime).getTime(),
                name:'取消时间'
            };
            timeList.push(a)
        }
        if (info.applyDrawbackTime) {
            var a={
                time:info.applyDrawbackTime,
                getTime:new Date(info.applyDrawbackTime).getTime(),
                name:'申请退款时间'
            };
            timeList.push(a)
        }
        if (info.drawbackTime) {
            var a={
                time:info.drawbackTime,
                getTime:new Date(info.drawbackTime).getTime(),
                name:'退款时间'
            };
            timeList.push(a)
        }
        var by = function(name){
            return function(o, p){
                var a, b;
                if (typeof o === "object" && typeof p === "object" && o && p) {
                    a = o[name];
                    b = p[name];
                    if (a === b) {
                        return 0;
                    }
                    if (typeof a === typeof b) {
                        return a < b ? -1 : 1;
                    }
                    return typeof a < typeof b ? -1 : 1;
                }
                else {
                    throw ("error");
                }
            }
        }
        timeList.sort(by('getTime'));
    }
    if(timeList.length>0){
        $.each(timeList,function (k,v) {
            timeListStr += '<div class="orderAdmin_detail_orderinfo_list"> ' +
                '<div class="orderAdmin_detail_orderinfo_listL"> ' +
                '<span>'+v.name+':</span> ' +
                '<span >' + v.time + '</span> ' +
                '</div></div>'
        })
    }
    $('.order_timeList').html(timeListStr);
    /*留言批注*/
    if (info.remark) {
        $('#remarkInfo').show()
    } else {
        $('#remarkInfo').hide()
    }
    /*退款原因*/
    if (info.drawbackDesc) {
        $('#drawbackDescInfo').show()
        $('#orderAdmin_drawbackDesc').text(info.drawbackDesc);
        if (info.drawbackRemark) {
            $('#orderAdmin_drawbackRemark').text(info.drawbackRemark).parent().parent().show()
        } else {
            $('#orderAdmin_drawbackRemark').parent().parent().hide()
        }
    } else {
        $('#drawbackDescInfo').hide()
    }
    /*商品循环*/
    var str = '';
    if (info.goodsVOList) {
        $.each(info.goodsVOList, function (k, v) {
            str += ' <div class="orderAdmin_goodsInfoList"> ' +
                '<div class="orderAdmin_goodsInfoList_img"> '
            if (v.goodsPhoto) {
                str += '<img src="' + v.goodsPhoto + '"> '
            } else {
                str += '<img src="./Content/Home/image/daiwei.jpg"> '
            }
            str += '</div> ' +
                '<div class="orderAdmin_goodsInfoList_property"> ' +
                '<div class="orderAdmin_goodsInfoList_title">' + v.goodsName + '</div> ' +
                '<div class="orderGoodsInfoAttribute"> ';
            if (v.attrVOList) {
                $.each(v.attrVOList, function (index, item) {
                    if (item.attrValueShowName != '') {
                        str += ' <span>' + item.attrValueShowName + '</span> '
                    }

                })
            }
            str += '</div> ' +
                '</div> ' +
                '<div class="orderAdmin_goodsInfoList_price"> ' +
                '<span>¥' + v.goodsPrice + '</span> ' +
                '<span>X' + v.goodsNum + '</span> ' +
                '</div> ' +
                '</div>'
        })
        $('.orderAdmin_goodsList').html(str)
    }
    $('#myModal').modal(
        {
            backdrop: 'static',
            keyboard: false,
            show: true
        }
    )
}

//显示退款
function showRefund() {
    console.log($('#refundAuto_orderId').val())
    $('#refundAuto').modal({
        backdrop: 'static',
        keyboard: false,
        show: true
    })
}
//退款
function autoRefund() {
    $('.errorRefundAuto').hide();
    var regF = /^\d+(\.\d{1,2})?$/,
        receiveId = $.trim($('#tuikuanId').val()),
        refundFee = $.trim($('#tuikuanFee').val()),
        refundChannel = $.trim($('#tuikuanChannel').val());
    if (receiveId == '') {
        $('.errorRefundAuto').eq(0).show();
        return false
    }
    if (!regF.test(refundFee)) {
        $('.errorRefundAuto').eq(1).show();
        return false;
    }
    $.ajax({
        type: "post",
        url: dataUrl + "/orderAdmin/manualRefund",
        data: {
            orderId: $('#refundAuto_orderId').val().toString(),
            refundChannel: refundChannel,
            refundFee: refundFee,
            receiveId: receiveId//退款账号
        },
        success: function (res) {
            if (res.success) {
                window.simpleNotify('退款成功');
                $('#refundAuto,#myModal').modal('hide');
                window.initPageTab(pageTable);
            }else {
                window.simpleNotify(res.message);
            }
        }
    })
}
