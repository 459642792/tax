<%--
  Created by IntelliJ IDEA.
  User: libra
  Date: 2017/4/5
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<script src="Scripts/VueExtend.js"></script>--%>

<div id="vendor_widget-grid">
    <div id="vendorContainer">
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
                                    <section class="col col-3">
                                        <label class="label">店铺名:</label>
                                        <label class="input">

                                            <input type="text" placeholder="店铺名" v-model="query.name"
                                                   @keyup.enter="search">
                                        </label>
                                    </section>

                                    <section class="col col-3">
                                        <label class="label">地区:</label>
                                        <label class="input">
                                            <input type="text" placeholder="地区" v-model="query.cityName"
                                                   @keyup.enter="search">

                                        </label>
                                    </section>

                                    <section class="col col-3">
                                        <label class="label">入驻时间:</label>
                                        <label class="input">
                                            <i class="icon-append fa fa-calendar"></i>
                                            <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                   placeholder="开始时间" v-date="_run('query.rz_begin')"/>
                                        </label>
                                    </section>
                                    <section class="col col-3">
                                        <label class="label">&nbsp;</label>
                                        <label class="input">
                                            <i class="icon-append fa fa-calendar"></i>
                                            <input type="text" class="form-control" data-date-format="yyyy-mm-dd"
                                                   placeholder="结束时间" v-date="_run('query.rz_end')">
                                        </label>
                                    </section>
                                </div>

                                <div class="row">
                                    <section class="col col-3">
                                        <label class="label">主营品牌:</label>
                                        <label class="input">

                                            <input type="text" placeholder="主营品牌" v-model="query.pinpai"
                                                   @keyup.enter="search">
                                        </label>
                                    </section>

                                    <section class="col col-3">
                                        <label class="label">认证状态:</label>
                                        <label class="select">
                                            <select name="enableFlag" v-combox="_run('query.authenticationStatus')">
                                                <option value="0">所有</option>
                                                <option value="10">待审核</option>
                                                <option value="20">已认证</option>
                                                <option value="100">未认证</option>
                                            </select>
                                        </label>
                                    </section>


                                </div>
                            </fieldset>

                            <footer>
                                <a class="btn btn-primary atncol_c" @click="search">查询</a>
                                <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                   href="${pageContext.request.contextPath}/vendor/addOrEditVendor?id=0"
                                   data-toggle="modal" data-backdrop='static' data-target="#remoteModal">新增</a>
                                <%--<a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a" href="${pageContext.request.contextPath}/couponMain/AddOrEdit?Id=0" data-toggle="modal" data-backdrop='static' data-target="#remoteModal">新增</a>--%>
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
                            <span>商家列表</span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic_vendorList" class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th data-class="expand" style="max-width:15%;text-align:center">店铺名</th>
                                        <th data-hide="tablet" style="width:14%;text-align:center">地区</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">区域运营商</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">入驻时间</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">主营品牌</th>
                                        <th data-hide="phone,tablet" style="width:6%;text-align:center"
                                            order="{field:'orderCount'}">交易次数
                                        </th>
                                        <th data-hide="phone,tablet" style="width:6%;text-align:center"
                                            order="{field:'orderAmount'}">交易金额
                                        </th>
                                        <th data-hide="phone,tablet" style="width:6%;text-align:center">认证状态</th>
                                        <th data-hide="phone,tablet" style="width:10%;">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="item in list">
                                        <td>{{item.name}}</td>
                                        <td>{{item.city?item.city.fullName:""}}
                                        </td>
                                        <td>{{item.carriersName}}</td>
                                        <td>{{new Date(item.createDate).format("yyyy-MM-dd")}}</td>
                                        <td>{{item.pinpaiStr}}</td>
                                        <td>{{item.orderCount}}</td>
                                        <td>{{item.orderAmount}}</td>
                                        <td>{{
                                            item.authenticationStatusInt ===0?
                                            "未认证":item.authenticationStatusInt === 10 ?
                                            "待审核" : item.authenticationStatusInt === 20 ? "已认证" :
                                            item.authenticationStatusInt == null ? "未认证": "已拒绝"}}
                                        </td>
                                        <td>
                                            <a class='tableBtn hidden-mobile'
                                               v-bind:href="'${pageContext.request.contextPath}/vendor/vendorInfo?id='+ item.id"
                                               data-toggle='modal' data-backdrop='static' data-target='#remoteModal'>
                                                <img class='hidden-md hidden-sm hidden-xs'
                                                     src='Content/Home/image/chakan.png'/>查看
                                            </a>

                                            <a v-if="item.authenticationStatusInt === 0 || item.authenticationStatusInt === null"
                                               class='tableBtn hidden-mobile'
                                               v-bind:href="'${pageContext.request.contextPath}/vendor/auth?id='+ item.id"
                                               data-toggle='modal' data-backdrop='static' data-target='#remoteModal'>
                                                <img class='hidden-md hidden-sm hidden-xs'
                                                     src='Content/Home/image/xiugai.png'/>认证
                                            </a>

                                            <a v-if="item.authenticationStatusInt === 10" class='tableBtn hidden-mobile'
                                               v-bind:href="'${pageContext.request.contextPath}/vendor/review?id='+ item.id"
                                               data-toggle='modal' data-backdrop='static' data-target='#remoteModal'>
                                                <img class='hidden-md hidden-sm hidden-xs'
                                                     src='Content/Home/image/shenhe.png'/>审核
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
                    <div class="modal-content" style="max-width:1000px; margin-left:-100px;"></div>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    loadExtendJs(function () {
        var table = new ListTable("#vendor_widget-grid", {
            listEl: "#dt_basic_vendorList",
            ajax: {url: "${pageContext.request.contextPath}/api/user/vendorList", method: "POST"}
        });
        table.vm.query.usertypes = 2;
        table.vm.query.name = "";
        table.vm.query.cityName = "";
        table.vm.query.pinpai = "";
        table.vm.query.authenticationStatus = 0;
        table.vm.query.rz_begin = "";
        table.vm.query.rz_end = "";
        table.reload();
        table.render();
        pageSetUp();
    });

</script>