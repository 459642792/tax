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
          href="${pageContext.request.contextPath}/goods/attr/css/goods_attr_list.css"/>
</head>
<body>
<div id="widget-grid">
    <div id="listContainer">
        <div class="row" id="listTypeAttr">
        </div>
        <div class="row content" id="content" style="padding: 0px;">
            <div style="display: block;" id="same">
                <div class="col-lg-2 col-md-2 col-sm-2 theme">
                    <div>酒精度</div>
                    <div>0-100</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 theme">
                    <div>规格</div>
                    <div>＞0</div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 theme">
                    <div>包装</div>
                    <div>
                        <table>
                            <tr>
                                <td>单瓶</td>
                            </tr>
                            <tr>
                                <td>整箱</td>
                            </tr>
                            <tr>
                                <td>套盒</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 theme">
                    <div>条码</div>
                    <div></div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 theme">
                    <div>指导价</div>
                    <div></div>
                </div>
            </div>
            <div id="unlike" style="display: none;" >
                <div style="height: 100%;width: 20%;float: left;border-right: 1px solid #797979">
                    <table class="table table-hover table-bordered" id="attributeNames" >
                        <tr v-for="(value,index) in message">
                            <td :id="value.id" :data-name="value.attrName"  v-if="index == 0"
                                class="info" v-on:click="selectAttributeValue($event,value)">
                                {{value.attrName}}
                            </td>
                            <td :id="value.id" :data-name="value.attrName"  v-else
                                v-on:click="selectAttributeValue($event,value)">
                                {{value.attrName}}
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="height: 100%;width: 80%;float: left">
                    <div style="height: 50px;">
                        <div class="attribute_name" id="attribute_name"></div>
                        <button class="button btn-primary " id="add_attrubute_data" onclick="addAttributeData()">添加</button>
                    </div>
                    <div role="content" style="border: 1px" id="infoDetailTable">
                        <div class="widget-body no-padding" style="margin:0px;" id="attribute_data_select">
                            <div id="dt_basic_wrapper" class="dataTables_wrapper form-inline no-footer" style="position: relative;">
                                <table id="attribute_value_table" class="table table-striped table-bordered table-hover" style="text-align: center;width：100%;">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th data-class="expand" style="text-align: center;font-size: 20px;width">SKU属性值</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                        <div class="widget-body no-padding" style="margin:0px;" id="attribute_data_select_update">
                            <div id="dt_basic_wrapper" class="dataTables_wrapper form-inline no-footer" style="position: relative;">
                                <table id="attribute_value_update_table" class="table table-striped table-bordered table-hover" style="text-align: center;width：100%;">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th data-class="expand" style="text-align: center;font-size: 20px;width">SKU属性值</th>
                                        <th data-class="expand"  style="text-align: center;font-size: 20px;width">操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                        <div style="margin:0px;display: none;" id="attribute_data_input" >
                            <div class="theme" style="width: 30%;margin-top: 130px;margin-left: 180px;">
                                <div></div>
                                <div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<%--新增--%>
<div class="modal fade" id="add_attribute_data_model" tabindex="-1" role="dialog"
     aria-labelledby="add_attribute_data_label">
    <div class="modal-dialog" role="document" style="width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">添加新值</h4>
            </div>
            <div class="modal-body no-padding" style="text-align: center">
                <input type="text" maxlength="30" name="" style="margin-top: 15px;height: 30px;width: 90%;" id="add_attribute_data"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        style="width: 50px;height: 32px;">取消
                </button>
                <button type="button" class="btn btn-primary" onclick="addAttributeDataSubmitClick()" id="add_attribute_data_submit"
                        style="width: 50px;height: 32px;">确定
                </button>
            </div>
        </div>
    </div>
</div>
<%--修改--%>
<div class="modal fade" id="update_attribute_data_model" tabindex="-1" role="dialog"
     aria-labelledby="update_attribute_data_label">
    <div class="modal-dialog" role="document" style="width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" >修改新值</h4>
            </div>
            <div class="modal-body no-padding" style="text-align: center">
                <table class="table able-striped table-bordered" >
                    <tr>
                        <td style="float: right;">属性值：</td>
                        <td style="width: 60%;">
                            <input type="text"   style="-webkit-box-shadow: 0 0 0 1000px white inset !important;"  data-code ="" id="update_attribute_data">
                        </td>
                    </tr>
                    <tr>
                        <td style="float: right;">密码：</td>
                        <td>
                            <input type="password" autocomplete="off" style="-webkit-box-shadow: 0 0 0 1000px white inset !important;" id="update_attribute_data_password">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        style="width: 50px;height: 32px;">取消
                </button>
                <button type="button" class="btn btn-primary" onclick="updateAttributeDataSubmitClick()" id="update_attribute_data_submit"
                        style="width: 50px;height: 32px;">确定
                </button>
            </div>
        </div>
    </div>
</div>
<%--删除--%>
<div class="modal fade" id="delete_attribute_data_model" tabindex="-1" role="dialog"
     aria-labelledby="delete_attribute_data_label">
    <div class="modal-dialog" role="document" style="width: 500px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">是否删除该属性值</h4>
            </div>
            <div class="modal-body no-padding" style="text-align: center">
                <table class="table able-striped table-bordered" >
                    <tr>
                        <td style="float: right;">请输入密码：</td>
                        <td style="width: 60%;">
                            <input type="password" autocomplete="off" data-code="" style="-webkit-box-shadow: 0 0 0 1000px white inset !important;" id="delete_attribute_data_password">
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"
                        style="width: 50px;height: 32px;">取消
                </button>
                <button type="button" class="btn btn-primary" onclick="deleteAttributeDataSubmitClick()" id="delete_attribute_data_submit"
                        style="width: 50px;height: 32px;">确定
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px; margin-left:-100px;"></div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-img-url="<%=Constants.IMAGE_UPLOAD_URL%>"
       data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src=" Scripts/uEdiotr/ueditor.config.js"></script>
<script type="text/javascript" src="Scripts/uEdiotr/ueditor.all.min.js"></script>
<script src="${pageContext.request.contextPath}/goods/attr/js/goods_attr_list.js"></script>
</body>
</html>
