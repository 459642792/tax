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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/dictionary/css/dictionary_list.css"/>
<script src="${pageContext.request.contextPath}/Scripts/libs/jquery-ui-1.10.3.js"></script>
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/vue"></script>
</head>
<body>
<input type="hidden" id="dataURL" value="${pageContext.request.contextPath}">
<div class="panel panel-default left-panel" id="dictionary">
    <div class="panel-heading">
        <div class="panel-title" style="    height: 30px;">
            <span style="    font-size: 26px;font-family: monospace;">基础类型列表</span>
            <div style="float:right">
                <button type="button" class="btn btn-primary" v-on:click="addDictionary()">添加</button>
                &nbsp;&nbsp;
                <button type="button" class="btn btn-default" v-on:click="deleteDictionary()">删除</button>
            </div>
        </div>
    </div>
    <div class="panel-body">
        <table class="table table-hover table-bordered" id="dictionary_list">
            <tr v-for="(value,index) in message">
                <td :id="value.id" :data-name="value.dictionaryName" :data-code="value.dictionaryCode" v-if="index == 0"
                    class="info" v-on:click="selectDictionary($event)">
                    {{value.dictionaryName}}
                </td>
                <td :id="value.id" :data-name="value.dictionaryName" :data-code="value.dictionaryCode" v-else
                    v-on:click="selectDictionary($event)">
                    {{value.dictionaryName}}
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="panel panel-primary right-panel" id="dictionary_data">
    <div class="panel-heading">
        <div class="panel-title">
            <span>类型详情</span>
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
                            <header role="heading">
                                <div class="sb">
                                    <span>查询条件 </span>
                                </div>
                            </header>
                            <div role="content">
                                <div class="widget-body no-padding" style="    min-height: 50px;">
                                    <form id="search-form" class="smart-form"
                                          novalidate="novalidate">
                                        <fieldset>
                                            <div class="row" style="margin-top: -22px">
                                                <div class="col col-6" style="float:left;width: 60%">
                                                    <div style="    float: left;width:20%;height: 3%; text-align: center; line-height: 35px;margin-top: 8px;">
                                                        详情名称
                                                    </div>
                                                    <input input type="text" class="form-control"
                                                           style=" height: 2%; margin-top: 8px;float: left;width: 70%;"
                                                           maxlength="10" id="exactValue" placeholder="请输入详情名称,支持模糊查找">
                                                </div>
                                                <div style="float: right">
                                                    <a id="btnSearch" class="btn btn-primary atncol_c">查询</a>
                                                    <button type="button" class="btn btn-primary navbar-btn"
                                                            id="addDictionaryData">
                                                        添加详情
                                                    </button>
                                                    <button type="button" class="btn btn-default navbar-btn"
                                                            id="deleteDictionaryData">
                                                        删除详情
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
                                                <th data-class="expand" style="width: 8px;"></th>
                                                <th data-class="expand">名称</th>
                                                <th data-class="expand">类别</th>
                                                <th data-class="expand">显示顺序</th>
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
                <h4 class="modal-title" id="addInfoDictionaryLabel">添加基础类型</h4>
            </div>
            <div class="modal-body">
                <div class="input-group">
                    <span class="input-group-addon">基础类型名称:</span>
                    <input type="text" class="form-control" maxlength="10" id="dictionary_name" placeholder="请输入名称">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="addDictionarySubmit">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<div class="modal fade" id="add_dictionnay_data" tabindex="-1" role="dialog" aria-labelledby="add_dictionnay_data_lable"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="addDictionnayDatayLabel">添加字典详情</h4>
            </div>
            <div class="modal-body">
                <table class="table">
                    <tr>
                        <td>所属类型：</td>
                        <td><span id="dictionary_data_type"></span></td>
                    </tr>
                    <tr>
                        <td>详情样式：</td>
                        <td style="width:80%">
                            <div>
                                <label class="checkbox-inline" style="    padding: 0px;">
                                    <input type="radio" data-vlaue="1" name="optionsRadiosinline" id="optionsRadios3"
                                           value="option1" checked>精确值
                                </label>
                                <label class="checkbox-inline">
                                    <input type="radio" data-vlaue="2" name="optionsRadiosinline" id="optionsRadios4"
                                           value="option2">区间值
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>字典详情：</td>
                        <td class="inputDiv">
                            <div class="distionaryVlue" id="dictionary_data_value_1">
                                <input type="text" name="" maxlength="10" id="dictionary_data_exactValue">
                            </div>
                            <div class="distionaryVlue active" id="dictionary_data_value_2">
                                <input type="text" name="" style="width: 40%" maxlength="10"
                                       id="dictionary_data_activeinfo_exactValue">
                                &nbsp; <span>-</span>&nbsp;
                                <input type="text" name="" style="width: 40%" maxlength="10"
                                       id="dictionary_data_activeinfo_intervalValue">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>显示顺序：</td>
                        <td class="inputDiv"><input type="number" name="" min="1" max="100" id="dictionary_data_sort"></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="addDictionaryDataSubmit">确定</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<input type="hidden" id="baseUrl" data-url="${pageContext.request.contextPath}">
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/dictionary/js/dictionary_list.js"></script>
</html>