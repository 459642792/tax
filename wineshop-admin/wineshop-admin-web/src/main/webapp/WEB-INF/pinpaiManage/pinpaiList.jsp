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
                                        <section class="col col-3">
                                            <label class="label">品牌</label>
                                            <label class="input">
                                                <input type="text" id="Names" name="Names" maxlength="30"
                                                       placeholder="请输入品牌,支持模糊查找">
                                            </label>
                                        </section>
                                    </div>
                                </fieldset>
                                <footer>
                                    <a class="btn btn-primary atncol_c" id="btnSearch">查询</a>
                                    <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                       href="${pageContext.request.contextPath}/pinpaiMain/AddOrEdit?Id=0"
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
                            <span>品牌列表 </span>
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
                                        <th data-class="expand">品牌</th>
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
        ajaxUrl: "${pageContext.request.contextPath}/pinpaiMain/pageMain",//ajax请求地址
        httpMethod: 'Get',//接口的请求方式方法分为get请求和post请求
        aoColumns: ["name"],//table要显示的列
        primaryKey: "id",
        diyColumn: [
            {
                aTargets: [2],//要显示的位置
                mData: "id",//主键
                mRender: function (data, type, full) {
                    var links = [];
                    links.push("<a class='tableBtn hidden-mobile' href='${pageContext.request.contextPath}/pinpaiMain/AddOrEdit?Id=" + full.id + "' data-toggle='modal' data-backdrop='static' data-target='#remoteModal'><img class='hidden-md hidden-sm hidden-xs' src='Content/Home/image/xiugai.png' />修改</a>");
                    return links.join(" ");
                }
            }
        ],
        ajxaParam: function () {
            return {
                Name: $.trim($('#Names').val())
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
