var dataUrl = $("#baseUrl").attr("data-url") + "/goodsVerify/";
var pageTable;
var editPageTable;
var orderObj = {
    timeOrderTag:{
        name:'timeOrderTag',
        value:0//提交时间排序 0：降序 1：升序
    },
    submitOrderTag:{
        name:'submitOrderTag',
        value:0//提交次数排序标志 0：降序 1：升序
    }
}
$(function () {
    pageSetUp();
    listGoods();
    //查看图片点击事件
    showBigImageList();
});	//页面初始化调用
//列表展示问题
function  listGoods() {
    pageTable = {
        dom: $('#goods_verify_list'),//table节点
        ajaxUrl: dataUrl+ "listVerify",//ajax请求地址
        httpMethod: 'POST',//接口的请求方式方法分为get请求和post请求
        aoColumns:["verifyBarCode","verifyCount","latestUpdateTime","verifyGoodsName","verifyBarCode",],
        primaryKey: "verifyBarCode",//主键
        diyColumn :[ //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "verifyBarCode",//
                mRender: function (data, type, full) {
                    // debugger
                    return full.verifyBarCode;
                }
            },
            {
                aTargets: [2],//要显示的位置
                mData: "verifyCount",//
                mRender: function (data, type, full) {
                    return full.verifyCount;
                },
            },
            {
                aTargets: [3],//要显示的位置
                mData: "latestUpdateTime",//
                mRender: function (data, type, full) {
                    return full.latestUpdateTime;
                }
            },
            {
                aTargets: [4],//要显示的位置
                mData: "verifyGoodsName",//
                mRender: function (data, type, full) {
                    return full.verifyGoodsName;
                },
            },
            {
                aTargets: [5],//要显示的位置
                mData: "verifyBarCode",//
                mRender: function (data, type, full) {
                    var barCode = full.verifyBarCode;
                    return '<a class="tableBtn hidden-mobile" href="javascript:void(0)" onclick="updateGoodsVerify('+"\'"+barCode+'\')"><img class="hidden-md hidden-sm hidden-xs icon_img" src="Content/Home/image/xiugai.png" />编辑</a>'
                },
            }
        ],
        ajxaParam: function() {
            return{
                timeOrderTag: orderObj.timeOrderTag.value,
                submitOrderTag:orderObj.submitOrderTag.value
            }
        },
    };
    window.initPageTab(pageTable);

}

//编辑
function  updateGoodsVerify(obj) {
    //将图片信息显示在图片预览测
    $(".preImageListContents").empty();
    $("#update_goods_verify_modal").text("待审核列表");
    $("#update_goods_verify #verifyBarCode").text(obj);
    $("#update_goods_verify #no_by_goods_verify").attr("data-code",obj);
    $('#update_goods_verify').modal();
    CommonBase.showLoading();
    editPageTable = {
        dom: $('#goods_verify_vendor_list'),//table节点
        ajaxUrl: dataUrl+ "listVendorVerify",//ajax请求地址
        httpMethod: 'POST',//接口的请求方式方法分为get请求和post请求
        //aoColumns:["vendorName","verifyGoodsName","submitTime","verifySalePrice","verifyGoodsState",],
        aoColumns:["vendorName","verifyGoodsName","submitTime","verifySalePrice","verifyGoodsState",{name:"verifyGoodsImageList",sClass:"hidden verifyGoodsImageList"}],
        primaryKey: "verifyBarCode",//主键
        diyColumn :[ //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "vendorName",//
                mRender: function (data, type, full) {
                    // debugger
                    return full.vendorName;
                }
            },
            {
                aTargets: [2],//要显示的位置
                mData: "verifyGoodsName",//
                mRender: function (data, type, full) {
                    return full.verifyGoodsName;
                },
            },
            {
                aTargets: [3],//要显示的位置
                mData: "submitTime",//
                mRender: function (data, type, full) {
                    return full.submitTime;
                }
            },
            {
                aTargets: [4],//要显示的位置
                mData: "verifySalePrice",//
                mRender: function (data, type, full) {
                    return full.verifySalePrice;
                },
            },
            {
                aTargets: [5],//要显示的位置
                mData: "verifyGoodsState",//
                mRender: function (data, type, full) {
                    var temporaryUp="<span class='color_red'>暂不上架 </span>";
                    var up = "<span class='color_blue'>上架 </span>";
                    return full.verifyGoodsState=='0'?temporaryUp:(full.verifyGoodsState=='1'?up:'');
                },
            },
            {
                aTargets: [6],//要显示的位置
                mData: "verifyGoodsImageList",//
                mRender: function (data, type, full) {
                    return JSON.stringify(full.verifyPhotoList);
                },
            },
        ],
        drawCallback:function (oSettings) {
            var _this = "#goods_verify_vendor_list tbody tr:first-of-type";
            $(_this).addClass("selectActive");
            var imgList = $(_this).find(".verifyGoodsImageList").text();
            //初始化填充图像数据
            showBigImageListElement(imgList);

        },
        ajxaParam: function() {
            return {
                verifyBarCode: obj
            };
        },
    };
    window.initPageTab(editPageTable);
    CommonBase.hideLoading();


}
//点击查看大图
function showBigImageList() {
    $("#goods_verify_vendor_list tbody tr").live('click',function () {
        //选中状态添加深色
        $(this).addClass('selectActive').siblings().removeClass('selectActive');
        //获取隐藏图片的地址数据
        var imgList = $(this).find(".verifyGoodsImageList").text();
        //填充图像数据
        showBigImageListElement(imgList);

    });
}
//填充图像数据信息
function showBigImageListElement(imgList) {
    //将图片信息显示在图片预览测
    $(".preImageListContents").empty();
    if(imgList){
        imgList=JSON.parse(imgList)
        var imgStr = "";
        for(val of imgList){
            var img = '<img src="'+val+'" class="preImageListContentsItem" alt="商家图片预览"></img>';
            imgStr+=img;
        }
        $(".preImageListContents").append(imgStr);
    }else{
        var str = '<p class="noData">该商品暂未上传图片数据</p>';
        $(".preImageListContents").append(str)
    }
}
//审核不通过
function noByGoodsVerify() {
    var code =  $("#update_goods_verify #no_by_goods_verify").attr("data-code");
    $.confirm({
        msg: "审核不通过？",
        confirm: function () {
            CommonBase.showLoading();
            $.ajax({
                url: dataUrl + "verifyRefuse",
                type: "POST",
                data: {verifyBarCode: code,verifyRefuseCode:0},
                success: function (data) {
                    if (data.success == true) {
                        simpleNotify("成功！");
                        listGoods();
                        $('#update_goods_verify').modal('hide');
                        CommonBase.hideLoading();
                    }else{
                        simpleNotify("失败！");
                        CommonBase.hideLoading();
                    }
                }
            })
        },
        cancel: function () {
            return false;
        }
    });
}

//排序
function goodsVerifyBySort(type,obj){
    $("#goods_verify_list tr th").removeClass('hasSortAsc');
    $("#goods_verify_list tr th").removeClass('hasSortDesc');
    var val = '';
    if(type==orderObj.timeOrderTag.name){
        //按照更新时间进行排序
            val = orderObj.timeOrderTag.value;
            orderObj.timeOrderTag.value = (val==0)?1:0;
    }else if(type==orderObj.submitOrderTag.name){
        //按照提交数目进行排序
         val = orderObj.submitOrderTag.value;
        orderObj.submitOrderTag.value = (val==0)?1:0;
    }
    $(obj).addClass(val==0?'hasSortAsc':'hasSortDesc');
    listGoods();
}
//添加新商品
function addGoods() {
    //隐藏添加列表数据
    $('#update_goods_verify').modal('hide');
    $("#remoteModal").modal({backdrop: 'static', keyboard: false});
    //初始化商品数据
    formInit();
}