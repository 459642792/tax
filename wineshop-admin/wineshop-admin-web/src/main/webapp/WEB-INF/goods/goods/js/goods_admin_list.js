var dataUrl = $("#baseUrl").attr("data-url") + "/goodsAdmin/";
var pageTable;
var vue;
//设置排序列表
var orderObj = {
    //价格排序
    suggestPriceTag:{
        name:'suggestPriceTag',
        value:''
    },
    //商品状态排序
    goodsStateTag:{
        name:'goodsStateTag',
        value:''
    }
}
$(function () {
    pageSetUp();
    getType();
    //品牌类型的切换
    $("#adminType input[name='adminType']") .live('change',function () {
        var checkBoxFlag = $(this).is(':checked');
        if(checkBoxFlag){
            $(this).attr("checked","false");
        }else{
            $(this).removeAttr("checked");
        }
        $("#adminType input[name='adminType']").not(this).removeAttr("checked");
        //类型ID
        var brandTypeID = $(this).attr('id');
        if(brandTypeID){
            listSearchAttr(brandTypeID);
        }
    })
    //包装的切换
    $("#packageAttrCode input[type='checkbox']").live('change',function () {
        var checkBoxFlag = $(this).is(':checked');
        if(checkBoxFlag){
            $(this).attr("checked","false");
        }else{
            $(this).removeAttr("checked");
        }
        $("#packageAttrCode input[type='checkbox']").not(this).removeAttr("checked");
    })
});	//页面初始化调用

//页面加载类型
function getType(){
    pageTable = null;
    CommonBase.showLoading();
    $.get(dataUrl + "listGoodsType",function (data) {
        $("#listTypeAttr").val("");
        if (data.success == false) {
            simpleNotify(data.message);
            CommonBase.hideLoading();
            return false;
        } else {
            $("#adminType").children().remove();
            $.each(data.data, function (key,val) {
                if (val.goodsTypeId != 1100){
                    $("#adminType").append("<label><input name='adminType' type='checkbox''  id="+val.goodsTypeId+" />"+val.goodsTypeName+" </label>&nbsp;&nbsp;");
                }
            });
            CommonBase.hideLoading();
        }
    });
    listGoods();
}
//搜索隐藏类型
function listSearchAttr(obj){
    $.post(dataUrl + "listSearchAttr",{goodsTypeId:obj},function (data) {
        $("#listTypeAttr").val("");
        if (data.success == false) {
            simpleNotify(data.message);
            CommonBase.hideLoading();
            return false;
        } else {
            $("#adminTypes").siblings().remove();
            $("#adminTypes").after("<div id='typeSearch' class='row' >");
            $.each(data.data, function (key,val) {
                if (val.attrType == 0){
                    var arr = new Array();
                    $.each(val.attrValVOList, function (key_key,val_val) {
                        arr.push("<label><input name='attr_"+val.attrCode+"' type='checkbox'' value='"+val_val.attrValueName+"'" +
                            " data-code="+val_val.attrValueCode+" />"+val_val.attrValueName+" </label>&nbsp;&nbsp;");
                    });
                    $("#typeSearch").prepend("<section class='col col-3'> " +
                        "<div class='brandType'>"+
                        "<label class='label title'>"+val.attrName+"</label>" +
                        "<label data-code='"+val.attrCode+"' id='packageAttrCode' > "+arr.join("")+"</label> " +
                        "</div></section>");
                }else if(val.attrType == 1){
                    $("#typeSearch").append("<section class='col col-3 alcoholAttrCode'>" +
                        "<label class='label title' id='alcoholAttrCode' data-code='"+val.attrCode+"'>"+val.attrName+"</label> " +
                        "<label class='input'>" +
                        "<input type='text'  id='alcoholValueNameFrom' placeholder='请输入开始"+val.attrName+"'> "+
                        "<input type='text' id='alcoholValueNameTo'  placeholder='请输入结束"+val.attrName+"'>"+
                        "</label> </section>");
                }
            });
            CommonBase.hideLoading();
        }
    });
}
//查询条件的设置
function getSearchData() {
    var searchData = {};
    //商品类别ID
    searchData.goodsType = '';
    var goodsType = $("#adminType input[name='adminType']:checked").attr("id");
    if(goodsType){
        searchData.goodsType=goodsType;
    }
    //商品指导价格
    searchData.suggestPriceFrom = '';
    searchData.suggestPriceTo='';
    var suggestPriceFrom = $("#suggestPriceFrom").val();
    var suggestPriceTo = suggestPriceTo=$("#suggestPriceTo").val();
    if(suggestPriceFrom && suggestPriceTo && (suggestPriceFrom<suggestPriceTo)){
        searchData.suggestPriceFrom=suggestPriceFrom;
        searchData.suggestPriceTo = suggestPriceTo;
    }
    //商品输入框
    searchData.searchKey = '';
    var searchKey = $("#search_value").val();
    if(searchKey){
        searchData.searchKey=searchKey;
    }
    //酒精度搜索
    searchData.alcoholAttrCode='';
    searchData.alcoholValueNameFrom='';
    searchData.alcoholValueNameTo='';
    //酒精度编码
    var alcoholAttrCode = $("#typeSearch #alcoholAttrCode").attr("data-code");
    //开始酒精度
    var alcoholValueNameFrom= $("#typeSearch #alcoholValueNameFrom").val();
    //结束酒精度
    var alcoholValueNameTo =$("#typeSearch #alcoholValueNameTo").val();
    //输入酒精度的时候，开始酒精度，结束酒精度，酒精度编码必须传入
    if(alcoholValueNameFrom && alcoholValueNameTo &&(alcoholValueNameFrom<alcoholValueNameTo)){
        searchData.alcoholAttrCode=alcoholAttrCode;
        searchData.alcoholValueNameFrom=alcoholValueNameTo;
        searchData.alcoholValueNameTo=alcoholValueNameTo;
    }
    //包装搜索时，包装code和value值都必须传入
    searchData.packageAttrCode = '';
    searchData.packageAttrValueCode = '';
    var packageAttrCode = $("#typeSearch #packageAttrCode").attr("data-code");
    var packageAttrValueCode = $("#typeSearch #packageAttrCode input[name^='attr_']:checked").attr("data-code");
    if(packageAttrCode && packageAttrValueCode){
        searchData.packageAttrCode = packageAttrCode;
        searchData.packageAttrValueCode = packageAttrValueCode;
    }
    //设置价格默认排序
    searchData.suggestPriceTag=orderObj.suggestPriceTag.value;//0:降序 1：升序
    //设计商品状态
    searchData.goodsStateTag=orderObj.goodsStateTag.value;//0:降序 1：升序
    return searchData;
}
//列表展示问题
function  listGoods() {
    var searchDataObj = getSearchData();
    pageTable = {
        dom: $('#goods_admin_list'),//table节点
        autoWidth:false,
        ajaxUrl: dataUrl+ "listGoods",//ajax请求地址
        httpMethod: 'POST',//接口的请求方式方法分为get请求和post请求
        aoColumns:["goodsName","mainBrandName","brandName","barCode","alcohol","content","packageType","goodsTypeName","suggestPrice","goodsState","goodsId",],
        primaryKey: "goodsId",//主键
        diyColumn :[ //自定义列
            {
                aTargets: [1],//要显示的位置
                mData: "goodsName",//
                mRender: function (data, type, full) {
                    // debugger
                    return full.goodsName;
                }
            },
            {
                aTargets: [2],//要显示的位置
                mData: "mainBrandName",//
                mRender: function (data, type, full) {
                    return full.mainBrandName;
                },
            },
            {
                aTargets: [3],//要显示的位置
                mData: "brandName",//
                mRender: function (data, type, full) {
                    return full.brandName;
                }
            },
            {
                aTargets: [4],//要显示的位置
                mData: "barCode",//
                mRender: function (data, type, full) {
                    return full.barCode;
                },
            },
            {
                aTargets: [5],//要显示的位置
                mData: "alcohol",//
                mRender: function (data, type, full) {
                    return full.alcohol;
                },
            },
            {
                aTargets: [6],//要显示的位置
                mData: "content",//
                mRender: function (data, type, full) {
                    return full.content;
                },
            },
            {
                aTargets: [7],//要显示的位置
                mData: "packageType",//
                mRender: function (data, type, full) {
                    return full.packageType;
                },
            },
            {
                aTargets: [8],//要显示的位置
                mData: "goodsTypeName",//
                mRender: function (data, type, full) {
                    return full.goodsTypeName;
                },
            },
            {
                aTargets: [9],//要显示的位置
                mData: "suggestPrice",//
                mRender: function (data, type, full) {
                    return full.suggestPrice;
                },
            },
            {
                aTargets: [10],//要显示的位置
                mData: "goodsState",//
                mRender: function (data, type, full) {
                    return full.goodsState=='0'?'下架中':(full.goodsState=='1'?'上架中':'未知');
                },
            },
            {
                aTargets: [11],//要显示的位置
                mData: "goodsId",//
                mRender: function (data, type, full) {
                    //上架按钮
                    var upBtn ="<a type='button' class='tableBtn hidden-mobile' onclick='upDownGoods("+full.goodsId+")'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/upup.png'/>上架 </a>";
                    //下架按钮
                    var downBtn ="<a button type='button' class='tableBtn hidden-mobile' onclick='upDownGoods("+full.goodsId+")' ><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/down.png' />下架 </a>";
                    //编辑按钮
                    var editBtn= "<a type='button' class='tableBtn hidden-mobile' onclick='updateProducts("+full.goodsId+")'><img class='hidden-md hidden-sm hidden-xs icon_img' src='Content/Home/image/xiugai.png' />编辑</a>";

                    return full.goodsState=='0'?upBtn+editBtn:(full.goodsState=='1'?downBtn+editBtn:'')
                },
            }
        ],
        ajxaParam: function() {
            return searchDataObj;
        },
    };
    window.initPageTab(pageTable);
}
//搜索查询按钮
function searchProductList() {
    listGoods();
}
//排序
function goodsBySort(type,obj){
    $("#goods_admin_list tr th").removeClass('hasSortAsc');
    $("#goods_admin_list tr th").removeClass('hasSortDesc');
    var val = '';
    if(type==orderObj.suggestPriceTag.name){
        //按照更新时间进行排序
        val = orderObj.suggestPriceTag.value;
        orderObj.suggestPriceTag.value = (val==0)?1:0;
        orderObj.goodsStateTag.value = '';
    }else if(type==orderObj.goodsStateTag.name){
        //按照提交数目进行排序
        val = orderObj.goodsStateTag.value;
        orderObj.suggestPriceTag.value = '';
        orderObj.goodsStateTag.value = (val==0)?1:0;
    }
    $(obj).addClass(val==0?'hasSortAsc':'hasSortDesc');
    listGoods();
}
//上下架
function upDownGoods(goodsId){
    $.confirm({
        msg: "是否确认上下架？",
        confirm: function () {
            CommonBase.showLoading();
            $.ajax({
                url: dataUrl + "goodsShelves",
                type: "POST",
                data: {goodsId: goodsId},
                success: function (data) {
                    if (data.success == true) {
                        simpleNotify("上下架成功！");
                        listGoods();
                        CommonBase.hideLoading();
                    }else{
                        simpleNotify(data.message);
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

//验证器
function goodsVerifier(obj,message){
    if (obj == undefined || obj == null || obj.length == 0) {
        return false;
    }else{
        return true;
    }
}
//添加商品
function addProducts() {
    //显示添加商品控件
    $("#remoteModal").modal({backdrop: 'static', keyboard: false});
    //初始化商品数据
    formInit();
}
//编辑更新商品
function updateProducts(productID) {
    $("#remoteEdit").modal({backdrop: 'static', keyboard: false});
    //初始化填充数据
    initUpdateProduct(productID);
}