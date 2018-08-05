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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/goods/goods/css/goods_admin_edit.css"/>
</head>
<body>
<div class="productsEditAdd">
    <form id="editForm" action="">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                &times;
            </button>
        </div>
        <!--基本信息-->
        <div class="baseInfo">
            <p class="title"><span>*</span><span>基本信息</span></p>
            <div class="detail">
                <!--品牌类型-->
                <section class="infoList">
                    <div class="infoListItemLabel"><span class="mustFill">*</span>品类选择:</div>
                    <div class="infoListItemContent productType">
                        <span class="editBrandType active" data-value="1101" name="editGoodsType">白酒</span>
                        <span class="editBrandType" data-value="1102" name="editGoodsType">葡萄酒</span>
                        <span class="editBrandType" data-value="1103" name="editGoodsType">洋酒</span>
                        <span class="editBrandType" data-value="1104" name="editGoodsType">啤酒</span>
                    </div>
                </section>
                <!--商品名称-->
                <section class="infoList">
                    <div class="infoListItemLabel"><span class="mustFill">*</span>商品名称</div>
                    <div class="infoListItemContent productName productInput"><input type="text" maxlength="30" placeholder="请输入商品名称" name="goodsName" id="editGoodsName" data-scope="all"/></div>
                </section>
                <!--条形码-->
                <section>
                    <section class="infoList">
                        <div class="infoListItemLabel"><span class="mustFill">*</span>商品条码</div>
                        <div class="infoListItemContent productQRCode productInput"><input type="text"  maxlength="25" placeholder="请输入商品条码" name="barCode" id="editBarCode" data-scope="all" /></div>
                    </section>
                </section>
                <!--主品牌-->
                <section class="infoList">
                    <div class="infoListItemLabel"><span class="mustFill">*</span>商品主品牌</div>
                    <div class="infoListItemContent productQRCode productInput">
                        <select id="editProductMainBrand" name="mainBrandId" data-scope="all"></select>
                    </div>
                </section>
                <!--子品牌-->
                <section class="infoList">
                    <div class="infoListItemLabel"><span class="mustFill">*</span>商品子品牌</div>
                    <div class="infoListItemContent productQRCode productInput">
                        <select id="eidtProductSubBrand" name="brandId" data-scope="all"></select>
                        <div class="addNewBrand"><a href="${pageContext.request.contextPath}/Index/index#/fjjhadmin/goodsBrand/listGoodsBrand" >新品牌？去添加新品牌</a></div>
                    </div>
                </section>
                <!--商品基本属性-->
                <div class="baseAttrInfo" id="eidtBaseAttrList"></div>
            </div>
        </div>
        <!--详细信息-->
        <div class="detailInfo">
            <p class="title"><span></span><span>详细信息</span></p>
            <div class="detail">
                <!--商品特有属性-->
                <div id="editSpecialAttrList"></div>
                <!--商品描述-->
                <section class="infoList">
                    <div class="infoListItemLabel"><span class="mustFill">*</span>商品描述:</div>
                    <script class="infoListItemContent" id="editContainer" name="content" type="text/plain"></script>
                </section>
                <!--商品图片-->
                <section class="infoList">
                    <div class="infoListItemLabel"><span class="mustFill">*</span>商品图片</div>
                    <div class="infoListItemContent productsImageList" id="eidtPhotoList" name="photoList">
                        <div class="addImageList">
                            <img style=" z-index: 0;width: 100px;height: 100px;" src="Content/themes/smart/img/loadImg.png" name="brandLogo" alt="子品牌图标" id="editBrandLogo">
                            <input type="file" onchange="uploadImage(event)">
                        </div>
                    </div>
                </section>
                <!--指导价格-->
                <section class="infoList">
                    <div class="infoListItemLabel"><span class="mustFill">*</span>商品价格</div>
                    <div class="infoListItemContent productInput"><input type="text" placeholder="请输入商品价格" name="suggestPrice" id="editSuggestPrice" data-scope="all"/>元</div>
                </section>
            </div>
        </div>
        <div class="submit">
            <button type="button" class="submitBtn" onclick="editSubmitForm();return false;" id="editSubmit">完成</button>
        </div>
    </form>
</div>
<script type="text/javascript" src=" Scripts/uEdiotr/ueditor.config.js"></script>
<script type="text/javascript" src="Scripts/uEdiotr/ueditor.all.min.js"></script>
<script src="http://gosspublic.alicdn.com/aliyun-oss-sdk-4.3.0.min.js"></script>
<script src="${pageContext.request.contextPath}/goods/goods/js/goods_admin_edit.js"></script>

</body>
</html>
