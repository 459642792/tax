<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
</head>
<title>交易数据1</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/shopType/css/shop_type_list.css"/>
<script src="${pageContext.request.contextPath}/Scripts/libs/jquery-ui-1.10.3.js"></script>
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/shopType/zTreeStyle/zTreeStyle.css" type="text/css">
<script src="https://unpkg.com/vue"></script>
</head>
<body>
<input type="hidden" id="dataURL" value="${pageContext.request.contextPath}">
<div class="panel panel-default left-panel" id="dictionary">
    <div class="panel-heading">
        <div class="panel-title">
            <h3 class="panel-title">
                类目管理
            </h3>
        </div>
    </div>
    <div class="panel-body">
        <ul id="treeAttribute" class="ztree"></ul>
    </div>
</div>
<div class="panel panel-primary right-panel" id="dictionary_data">
    <div class="panel-heading">
        <div class="panel-title">
            <span>上级属性名称：</span><span id="attribute_name"></span>
        </div>
    </div>
    <div class="panel-body">
        <div id="widget-grid">

            <div id="listContainer">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div
                                class='jarviswidget jarviswidget-color-white jarviswidget-sortable'
                                id="wid-id-search" data-widget-editbutton="false"
                                data-widget-deletebutton="false" data-widget-colorbutton="false"
                                style="position: relative; opacity: 1; margin-bottom: 10px;"
                                data-widget-collapsed='@(HttpUtil.IsFromMobile(Request)?"true":"false")'>
                            <div role="content">
                                <div class="widget-body no-padding" style="    min-height: 50px;">
                                    <form id="search-form" class="smart-form"
                                          novalidate="novalidate">
                                        <fieldset>
                                            <div class="row" style="margin-top: -22px">
                                                <div class="col col-6"
                                                     style="float:left;width: 50%;font-size: 16px;    font-family: sans-serif;;">
                                                    <div style="    float: left;    width:300px;height: 3%;  line-height: 35px;margin-top: 8px;">
                                                        详情名称:&nbsp; <span data-id="" id="shop_type_name"></span></div>
                                                </div>
                                                <div style="float: left">
                                                    <button type="button" class="btn btn-primary navbar-btn"
                                                            id="updateAttribute">
                                                        修改
                                                    </button>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div
                                class="jarviswidget jarviswidget-color-darken jarviswidget-sortable"
                                id="wid-id-list" data-widget-editbutton="false"
                                data-widget-deletebutton="false" data-widget-colorbutton="false"
                                style="position: relative; opacity: 1;">
                            <header role="heading">
                                <div class="sb">
                                    <span>类型详情 </span>
                                </div>
                            </header>
                            <div role="content" style="border: 1px" id="infoDetailTable">
                                <div class="widget-body no-padding">
                                    <div id="dt_basic_wrapper"
                                         class="dataTables_wrapper form-inline no-footer"
                                         style="position: relative;">
                                        <table id="dictionaryDetailTable"
                                               class="table table-striped table-bordered table-hover"
                                               width="100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th data-class="expand"></th>
                                                <th data-class="expand">字典详情</th>
                                                <th data-class="expand">添加时间</th>
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

    </div>
</div>

<div class="modal fade" id="addInfoDictionary" tabindex="-1" role="dialog" aria-labelledby="dictionary_list_add"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="addInfoDictionaryLabel">修改匹配类型</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div class="row" style="margin-top: -22px">
                            <div style="float: left;margin-left: 20%;margin-top: 10px;width: 350px;">
                                <input type="text" maxlength="10" id="dictionary_name" style="    height: 33px;"
                                       placeholder="请输入名称">
                                <button type="button" class="btn btn-primary navbar-btn" style="    margin-left: 20px;"
                                        id="selectDictionary">
                                    查询
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div role="content" style="border: 1px" id="updateDetailTable">
                            <table id="updateDictionaryDetailTable"
                                   class="table table-bordered"
                                   width="100%">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th data-class="expand"></th>
                                    <th data-class="expand">基本字典类型</th>
                                    <th data-class="expand">添加时间</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="addDictionarySubmit">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<input type="hidden" id="baseUrl" data-url="${pageContext.request.contextPath}">
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/shopType/js/shop_type_list.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/shopType/js/jquery.ztree.all.js"></script>
</html>