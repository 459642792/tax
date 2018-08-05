<%--
  Created by IntelliJ IDEA.
  User: libra
  Date: 2017/4/5
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.blueteam.base.constant.Constants" %>
<%--<script src="Scripts/VueExtend.js"></script>--%>

<div id="carriers_widget-grid">
    <div id="carriers_couponManagerContainer">

        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div class='jarviswidget jarviswidget-color-white jarviswidget-sortable'
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
                        <form id="search-form" class="smart-form" novalidate="novalidate">
                            <fieldset>
                                <div class="row">
                                    <section class="col col-4">
                                        <label class="label">负责区域:</label>
                                        <label class="input">
                                            <input type="text" placeholder="负责区域" v-model.trim="query.managerArea">
                                        </label>
                                    </section>

                                    <section class="col col-4">
                                        <label class="label">手机号:</label>
                                        <label class="input">
                                            <input type="text" maxlength="11" placeholder="手机号"
                                                   v-model.trim="query.mobilePhone">
                                        </label>
                                    </section>


                                </div>


                            </fieldset>

                            <footer>
                                <%--<a class="btn btn-primary atncol_c"  @click="search">查询</a>--%>
                                <a class="btn btn-primary atncol_c" @click="search">查询</a>
                                <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                   href="${pageContext.request.contextPath}/carriers/edit?id=0"
                                   data-target="#remoteModal" data-toggle="modal" data-backdrop='static'>新增</a>
                                <%--<a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a" href="${pageContext.request.contextPath}/carriers/edit?id=0" data-toggle="modal" data-backdrop='static' data-target="#remoteModal">新增1</a>--%>
                            </footer>
                        </form>
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
                            <span>运营商列表</span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic_carriersList" class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th data-class="expand" style="max-width:15%;text-align:center">运营商名</th>
                                        <th data-hide="tablet" style="width:14%;text-align:center">负责人</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">手机号</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">负责区域</th>
                                        <th style="width:5%">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="item in list">
                                        <td>{{item.name}}</td>
                                        <td>{{item.personName}}</td>
                                        <td>{{item.personPhone}}</td>
                                        <td>{{item.managerAreaName}}</td>
                                        <td>
                                            <a class='tableBtn hidden-mobile'
                                               v-bind:href="'${pageContext.request.contextPath}/carriers/edit?id='+ item.id"
                                               data-toggle='modal' data-backdrop='static' data-target='#remoteModal'>
                                                <img class='hidden-md hidden-sm hidden-xs'
                                                     src='Content/Home/image/xiugai.png'/>编辑
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
                    <div class="modal-content" style="width:800px; margin-left:-100px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    loadExtendJs(function () {
        var table = new ListTable("#carriers_couponManagerContainer", {
            listEl: "#dt_basic_carriersList",
            ajax: {url: "${pageContext.request.contextPath}/api/user/carriersList", method: "POST"}
        });
        table.vm.query.usertypes = 1;
        table.vm.query.managerArea = "";
        table.vm.query.mobilePhone = "";
        table.reload();
        table.render();
        //页面初始化调用
        pageSetUp();
    });


</script>