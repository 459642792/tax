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
                            <span>查询条件</span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <form id="search-form" class="smart-form" novalidate="novalidate">
                                <fieldset>
                                    <div class="row" style="margin-top:-22px">
                                        <section class="col col-4">
                                            <label class="label">商品名称</label>
                                            <label class="input">
                                                <input type="text" id="productName" name="productName" maxlength="30"
                                                       placeholder="请输入标题,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-4">
                                            <label class="label">品牌</label>
                                            <label class="input">
                                                <input type="text" id="brande" name="brande" maxlength="30"
                                                       placeholder="请输入类型,支持模糊查找">
                                            </label>
                                        </section>
                                        <section class="col col-4">
                                            <label class="label">状态</label>
                                            <label class="input">
                                                <select id="productStatus" name="productStatus" class="form-control">
                                                    <option selected="selected" value=0>全部</option>
                                                    <option value=1>上架</option>
                                                    <option value=2>下架</option>
                                                </select>
                                            </label>
                                        </section>
                                    </div>
                                </fieldset>
                                <footer>
                                    <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                    <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                       href="${pageContext.request.contextPath}/discover/AddorEditdiscover"
                                       data-toggle="modal" data-backdrop='static' data-target="#remoteModal">新增商品</a>
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
                                <table id="dt_basic" class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th data-class="expand"></th>
                                        <th data-class="expand">图片</th>
                                        <th data-class="expand">商品名称</th>
                                        <th data-class="expand">品牌</th>
                                        <th data-class="expand">酒币</th>
                                        <th data-class="expand">兑换记录（笔）</th>
                                        <th data-class="expand">状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
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
        </div>
    </div>
</div>
<script type="text/javascript">
    //页面初始化调用
    pageSetUp();
    var pageTable = {
        dom: $('#dt_basic'),//table节点
        ajaxUrl: "${pageContext.request.contextPath}/discover/discoverList",//ajax请求地址

        httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["id", 'Title', 'Type', 'Label', 'IsUser', 'Status', 'Visits', 'updateDate'],//table要显示的列
        primaryKey: "id",
        diyColumn: [
            {
                /*状态转换*/
                aTargets: [6],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full) {
                    var statusVal;
                    switch (full.Status) {
                        case 1:
                            statusVal = "待审核";
                            break;
                        case 2:
                            statusVal = "已通过";
                            break;
                        case 3:
                            statusVal = "已拒绝";
                            break;
                        default:
                            break;
                    }
                    return statusVal;
                }
            },
            {
                /*日前格式化*/
                aTargets: [8],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full) {
                    var newDate = new Date(full.updateDate);
                    return newDate.getFullYear() + "-" + ((newDate.getMonth() + 1) > 10 ? (newDate.getMonth() + 1) : "0" + (newDate.getMonth() + 1)) + "-" + (newDate.getDate() > 10 ? newDate.getDate() : "0" + newDate.getDate()) + " " + newDate.getHours() + ":" + (newDate.getMinutes() > 10 ? newDate.getMinutes() : "0" + newDate.getMinutes());
                }
            },
            {
                /*操作按钮组*/
                aTargets: [9],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full) {
                    var links = [];
                    switch (full.Status) {
                        case 1:
                            links.push("<a class='tableBtn hidden-mobile' href='${pageContext.request.contextPath}/pinpaiMain/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/编辑</a>");
                            links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0)' onclick='defineField(" + full.id + ")' data-toggle='modal' data-logout-msg='sss' data-backdrop='static' data-target='#remoteModal'>审核</a>");
                            break;
                        case 2:
                            links.push("<a class='tableBtn hidden-mobile' href='${pageContext.request.contextPath}/pinpaiMain/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/编辑</a>");
                            break;
                        case 3:
                            links.push("<a class='tableBtn hidden-mobile' href='${pageContext.request.contextPath}/pinpaiMain/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />查看/编辑</a>");
                            break;
                        default:
                            break;
                    }
                    if (full.isShow == 'Y') {
                        links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0)' data-toggle='modal' >隐藏</a>");
                    } else {
                        links.push("<a class='tableBtn hidden-mobile' href='javascript:void(0)' data-toggle='modal' >显示</a>");
                    }
                    return links.join(" ");
                }
            }

        ],
        ajxaParam: function () {
            return {
                Type: $('#discoverType').val(),
                Status: $("#discoverStatus option:selected").val(),
                Title: $('#discoverTitle').val(),
                IsUser: $('#discoverUser').val()
            };
        },
    };
    (function () {
        $("#btnSearch").click(function () {
            if (pageTable) {
                pageTable.container.fnDraw();
            }
        });
        window.initPageTab(pageTable);
    })()
</script>
</body>
</html>
