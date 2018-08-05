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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/goods/brand/css/goods_brand_addUpdate.css"/>
</head>
<body class="brandListMain">
<div id="widget-grid">
    <div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width:800px; margin-left:-100px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">品牌管理</h4>
                </div>
                <div class="modal-body no-padding">
                    <!--添加子品牌信息-->
                    <form action="" method="Post" id="actionBrandForm" class="addSubBrand">
                        <!--品牌ID-->
                        <input type="hidden" id="brandTypeId" value="">
                        <!--品牌名称-->
                        <div class="labelItem subBrandName">
                            <label class="brandLabelTile"><span>*</span>品牌名称:</label>
                            <input type="text" placeholder="请输入品牌" maxlength="30" name="brandName" id="brandName" data-check="true" onfocus="checkSecondPassword()">
                        </div>
                        <!--酒类类型-->
                        <div class="labelItem  subBrandType">
                            <label class="brandLabelTile"><span>*</span>酒类名称:</label>
                            <label for="subBrandSpirit"><input type="radio" name="subBrandType" value="1101" id="subBrandSpirit">白酒</label>
                            <label for="subBrandGrape"><input type="radio" name="subBrandType"  value="1102" id="subBrandGrape">葡萄酒</label>
                            <label for="subBrandImport"><input type="radio" name="subBrandType" value="1103" id="subBrandImport">洋酒</label>
                            <label for="subBrandBeer"><input type="radio" name="subBrandType"   value="1104" id="subBrandBeer">啤酒</label>
                        </div>
                        <!--logo上传-->
                        <div class="labelItem upLoadImage">
                            <label class="brandLabelTile"><span>*</span>品牌图标:</label>
                            <label class="upload">
                                <img style=" z-index: 0;width: 100px;height: 100px;" src="Content/themes/smart/img/loadImg.png" name="brandLogo" alt="子品牌图标" id="brandLogo">
                                <input type="file" onchange="upLoadChange(event)">
                            </label>
                        </div>
                        <!--富文本编辑器-->
                        <div class="labelItem subBrandDesc">
                            <p class="subBrandDescTitle brandLabelTile"><span>*</span>品牌介绍</p>
                            <script id="container" name="content" type="text/plain"></script>
                        </div>
                        <!--提交按钮-->
                        <div class="submit">
                            <div onclick="addSubBrandObj()">完成</div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!--删除按钮的提示信息-->
    <div class="modal fade checkPasswordConfirmCover" id="checkPasswordForm">
        <div class="modal-content checkPasswordConfirm" >
            <p class="title">请输入管理员密码</p>
            <div class="waringInfo"></div>
            <div class="contents">
                <div class="detail">
                    <div class="title1">请输入密码</div>
                    <div class="input"><input type="text" placeholder="请输入管理员密码" id="checkPasswordFormInput"></div>
                </div>
                <div class="errorMessage"></div>
            </div>
            <button class="cancle" onclick="cancleCheckSecondPassword()">取消</button>
            <button class="confirm" onclick="confirmCheckSecondPassword()">确定</button>
        </div>
    </div>

</div>
<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>" data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src=" Scripts/uEdiotr/ueditor.config.js"></script>
<script type="text/javascript" src="Scripts/uEdiotr/ueditor.all.min.js"></script>
<script src="http://gosspublic.alicdn.com/aliyun-oss-sdk-4.3.0.min.js"></script>
<script src="${pageContext.request.contextPath}/goods/brand/js/goods_brand_addUpdate.js"></script>
</body>
</html>
