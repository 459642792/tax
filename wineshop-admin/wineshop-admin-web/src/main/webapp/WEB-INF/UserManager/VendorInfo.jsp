<%--
  Created by IntelliJ IDEA.
  User: libra
  Date: 2017/4/5
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<%@ page import="com.blueteam.base.constant.Constants" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <style>
        .infoTab {
            list-style: none;
            border-bottom: 1px solid #EAEAEA;
            float: left;
            width: 100%;
        }

        .infoContainer {
            padding: 0 30px;
        }

        .infoTab li {
            float: left;
            margin-right: 20px;
        }

        .infoTab li a {
            color: #333;
            display: inline-block;
            padding: 10px 20px;
            margin-bottom: -1px;
        }

        .infoTab .tabActive a {
            border-bottom: 2px solid orange;
        }

        .infoTab li a:hover {
            text-decoration: none;
        }

        .typeList {
            list-style: none;
            border: 1px solid #EAEAEA;
            border-radius: 5px;
        }

        .typeList li {
            padding: 0 10px;
            text-align: left;
        }

        .typeList li a {
            color: #333;
            padding: 10px 0;
            display: inline-block;
            width: 100%;
        }

        .typeList li a.border {
            border-bottom: 1px solid #EAEAEA;
        }

        .typeList .typeAcitive a {
            color: orange;
        }

        .detailContainer {
            display: none;
        }

        .detailPic {
            list-style: none;
            display: block;
            width: 100%;
        }

        .detailPic li {
            text-align: left;
        }

        #myTab .active a {
            background: #2196f3 !important;
            border: 1px solid #459E48 !important;
            color: #f0fff0;
        }

        #myTab a {
            text-align: center;
        }

        #myTab .active a {
            background: #2196f3 !important;
            border: 1px solid #459E48 !important;
            color: #f0fff0;
            border-radius: 10px;
            margin-bottom: 5px;
            text-align: center;
        }

        #myTab a:hover {
            border: 1px solid #459E48 !important;
            border-radius: 10px;
        }
    </style>
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title">店铺信息</h4>
</div>

<div class="modal-body no-padding">
    <div class="container" style="width: 100%">
        <div class="row">
            <div class="span3">
                <ul id="myTab" class="nav nav-tabs">
                    <li class="in active" style="margin-top: 5px;"><a href="#vendor_details" data-toggle="tab"
                                                                      style=" width: 100px;margin-left: 240px">店铺详情</a>
                    </li>
                    <li style="margin-top: 5px;"><a href="#goods_list" data-toggle="tab" style=" width: 100px;">商品列表</a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="vendor_details" style="width: 100%">
                <div id="formVendorInfo">
                    <div class="modal-body no-padding">
                        <form class="smart-form">
                            <fieldset style="max-height:700px; overflow-x: hidden; overflow-y: auto;">
                                <section>
                                    <div class="row">
                                        <label class="label col col-2">店铺名称</label>
                                        <div class="col col-4">
                                            <label class="input">
                                                <i class="icon-append fa fa-fw fa-info-circle"></i>
                                                <input type="text" v-model="model.name" name="title" class="input"
                                                       maxlength="15" placeholder="店铺名称"/>
                                                <b class="tooltip tooltip-top-right">请输入店铺名称</b>
                                            </label>
                                        </div>
                                    </div>
                                </section>

                                <section>
                                    <div class="row">
                                        <label class="label col col-2">店铺地址</label>
                                        <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                            {{(model.city === null ? "" :model.city.fullName)+model.addr}}
                                        </div>
                                    </div>
                                </section>


                                <section>
                                    <div class="row">
                                        <label class="label col col-2">店内公告</label>
                                        <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                            {{model.label}}
                                        </div>
                                    </div>
                                </section>
                                <section>
                                    <div class="row">
                                        <label class="label col col-2">主营品牌</label>
                                        <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                            {{model.pinpais}}
                                        </div>
                                    </div>
                                </section>
                                <section>
                                    <div class="row">
                                        <label class="label col col-2">营业时间</label>
                                        <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                            {{model.opentime}}
                                        </div>
                                    </div>
                                </section>


                                <section>
                                    <div class="row">
                                        <label class="label col col-2">店铺评分</label>
                                        <div class=" col col-10" style="padding-top: 7px;line-height: 19px;">
                                            {{model.score}}
                                        </div>
                                    </div>
                                </section>
                                <%--<section>--%>
                                <%--<div class="row">--%>
                                <%--<label class="label col col-2">店招图片</label>--%>
                                <%--<div class="col col-10">--%>
                                <%--<img style="width:200px;height: 200px;" :src="model.image"/>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</section>--%>

                                <section>
                                    <div class="row">
                                        <label class="label col col-2">联系人</label>
                                        <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                            {{model.contactPerson}}
                                        </div>
                                    </div>
                                </section>

                                <section>
                                    <div class="row">
                                        <label class="label col col-2">联系电话</label>
                                        <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                            {{model.telephone}}
                                        </div>
                                    </div>
                                </section>


                                <%--<section>--%>
                                <%--<div class="row">--%>
                                <%--<label class="label col col-2">店铺二维码</label>--%>
                                <%--<div class="col col-10">--%>
                                <%--<img :src="qrImage"  style="width:150px;height: 150px;"/>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</section>--%>
                                <section>
                                    <div class="row infoContainer">
                                        <ul class="infoTab" id="infoTab">
                                            <li class="tabActive infoTabs" data-for="infoContainer"><a
                                                    href="javascript:void(0)">照片</a></li>
                                            <li data-for="detailContainer" class="infoTabs"><a
                                                    href="javascript:void(0)">认证</a></li>
                                        </ul>
                                    </div>
                                </section>

                                <section>
                                    <div class="row infoContainer" id="infoContainer">
                                        <label class="col col-2">
                                            <%--<select id="sel_vendorImages"  class="input-sm Town" style="">--%>

                                            <%--<option value="CREATE_VENDOR_DETAIL_FACADE" selected="selected">门脸图</option>--%>
                                            <%--<option value="CREATE_VENDOR_DETAIL_AMBIENT">店内环境图</option>--%>
                                            <%--<option value="CREATE_VENDOR_DETAIL_PRODUCT">单品图</option>--%>
                                            <%--<option value="CREATE_VENDOR_GENERALVIEW">店铺全景</option>--%>
                                            <%--</select>--%>
                                            <ul class="typeList" id="typeList">
                                                <li class="infoTypes typeAcitive"><a class="shopPic border"
                                                                                     data-type="CREATE_VENDOR_GENERALVIEW"
                                                                                     href="javascript:void(0)">全景</a>
                                                </li>
                                                <li class="infoTypes"><a class="border localInfo" data-for="shopImage"
                                                                         :data-content="model.image"
                                                                         href="javascript:void(0)">店招</a></li>
                                                <li class="infoTypes"><a class="border localInfo" data-for="qrImage"
                                                                         :data-content="model.qrImage"
                                                                         href="javascript:void(0)">二维码</a></li>
                                                <li class="infoTypes"><a class="border shopPic"
                                                                         data-type="CREATE_VENDOR_DETAIL_FACADE"
                                                                         href="javascript:void(0)">门脸</a></li>
                                                <li class="infoTypes"><a class="border shopPic"
                                                                         data-type="CREATE_VENDOR_DETAIL_AMBIENT"
                                                                         href="javascript:void(0)">店内环境</a></li>
                                                <li class="infoTypes"><a class=" shopPic"
                                                                         data-type="CREATE_VENDOR_DETAIL_PRODUCT"
                                                                         href="javascript:void(0)">单品</a></li>
                                            </ul>
                                        </label>

                                        <div class="col col-10">
                                            <ul style="float:left;" id="shopPic">
                                                <li v-for="item in list"
                                                    style="list-style: none;float:left;width:100px;height:100px;margin: 10px;border: 1px solid #666;">
                                                    <img :src="item.img" style="width:100px;height:100px;"/>
                                                </li>
                                            </ul>
                                            <ul style="float:left;display:none" id="shopImage">
                                                <li style="list-style: none;float:left;width:100px;height:100px;margin: 10px;">
                                                    <img :src="model.image" style="width:150px;height: 150px;"/>
                                                </li>
                                            </ul>
                                            <ul style="float:left;display:none" id="qrImage">
                                                <li style="list-style: none;float:left;width:100px;height:100px;margin: 10px;">
                                                    <img :src="qrImage" style="width:150px;height: 150px;"/>
                                                </li>
                                            </ul>
                                        </div>


                                        <div id="pager" style="clear: both;"></div>

                                    </div>
                                    <div class="row detailContainer" id="detailContainer">

                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">法人姓名</label>
                                                <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                                    {{model.legalPerson}}
                                                </div>
                                            </div>
                                        </section>
                                        <section>
                                            <div class="row">
                                                <label class="label col col-2">身份证号码</label>
                                                <div class="col col-10" style="padding-top: 7px;line-height: 19px;">
                                                    {{model.legalPersonIdCard}}
                                                </div>
                                            </div>
                                        </section>
                                        <%--<section>--%>
                                        <%--<ul class="col col-12 detailPic">--%>
                                        <%--<li class="col col-4">--%>
                                        <%--<p>手持身份证照</p>--%>
                                        <%--</li>--%>
                                        <%--<li class="col col-4">--%>
                                        <%--<p>营业执照</p>--%>
                                        <%--</li>--%>
                                        <%--<li class="col col-4">--%>
                                        <%--<p>特许证件</p>--%>
                                        <%--</li>--%>
                                        <%--</ul>--%>
                                        <%--</section>--%>
                                    </div>
                                </section>
                            </fieldset>
                            <footer>
                                <button class="btn btn-primary" @click.prevent="post">
                                    <i class="fa fa-save"></i>
                                    保存
                                </button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                    取消
                                </button>
                            </footer>
                        </form>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="goods_list" style="width: 100%">
                <div class="relevance_vendor_Table" id="infoTable">
                    <table id="relevance_vendor_infoesTable" style="width: 100%" class="table table-bordered"
                           style="table-layout: fixed">
                        <thead>
                        <tr>
                            <th></th>
                            <th style="width: 100px;text-align: center" data-class="expand">单品名</th>
                            <th style="width: 160px;text-align: center" width="80px" data-class="expand">品牌</th>
                            <th style="width: 100px;text-align: center" data-class="expand">类别</th>
                            <th style="width: 100px;text-align: center" data-class="expand">产地</th>
                            <th style="width: 60px;text-align: center" data-class="expand">包装</th>
                            <th style="width: 60px;text-align: center" data-class="expand">价格</th>
                            <th style="width: 160px;text-align: center" data-class="expand">添加时间</th>
                            <th style="width:60px;text-align:  data-class=" expand
                            " >操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>


</div>


<div class="modal fade" id="vendor_goods_detail" tabindex="-1" role="dialog"
     aria-labelledby="vendor_goods_detail_modal" style="width: 900px;">
    <div class="modal-dialog" role="document" style="width: 900px;padding: 0px;margin-top: 0px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">商品详情</h4>
            </div>
            <div class="infoContainer">
                <div class="modal-body no-padding" id="detail_body_goods">
                    <table class="table able-striped table-bordered" id="table_vendor_goods_detail">
                        <tr id="save_table_vendor_goods_detail">
                            <td id="" data-value="" name="" style="width:20%;text-align: center">品牌</td>
                            <td>
                                <input type="text" id="save_vendor_pinpai_name" disabled="disabled"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="down_model_goods_password_model" tabindex="-1" role="dialog"
     aria-labelledby="down_model_goodsl_password_label">
    <div class="modal-dialog" role="document" style="width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">输入密码</h4>
            </div>
            <div class="infoContainer">
                <div class="modal-body no-padding">
                    <table class="table able-striped table-bordered" id="table_down_model_goods_password">
                        <tr>
                            <td>密码：</td>
                            <td>
                                <input type="password" id="down_model_goods_password">
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            style="width: 50px;height: 32px;">取消
                    </button>
                    <button type="button" class="btn btn-primary" id="passwordModelGoodsSubmit"
                            style="width: 50px;height: 32px;">确定
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-id="${id}" data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src=" Scripts/uEdiotr/ueditor.config.js"></script>
<script type="text/javascript" src="Scripts/uEdiotr/ueditor.all.min.js"></script>

<script src="${pageContext.request.contextPath}/UserManager/vendor_info.js"></script>
<script>
    $(function () {
        var pageIndex = 1;
        var pageSize = 20;
        var totalPage = 1;
        var totalMsg;
        var pager = false;

        function generPager() {
            $("#infoTab").on("click", ".infoTabs ", function () {
                $(this).addClass("tabActive").siblings().removeClass("tabActive");
                var module = $(this).attr("data-for")
                $("#" + module).fadeIn().siblings().fadeOut();
            })

            $("#typeList").on("click", ".infoTypes", function () {
                $(this).addClass("typeAcitive").siblings().removeClass("typeAcitive");
                if ($(this).find('a').hasClass("localInfo")) {
                    var module = $(this).find('a').attr("data-for");
                    $("#" + module).slideDown().siblings().hide();
                }
            })
            $("#kkpager").html('');
            //生成分页
            // 有些参数是可选的，比如lang，若不传有默认值
            $("#pager").createPage({
                current: pageIndex,
                //第几页
                totalMsg: totalMsg,
                //总条数
                pageSize: pageSize,
                //显示几条
                isCorotlPage: false,
                backFn: function (n, size) {
                    pageIndex = n;
                    pager = true;
                    $("#typeList .typeAcitive .shopPic").trigger('click', selChange);
                    return false;
                }
            }, true);
        }

        generPager();

        function selChange() {
            var code = $(this).attr("data-type");
            $("#shopPic").slideDown().siblings().hide();
            if (!pager)
                pageIndex = 1;
            else
                pager = false;
            CommonBase.request({url: "<%=Constants.APIS_WEBSITE%>/vendor/vendorPhotoList", method: "GET"},
                {
                    TypeCode: code,
                    ForeignKey:${id},
                    pageIndex: pageIndex,
                    pageSize: pageSize
                },
                function (ret) {
                    lm.vm.list.length = 0;
                    if (ret.success && ret.data && ret.data.length > 0) {
                        for (var i = 0; i < ret.data.length; i++) {
                            lm.vm.list.push(ret.data[i]);
                        }
                        totalMsg = ret.count;
                        totalPage = Math.ceil(ret.count / pageSize);
                    } else {
                        lm.vm.list.sort();
                    }
                    generPager();
                });
        }


        var lm = new EditPage("#formVendorInfo", {
            getAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/get?id=${id}',
                method: 'GET'
            },
            editAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/editVendorName',
                method: 'POST'
            },
            bindEnd: function () {
                $("#typeList .shopPic").on('click', selChange);
                $("#typeList .shopPic").eq(0).trigger('click', selChange);
            },
            include: ["id", "name"],
            beforeBinding: function () {
                lm.vm.list = new Array();
                lm.vm.qrImage = "";
                $.get("${pageContext.request.contextPath}/api/vendor/getVendorQrcode?vendorId=${id}", function (ret) {
                    if (ret && ret.success)
                        lm.vm.qrImage = '${pageContext.request.contextPath}' + ret.data;
                });


            },
            beginPost: function (data) {
                if (data.name === "") {
                    simpleNotify("请输入店铺名称");
                    return false;
                }
                return true;
            }
        });
        lm.init();
        vendorList();
    });


</script>

</body>
</html>