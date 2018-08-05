<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<body>
<div id="widget-grid">
    <div id="listContainer">
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class='jarviswidget jarviswidget-color-white jarviswidget-sortable' id="wid-id-search"
                     data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                     style="position: relative; opacity: 1; margin-bottom:10px;"
                     data-widget-collapsed='@(HttpUtil.IsFromMobile(Request)?"true":"false")'>
                    <header role="heading">
                        <div class="sb">
                            <span>查询条件  </span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <form id="search-form" class="smart-form" novalidate="novalidate">
                                <fieldset>
                                    <div class="row" style="margin-top:-22px">
                                        <section class="col col-3">
                                            <label class="label">标题</label>
                                            <label class="input">
                                                <input type="text" id="discoverTitle" name="discoverTitle"
                                                       maxlength="30" placeholder="请输入标题,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-3">
                                            <label class="label">类型</label>
                                            <label class="input">
                                                <select style="height: 30px;" id="discoverType" name="discoverStatus"
                                                        class="form-control">
                                                    <option selected="selected" value="">全部</option>
                                                    <option value=1>喜宴精选</option>
                                                    <option value=2>聚抢五折</option>
                                                    <option value=3>海外购</option>
                                                    <option value=4>品鉴招商</option>
                                                </select>
                                            </label>
                                        </section>

                                        <c:if test="${Power=='admin'}">
                                            <section class="col col-3">
                                                <label class="label">发布人</label>
                                                <label class="input">
                                                    <input type="text" id="discoverUser" name="discoverUser"
                                                           maxlength="30" placeholder="请输入发布人,支持模糊查找">
                                                </label>
                                            </section>

                                        </c:if>
                                        <section class="col col-3">
                                            <label class="label">状态</label>
                                            <label class="input">
                                                <select style="height: 30px;" id="discoverStatus" name="discoverStatus"
                                                        class="form-control">
                                                    <option selected="selected" value=0>全部</option>
                                                    <option value=1>待审核</option>
                                                    <option value=2>已通过</option>
                                                    <option value=3>未通过</option>
                                                </select>
                                            </label>
                                        </section>
                                    </div>
                                </fieldset>
                                <footer>
                                    <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                    <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                       href="${pageContext.request.contextPath}/discover/AddOrEdit?Id=0"
                                       data-toggle="modal" data-backdrop='static' data-target="#remoteModal">新增</a>
                                </footer>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class="jarviswidget jarviswidget-color-darken jarviswidget-sortable" id="wid-id-list"
                     data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                     style="position: relative; opacity: 1;">
                    <header role="heading">
                        <div class="sb">
                            <span>发现列表 </span>
                        </div>
                    </header>
                    <div role="content" style="border:1px">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wrapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%"
                                       style="text-align: center;table-layout:fixed;">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th style="text-align: center;" data-class="expand" width="5%"></th>
                                        <th style="text-align: center;" data-class="expand" width="15%">标题</th>
                                        <th style="text-align: center;" data-class="expand" width="10%">类型</th>
                                        <th style="text-align: center;" data-class="expand" width="10%">标签</th>
                                        <th style="text-align: center;" data-class="expand" width="10%">发布人</th>
                                        <th style="text-align: center;" data-class="expand" width="5%">状态</th>
                                        <th style="text-align: center;" data-class="expand" width="5%">浏览量</th>
                                        <th style="text-align: center;" data-class="expand" width="10%">发布时间</th>
                                        <th style="text-align: center;" width="20%;">操作</th>
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
<div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
         <div class="modal-content" style="width:800px; margin-left:-100px;">
         </div>
    </div>
</div>
<div class="modal fade" id="groomModal" tabindex="-1" role="dialog" aria-labelledby="groomModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">推荐管理</h4>
            </div>
            <div class="modal-body">
                <p>
                    正在推荐中的内容已达到3个，请<span style="color: red">重新勾选两个及以下推荐的内容。</span>
                </p>
                <table id="groomTable" class="table table-bordered" style="table-layout: fixed">
                    <thead>
                    <tr>
                        <th style="text-align: center" width="50px"></th>
                        <th style="text-align: center">活动名</th>
                        <th style="text-align: center" width="100px">类型</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="reSubGroom" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="subGroom">确定</button>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="baseUrl" data-admin="${Power}" data-url="${pageContext.request.contextPath}">
<script type="text/javascript" src="discoverManage/js/discoverList.js"></script>
</body>
</html>
