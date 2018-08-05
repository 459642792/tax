<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page import="com.blueteam.base.constant.Constants" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/goods/modelGoods/css/model_goods_up_list.css"/>
</head>
<body>
<div id="widget-grid">
    <div id="listContainer" style="margin-top: 100px;">
        <div class="row">
            <span style="margin-left: 100px;">权限须知：</span><br/>
            <span style="margin-left: 120px;">1.打开权限后，店铺将被允许自行添加商品简介，而不采用厂家标准商品简介；</span><br/>
            <span style="margin-left: 120px;">2.权限关闭后，店铺只能使用厂家标准商品简介。</span>
        </div>
        <div class="row" style="margin-top: 15px;width: 55%;">
            <div class="container">
                <form action="" method="get" style="    margin-left: 15%;">
                    <table class="table able-striped table-bordered" id="table_down_model_goods"
                           style="text-align: center;">
                        <tr>
                            <td colspan="2" style="font-weight: 900;"> 是否允许店铺编辑简介</td>
                            <td></td>
                        </tr>
                        <tr id="shop_type">
                            <td data-value="" name="" style="width:20%;text-align: center">类别</td>
                            <td style='width:50%'>
                                <select id="save_shop_type_name" onchange="selectShopType(this)">
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="updateGoodsAttributeSubmit"
                        style="width: 50px;height: 32px;">提交
                </button>
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
                    <table class="table able-striped table-bordered" id="save_model_goods_table">
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


<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>"
       data-url="${pageContext.request.contextPath}">

<script>
    $(function () {
        $.get("${pageContext.request.contextPath}/modelGoods/getByModelGoodsShopType", function (dataShopName) {
            var shopTypeNames = dataShopName.data.shopType.data;
            var shopTypeId;
            if (shopTypeNames != undefined && shopTypeNames.length > 0) {
                var shopTypes = new Array();
                $.each(shopTypeNames, function (key, value) {
                    if (key == 0) {
                        shopTypeId = value.id;
                    }
                    shopTypes.push("<option id=" + value.id + " >" + value.shopTypeName + "</option>")
                })
                $("#save_shop_type_name").html(shopTypes.join())
            }
            CommonBase.showLoading();
            listShopTpeAttribute(shopTypeId);
        });
    })
    $("#updateGoodsAttributeSubmit").click(function () {
        $("#down_model_goodsl_password_label").text("输入密码");
        $('#down_model_goods_password_model').modal();
        $("#passwordModelGoodsSubmit").click(function () {
            CommonBase.showLoading();
            var attributeId = $("input:radio[name='redact']:checked").attr("data-id");
            var id = $("input:radio[name='redact']:checked").val();
            var password = $("#down_model_goods_password_model #down_model_goods_password").val();
            if (password == undefined || password == null || password.length == 0) {
                simpleNotify("请输入密码");
                CommonBase.hideLoading();
                return false;
            }
            $.post("${pageContext.request.contextPath}/shopType/updateAttributeInfo", {
                attributeId: parseInt(attributeId),
                status: parseInt(id),
                password: password
            }, function (data) {
                if (data.success == false) {
                    simpleNotify(data.message);
                    CommonBase.hideLoading();
                    return false;
                } else {
                    simpleNotify(data.message);
                    $('#down_model_goods_password_model').modal('hide');
                    location.reload();
                    CommonBase.hideLoading();
                }
            });
        });
    });

    function selectShopType(obj) {
        CommonBase.showLoading();
        listShopTpeAttribute($(obj).find("option:selected").attr("id"));
    }

    function listShopTpeAttribute(shopTypeId) {
        debugger
        ediotr = "";
        editorDetails = "";
        $.get("${pageContext.request.contextPath}/modelGoods/getByShopTypeIdAttribute", {shopTypeId: parseInt(shopTypeId)}, function (data) {
            $("#shop_type").nextAll().remove();
            if (data.data != undefined && data.data != null) {
                var attribute = new Array;
                $("#shop_type").next().nextAll().remove();
                $.each(data.data, function (key, value) {
                    goodsAttribute(value.attribute, value.goodsAttributeInfo, attribute);
                });
                $("#shop_type").after(attribute.join());
                CommonBase.hideLoading();
            }
        });
    }

    function goodsAttribute(attributes, goodsAttributeInfo, attribute) {
        if (goodsAttributeInfo.goodsAttributeName == '简介') {
            var html = "";
            if (goodsAttributeInfo.goodsAttributeModify == 0) {
                html = "<tr><td>权限:</td>" +
                    "<td> <label><input name='redact' type='radio'  data-id='" + goodsAttributeInfo.id + "' value='1' />允许编辑 </label>&nbsp;&nbsp;" +
                    "<label><input name='redact' type='radio' data-id='" + goodsAttributeInfo.id + "' checked = 'checked'value='0' />不允许编辑 </label>" +
                    "</td> </tr>";
            } else {
                html = "<tr><td>权限:</td>" +
                    "<td> <label><input name='redact' type='radio'   data-id='" + goodsAttributeInfo.id + "' checked = 'checked' value='1' />允许编辑 </label>&nbsp;&nbsp;" +
                    "<label><input name='redact' type='radio'  data-id='" + goodsAttributeInfo.id + "' value='0' />不允许编辑 </label>" +
                    "</td> </tr>";
            }
            attribute.push(html);
        }

    }
</script>
</body>
</html>
