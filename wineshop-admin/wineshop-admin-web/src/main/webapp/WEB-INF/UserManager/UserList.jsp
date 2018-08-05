<%--
  Created by IntelliJ IDEA.
  User: libra
  Date: 2017/4/5
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%--<script src="Scripts/VueExtend.js"></script>--%>

<div id="user_widget-grid">
    <div id="user_couponManagerContainer">

        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class="jarviswidget jarviswidget-color-darken jarviswidget-sortable" id="wid-id-list"
                     data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-colorbutton="false"
                     style="position: relative; opacity: 1;">
                    <header role="heading">
                        <div class="sb">
                            <span>普通用户</span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic_userList" class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th data-class="expand" style="max-width:15%;text-align:center">昵称</th>
                                        <th data-hide="tablet" style="width:14%;text-align:center">最后登录时间</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center"
                                            order="{field:'orderCount'}">交易次数
                                        </th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center"
                                            order="{field:'orderAmount'}">交易金额
                                        </th>
                                        <th style="width:10%;">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="item in list">
                                        <td>{{item.nickname}}</td>
                                        <td>{{item.logintime === null ? "" : new Date(item.logintime).format('yyyy-MM-dd hh:mm')}}
                                        </td>
                                        <td>{{item.orderCount}}</td>
                                        <td>{{item.orderAmount}}</td>
                                        <td>
                                            <a class='tableBtn hidden-mobile'
                                               v-bind:href="'${pageContext.request.contextPath}/user/userInfo?id='+ item.id"
                                               data-toggle='modal' data-backdrop='static' data-target='#remoteModal'>
                                                <img class='hidden-md hidden-sm hidden-xs'
                                                     src='Content/Home/image/chakan.png'/>查看
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="remoteModal" role="dialog" tabindex="-1" aria-labelledby="remoteModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="width:auto; margin-left:-100px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>


<script>

    loadExtendJs(function () {
        var table = new ListTable("#user_couponManagerContainer", {
            listEl: "#dt_basic_userList",
            ajax: {url: "${pageContext.request.contextPath}/api/user/userList", method: "POST"}
        });
        table.vm.query.usertypes = 4;

        table.reload();
        table.render();
        //页面初始化调用
        pageSetUp();
    });

</script>
