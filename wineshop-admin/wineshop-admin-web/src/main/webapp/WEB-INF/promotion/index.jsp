<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="promotion/css/promotion.css"/>
</head>
<body>
<input id="baseUrl" type="hidden" dataurl="${pageContext.request.contextPath}">
<input id="listPromotionCatagoryId" type="hidden" value="">
<div id="widget-grid" style="width: 40%;float: left">
    <div id="listContainer">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div
                        class="jarviswidget jarviswidget-color-darken jarviswidget-sortable"
                        id="wid-id-list" data-widget-editbutton="false"
                        data-widget-deletebutton="false" data-widget-colorbutton="false"
                        style="position: relative; opacity: 1;">
                    <header role="heading">
                        <div class="sb" style="padding: 5px 0;display: flex">
                            <div style="flex: 1;text-align: right">
                                <sapn class="btn btn-primary atncol_c" id="btnSearch" onclick="refresh()">刷新</sapn>
                            </div>

                        </div>
                    </header>
                    <div role="content" style="padding-top: 0px">
                        <div class="widget-body no-padding" style="margin: 0">
                            <div id="dt_basic_wrapper"
                                 class="dataTables_wrapper form-inline no-footer"
                                 style="position: relative;">
                                <table id="orderInfo_list1"
                                       class="table table-striped table-bordered table-hover"
                                       width="100%" style="font-size: 13px">
                                    <thead>
                                    <tr>
                                        <td data-class="phone" style="border-left: none !important;">活动名称</td>
                                        <td data-hide="phone">更新时间</td>
                                        <td data-hide="phone,tablet">操作者</td>
                                        <td data-hide="phone,tablet">位置模式</td>
                                        <td data-hide="phone,tablet">操作</td>
                                    </tr>
                                    </thead>
                                    <tbody class="links_content">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="widget-grid1" style="width: 58%;float: right">
    <div id="listContainer1">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div
                        class="jarviswidget jarviswidget-color-darken jarviswidget-sortable"
                        id="wid-id-list1" data-widget-editbutton="false"
                        data-widget-deletebutton="false" data-widget-colorbutton="false"
                        style="position: relative; opacity: 1;">
                    <header role="heading">
                        <div class="sb" style="padding: 5px 0;display: flex">
                            <div style="flex: 1;">
                                <sapn id="ad_textRight">活动信息：喜酒</sapn>
                            </div>

                        </div>
                    </header>
                    <div class="row">
                        <div class="col">
                            <span>地区</span>
                            <select class="input-sm" id="Province" name="Province" style="width:100px">
                            </select>
                            <select class="input-sm Town" id="city" name="city" style="width:100px">
                            </select>
                            <span style="margin-left: 10px">状态</span>
                            <select class="input-sm Town" id="ad_state" name="state" style="width:70px">
                                <option value="2">全部</option>
                                <option value="1">正常</option>
                                <option value="0">过期</option>
                            </select>
                            <sapn class="btn btn-primary atncol_c promotion_btn" id="shopSearch">查询</sapn>
                            <sapn class="btn btn-primary atncol_c promotion_btn" id="addGoods">新增</sapn>
                        </div>
                    </div>
                    <div role="content">
                        <div class="widget-body no-padding" style="margin: 0;">
                            <table id="adshop_list"
                                   class="table table-striped table-bordered table-hover"
                                   width="100%" style="font-size: 13px">
                                <thead>
                                <tr>
                                    <td>ID</td>
                                    <td data="phone">店铺</td>
                                    <td data-hide="phone tablet">商品</td>
                                    <td data-hide="phone tablet">点击量</td>
                                    <td data-hide="phone tablet">投放时间</td>
                                    <td data-hide="phone tablet">结束时间</td>
                                    <td data-hide="phone tablet">状态</td>
                                    <td data-hide="phone tablet">权重</td>
                                    <td data-hide="phone tablet">操作</td>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 编辑活动 -->
<div class="modal fade orderAdmin_orderDetail" id="editAdlist" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: none;padding-bottom: 0">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">× </span>
                </button>
                <h4 class="modal-title orderAdmin_detail_titile" id="editAdlist_title">【喜酒】文案图片编辑</h4>
            </div>
            <div class="modal-body" style="padding: 0 40px;padding-top:0;margin-top: 20px ">

                <div class="col" style="margin-top: 20px">
                    <div>
                        <span><i style="color: red">*</i>文案编辑</span>
                        <input type="hidden" id="promotionCatagoryId">
                        <textarea class="form-control"
                                  placeholder="请输入内容，(最多3行，每行最多6个汉字，请使用换行区分)"
                                  id="editAD_listText" maxlength="18">

                        </textarea>
                        <span class="redColor errorText">请输入内容，(最多3行，每行最多6个汉字，请使用换行区分)</span>
                    </div>
                </div>
                <div class="col" style="margin-top: 20px">
                    <span><i style="color: red">*</i>图片编辑</span>
                    <div class="editAdList_img">
                        <img src="" id="brandLogo">
                        <input type="file" onchange="upLoadChange(event)">
                        <div class="editAdList_imgAdd">上传图片</div>
                    </div>
                    <span class="redColor errorImg">请上传图片</span>
                </div>


                <button type="button" class="btn btn-primary atncol_c promotion_btn1" onclick="editCatagorySAve(this)">
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
<!-- 新增和编辑活动商品 -->

<div class="modal fade orderAdmin_orderDetail" id="addActivity" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="overflow-y: auto">
            <div class="modal-header" style="border: none;padding-bottom: 0">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">× </span>
                </button>
                <h4 class="modal-title orderAdmin_detail_titile">活动商品</h4>
            </div>
            <div class="modal-body" style="padding: 0,40px;padding-top:0;margin-top: 20px ">
                <div class="col">
                    <span><i style="color: red">*</i>请选择地区</span>
                    <select class="input-sm" id="DetailProvince" name="DetailProvince" style="width:100px">
                    </select>
                    <select class="input-sm Town" id="DetailCity" name="DetailCity" style="width:100px">
                    </select>
                </div>
                <input type="hidden" id="vendorId">
                <input type="hidden" id="vendorName">
                <input type="hidden" id="goodsId">
                <input type="hidden" id="goodsName">
                <div class="col" style="margin-top: 20px">
                    <div style="width: 49%;float: left;position: relative">
                        <span><i style="color: red">*</i>请选择商家</span>
                        <input class="input-sm" type="text" id="content_name" maxlength="15"
                               placeholder="请输入商家或者输入商家ID">
                        <span class="redColor editError vendorNameError">请输入商家</span>
                        <div class="ad_displayNon" id="ad_shops">
                            <ul>
                            </ul>
                        </div>
                    </div>

                    <div style="width: 49%;float: right">
                        <span><i style="color: red">*</i>请选择商品</span>
                        <input class="input-sm" type="text" id="adGoods_name" maxlength="15"
                               placeholder="请输入商品或者输入商品ID">
                        <span class="redColor editError goodsNameError">请输入商品</span>
                        <div class="ad_displayNon" id="ad_goods">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col" style="margin-top: 20px;clear: both">
                    <div style="width: 49%;float: left; margin-top: 20px">
                        <span style="padding-right: 13px; "><i style="color: red">*</i>活动日期</span>
                        <label class="input" style="display: inline-block"> <input
                                type="text"
                                class="form-control"
                                data-date-format="yyyy-mm-dd"
                                id="content_beginTime"
                                placeholder="开始时间">
                        </label>
                        <span class="redColor editError goodsTimeError">活动日期</span>
                    </div>
                    <div style="width: 49%;float: right;margin-top: 20px">
                        <label class="input"><input
                                type="text"
                                class="form-control"
                                data-date-format="yyyy-mm-dd"
                                id="content_endTime"
                                placeholder="结束时间">
                        </label>
                    </div>
                </div>
                <div class="col" style="clear: both;overflow: hidden">
                    <div style="margin-top: 20px">
                        <span style="padding-right: 13px;"><i style="color: red">*</i>权重设置</span>
                        <select class="input-sm" id="content_Index">
                            <option>0</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                            <option>9</option>
                            <option>10</option>
                        </select><br>
                        <span style="padding-left: 75px;color: #c0c0c0;padding-top: 5px">0表示无排序需求，10-6根据大小表示排序越靠前</span>
                        <span class="redColor editError goodsWeightError"></span>
                    </div>
                    <input type="hidden" id="promotionInfoId">
                </div>
                <button type="button" class="btn btn-primary atncol_c promotion_btn1" id="ADsubmit">提交</button>
            </div>
        </div>
    </div>
</div>
<script src="http://gosspublic.alicdn.com/aliyun-oss-sdk-4.3.0.min.js"></script>
<script src="promotion/js/index.js"></script>

<script type="text/javascript">

    $(function () {
        //初始化调用省份
        function getADDr(resolve, reject) {

            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/city/getProvinceList",
                success: function (result) {
                    detailGetCity(result.data[7].code)//默认为成都
                    for (var i = 0; i < result.data.length; i++) {
                        $('#Province').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                        $('#DetailProvince').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                    }
                    //默认为四川成都
                    $('#Province,#DetailProvince').val(result.data[7].code)
                    resolve(result)
                }
            })
        }
        var p = new Promise(getADDr);
        p.then(function (result) {
            //默认为四川成都
            fristGetCity(result.data[7].code).then(getCatagory).then(function () {
                getPromotionList()
            })
        })
        /*getADDr().then(function (res) {

         }).fristGetCity(result.data[0].code).then(getCatagory).then(getPromotionList());*/
        //省市联动数据调用
        $('#Province').change(function () {
            fristGetCity();
        })
        //省市联动数据调用
        $('#DetailProvince').change(function () {
            var me = $(this).attr('disabled');
            if (me) {
                return
            }
            detailGetCity().then(getShops);
        })
        $('#DetailCity').change(function () {
            var me = $(this).attr('disabled');
            if (me) {
                return
            }
            getShops();
        })

    });	//页面初始化调用
    //frist
    function fristGetCity(msg) {
        $("#city").empty();
        return new Promise(function (resolve, reject) {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/city/getCityByParent",
                data: {
                    parent: msg ? msg : $('#Province').val()
                },
                success: function (result) {
                    for (var i = 0; i < result.data.length; i++) {
                        $('#city').append("<option value=" + result.data[i].code + ">" + result.data[i].name + "</option>");
                    }
                    resolve(result)
                }
            })
        });
    }
    $('#shopSearch').click(function () {
        getPromotionList()
    })
</script>
</body>
</html>
