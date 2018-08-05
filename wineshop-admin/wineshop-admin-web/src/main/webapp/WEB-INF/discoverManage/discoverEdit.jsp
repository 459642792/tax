<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title" id="dialogTitle">查看/修改</h4>
</div>
<div class="modal-body no-padding">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">标题</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" id="title" name="title" class="input" maxlength="35"
                                   placeholder="请输入标题"/>
                            <b class="tooltip tooltip-top-right">请输入标题</b>
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">类型</label>
                    <div class="col col-4">
                        <select class="input-sm" id="coverType" name="coverType" style="width:100%">
                            <option value="" selected="selected">请选择</option>
                            <option value="1">喜宴精选</option>
                            <option value="2">聚抢五折</option>
                            <option value="3">海外购</option>
                            <option value="4">品鉴招商</option>
                        </select>
                    </div>
                    <label class="label col col-2">头条显示</label>
                    <div class="col col-4">
                        <label style="padding-top: 7px">
                            <input style="vertical-align: -2px;margin-right: 5px" type="checkbox" id="isShowTitle">显示到商圈头条
                        </label>
                    </div>
                </div>
            </section>
            <c:if test="${Power=='admin'}">
                <div id="allAddr">
                    <section class="row">
                        <div class="address">
                            <label class="label col col-2">发布地区</label>
                            <div class="col col-9" class="getNewAddr">
                                <select class="input-sm" id="Province" name="Province" style="width:150px">
                                </select>
                                <select class="input-sm Town city" name="city" style="width:150px">
                                </select>
                                <select class="input-sm Town county" name="county" style="width:150px">
                                </select>
                            </div>
                            <label class="col col-1 glyphicon glyphicon-plus" id="addAddr"
                                   style="height: 24px;line-height: 24px"></label>
                        </div>
                    </section>
                </div>
            </c:if>
            <section>
                <div class="row">
                    <label class="label col col-2">标签</label>
                    <label class="col col-10">
                        <input type="hidden" class="form-control" id="tagMenu">
                    </label>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">链接店铺</label>
                    <div class="col col-10">
                        <label class="input">
                            <select type="text" multiple="multiple" class="input " id="shopLink" name="shopLink"
                                    placeholder="请输入店铺链接">
                            </select>
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">内容</label>
                    <div class="col col-10">
                        <!-- 加载编辑器的容器 -->
                        <script id="container" name="content" type="text/plain">

                        </script>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">封面</label>
                    <div class="col col-10">
                        <label class="input" id="coverImg" style="width: 380px">
                            <ul style="padding: 10px">
                                <li style="cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative">
                                    <img style=" z-index: 0;width: 100px;height: 100px;"
                                         src="Content/themes/smart/img/loadImg.png" alt="">
                                    <input type="file" name="Upload" value="" accept="image/jpg,image/jpeg,image/png"
                                           class="fileQj"
                                           style=" z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;"/>
                                </li>
                                <li style="cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative">
                                    <img style=" z-index: 0;width: 100px;height: 100px;"
                                         src="Content/themes/smart/img/loadImg.png" alt="">
                                    <input type="file" name="Upload" value="" accept="image/jpg,image/jpeg,image/png"
                                           class="fileQj"
                                           style=" z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;"/>
                                </li>
                                <li style="cursor:pointer;float:left;list-style: none;margin-right: 20px;position: relative">
                                    <img style=" z-index: 0;width: 100px;height: 100px;"
                                         src="Content/themes/smart/img/loadImg.png" alt="">
                                    <input type="file" name="Upload" value="" accept="image/jpg,image/jpeg,image/png"
                                           class="fileQj"
                                           style=" z-index: 1;position: absolute; top: 0%; opacity: 0; width: 100px;height:100px;"/>
                                </li>
                            </ul>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <label class="label col col-2"></label>
                    <div class="col col-10">
                        <p style="margin: 5px 0;clear: both;color: red">1.选填</span>，如不上传封面，则文章无封面。</p>
                        <p style="margin: 5px 0;color: red">2.封面只能选择上传1张或3张。</p>
                    </div>
                </div>
                <c:if test="${Power=='admin'}">
                    <div class="row">
                        <label class="label col col-2">推荐</label>
                        <div class="col col-10" style="padding: 7px 15px 0">
                            <p>
                                <input type="checkbox" style="vertical-align: -2px;margin-right: 5px;" id="isGroom">
                                推荐到活动首页专区
                            </p>
                        </div>
                    </div>
                </c:if>
            </section>
        </fieldset>
        <footer id="subBtnItem">

        </footer>
    </form>
</div>
<input type="hidden" id="baseUrl" data-id="${Id}" data-admin="${Power}" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>"
       data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src=" Scripts/uEdiotr/ueditor.config.js"></script>
<script type="text/javascript" src="Scripts/uEdiotr/ueditor.all.min.js"></script>
<script type="text/javascript" src="discoverManage/js/discoverEdit.js"></script>
</body>
</html>
