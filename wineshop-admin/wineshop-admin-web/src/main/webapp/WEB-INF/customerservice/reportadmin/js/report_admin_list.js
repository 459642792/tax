/**
 * Created by Administrator on 2018/01/09.
 */
/**
 * Created by Administrator on 2018/01/08.
 */
//页面初始化调用
var dataUrl = $('#baseUrl').attr('dataurl');
var pageTable = {
    dom: $('#orderInfo_list'),//table节点
    ajaxUrl: dataUrl + "/reportAdmin/listReport",//ajax请求地址
    httpMethod: 'post',//接口的请求方式方法分为get请求和post请求
    aoColumns: ["reportTime", "orderNo", "shopName", 'userId', "reportContext", "reportState", ""],//table要显示的列
    primaryKey: "orderNo",//主键
    diyColumn: [     //自定义列
        {
            aTargets: [6],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                var str = ""
                switch (full.reportState) {
                    case 0:
                        str = '未处理';
                        break;
                    case 1:
                        str = '处理中';
                        break;
                    case 2:
                        str = '已处理';
                        break;
                    default :
                        str = '未知'
                }
                return str;
            }
        },
        {
            aTargets: [7],//要显示的位置
            mData: "ID",//主键
            mRender: function (data, type, full) {//返回参数
                var text = '';
                switch (full.reportState) {
                    case 0:
                        text = '处理';
                        break;
                    case 1:
                        text = '查看';
                        break;
                    case 2:
                        text = '查看';
                        break;
                    default :
                        text = '未知'
                }
                var str = "<a style='text-align: center; cursor: pointer' onclick=checkDetail('" + full.reportId + "','" + full.reportState + "')  >" + text + "</a>"
                return str;
            }
        }
    ],
    ajxaParam: function () {
        return {
            reportContext: $("#content_tradingArea").val(),
            keyword: $("#content_name").val(),
            reportFrom: $("#content_beginTime").val(),
            reportTo: $("#content_endTime").val(),
            reportState: $('#content_tradingState').val() == 10 ? null : $('#content_tradingState').val()
        };
    },
}
$(function () {
    var month = (new Date().getMonth() + 1);
    month = month < 10 ? '0' + month : month;
    var minute = (new Date().getDate());
    minute = minute < 10 ? '0' + minute : minute;
    var dates = (new Date().getFullYear()) + "-" + month + "-" + minute;
    $("#content_beginTime").val($("#content_beginTime").val() == "" || $("#content_beginTime").val() == null ? dates : $("#content_beginTime").val()) ,
        $("#content_endTime").val($("#content_endTime").val() == "" || $("#content_endTime").val() == null ? dates : $("#content_endTime").val()) ,
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
var reportId=null;
function checkDetail(id, state) {
    reportId=id;
    $.ajax({
        url: dataUrl + "/reportAdmin/getReportDetail",
        type: 'POST',
        data: {reportId: id},
        success: function (res) {
            if (res.success) {
                showDetial(res.data)
                $('#myModal').modal(
                    {
                        backdrop: 'static',
                        keyboard: false,
                        show: true
                    }
                )
            }
        }
    });

}
//搜索
$("#btnSearch").click(function () {
    if (pageTable) {
        pageTable.container.fnDraw();
    }
});

function showDetial(res) {
    var info = res;
    $('#orderAdmin_orderNumber').text(info.orderNo)
    $('#orderAdmin_orderStatus').text(info.payPrice)
    $('#orderAdmin_orderAddrr').text(info.shopName)
    $('#orderAdmin_orderSource').text(info.userId)
    $('.reportAdmin_listText').text(info.reportContext)
    $('.reportAdmin_servicereply').text(info.reply)
    $('.reportAdmin_serviceresult').text(info.result);
    var str='';
    if(info.reportPhotoList){
        $.each(info.reportPhotoList,function (k,v) {
            str+='<a target="_blank" href="'+v+'"><img src="'+v+'"></a>'
        });
    }
    $('.reportAdmin_listIAmge').html(str);
    if(info.reportState==0){
        $('#jg_text').hide();
        $('.reportAdmin_servicereply').parent().hide();
        $('#kfhf_textarea').show();
        $('#reportAdmin_button').text('开始处理').attr('dispose',0)
    }
    if(info.reportState==1){
        $('#jg_text,#kf_text').show();
        $('.reportAdmin_servicereply,#cljg_textarea').show();
        $('#kfhf_textarea').hide();
        $('.reportAdmin_serviceresult').parent().hide();
        $('#reportAdmin_button').text('处理完成').attr('dispose',1)
    }
    if(info.reportState==2){
        $('#jg_text,#kf_text').show();
        $('.reportAdmin_servicereply,.reportAdmin_serviceresult').parent().show();
        $('#reportAdmin_button').hide();
        $('#cljg_textarea,#kfhf_textarea').parent().hide();
    }
}
/*处理投诉举报*/
$('#reportAdmin_button').click(function () {
    var me=$(this);
    if(me.attr('dispose')==0){
        var kfhf_textarea=$.trim($('#kfhf_textarea').val())
        if(kfhf_textarea==''){
            $('.redColor').eq(0).text('请输入50字以内的客服回复');
            return false
        }
        submitReply();
    }
    if(me.attr('dispose')==1){
        var cljg_textarea=$.trim($('#cljg_textarea').val())
        if(cljg_textarea==''){
            $('.redColor').eq(1).text('请输入50字以内的处理结果');
            return false
        }
        submitResult();
    }
    $('.redColor').text('')
})
/*提交客服回复*/
function submitReply() {
    $.ajax({
        url: dataUrl + "/reportAdmin/submitReply",
        type: 'POST',
        data: {reportId: reportId,reply:$.trim($('#kfhf_textarea').val())
        },
        success: function (res) {
            if (res.success) {
                window.simpleNotify('操作成功','提示','success');
                $('#myModal').modal('hide');
                window.initPageTab(pageTable);
            }else {
                window.simpleNotify(res.message);
            }
        },
        error:function (res) {
            window.simpleNotify(res.message);
        }
    });
}
/*提交处理结果*/
function submitResult() {
    $.ajax({
        url: dataUrl + "/reportAdmin/submitResult",
        type: 'POST',
        data: {reportId: reportId,result:$.trim($('#cljg_textarea').val())
        },
        success: function (res) {
            if (res.success) {
                window.simpleNotify('操作成功','提示','success');
                $('#myModal').modal('hide');
                window.initPageTab(pageTable);
            }else {
                window.simpleNotify(res.message);
            }
        },
        error:function (res) {
            window.simpleNotify(res.message);
        }
    });
}