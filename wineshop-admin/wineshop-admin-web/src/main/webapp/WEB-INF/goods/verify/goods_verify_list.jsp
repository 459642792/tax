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
          href="${pageContext.request.contextPath}/goods/verify/css/goods_verify_list.css"/>
</head>
<body>
<div id="widget-grid">
    <div id="listContainer">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div
                        class="jarviswidget jarviswidget-color-darken jarviswidget-sortable"
                        id="wid-id-list" data-widget-editbutton="false"
                        data-widget-deletebutton="false" data-widget-colorbutton="false"
                        style="position: relative; opacity: 1;">
                    <header role="heading">
                        <div class="sb">
                            <span>商品列表 </span>
                        </div>
                    </header>
                    <div role="content" style="border: 1px">
                        <div class="widget-body no-padding">
                            <div
                                 class="dataTables_wrapper form-inline no-footer"
                                 style="position: relative;">
                                <table id="goods_verify_list"
                                       class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th>条形码</th>
                                        <th class="clickSort" onclick="goodsVerifyBySort('submitOrderTag',this)">提交数</th>
                                        <th class="clickSort" onclick="goodsVerifyBySort('timeOrderTag',this)">最后提交时间</th>
                                        <th>名称预览</td>
                                        <th>操作</th>
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
</div>

<div class="modal fade" id="update_goods_verify" tabindex="-1" role="dialog"
     aria-labelledby="update_goods_verify_modal">
    <div class="modal-dialog" role="document" style="width: 900px;">
        <div class="modal-content pendingAuditList">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">待审核列表</h4>
            </div>
            <div class="infoContainer">
                <div class="modal-body no-padding" id="update_goods_verify_body">
                    <div  class="update_goods_verify_body">
                        <div style="float: left;width: 70%;">
                            <div class="update_goods_verify_title">
                                <span >条形码:&nbsp;</span><span id="verifyBarCode"></span>
                                <a  href="javascript:void(0)" onclick="addGoods()" class="btn btn-primary"  id="add_goods" style="width: 100px;height: 32px;float: right;">去添加商品
                                </a>
                                <button type="button" class="btn btn-danger" onclick="noByGoodsVerify()" id="no_by_goods_verify"
                                        style="width: 100px; height: 32px;float: right;margin-right: 10px;"  data-code="">审核不通过
                                </button>
                            </div>
                            <div>
                                <div class="dataTables_wrapper form-inline no-footer" style="position: relative;">
                                    <table id="goods_verify_vendor_list"
                                           class="table table-striped table-bordered table-hover"
                                           width="100%">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>商家</th>
                                            <th>商品名称</th>
                                            <th>提交时间</td>
                                            <th>售价</th>
                                            <th>上架状态</th>
                                            <th >图像信息</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="goodsImageBox">
                            <span class="preTile">图片预览</span>
                            <div class="preImageListContents"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px; margin-left:-100px;">
            <jsp:include page="goods_verify_add.jsp"></jsp:include>
        </div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>" data-url="${pageContext.request.contextPath}">
<script src="${pageContext.request.contextPath}/goods/verify/js/goods_verify_list.js"></script>

</body>
</html>
