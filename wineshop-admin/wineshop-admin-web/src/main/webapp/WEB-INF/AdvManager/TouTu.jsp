<%--
  Created by IntelliJ IDEA.
  User: libra
  Date: 2017/4/5
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<script src="Scripts/VueExtend.js"></script>--%>

<div id="advs_widget-grid">
    <div id="couponManagerContainer">
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
                                        <label class="label">显示地区:</label>
                                        <label class="input">

                                            <input type="text" placeholder="显示地区" v-model="query.cityName">
                                        </label>
                                    </section>


                                </div>


                            </fieldset>

                            <footer>
                                <a class="btn btn-primary atncol_c" @click="search">查询</a>
                                <a class="btn btn-success header-btn hidden-mobile fa fa-plus xxx atncol_a"
                                   href="${pageContext.request.contextPath}/adv/edit?id=0&typeCode=${typeCode}"
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
                            <span>首页头图列表</span>
                        </div>
                    </header>
                    <div role="content">
                        <div class="widget-body no-padding">
                            <div id="dt_basic_wapper" class="dataTables_wrapper form-inline no-footer"
                                 style="position:relative;">
                                <table id="dt_basic_advList" class="table table-striped table-bordered table-hover"
                                       width="100%">
                                    <thead>
                                    <tr>
                                        <th data-class="expand" style="max-width:15%;text-align:center">广告图</th>
                                        <th data-hide="tablet" style="width:14%;text-align:center">发布人</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">显示地区</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">发布时间</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">链接店铺</th>
                                        <th data-hide="phone,tablet" style="width:12%;text-align:center">点击量</th>
                                        <th style="width:5%;">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="item in list">
                                        <td>
                                            <img :src="(item.img==''||item.img==null)?'Content/Home/image/no-banner.jpg':item.img"
                                                 style="width:100px;height:60px;"/>
                                        </td>
                                        <td>{{item.creator}}</td>
                                        <td style="text-align: center">{{item.city?item.city.fullName:"全国"}}</td>
                                        <td style="text-align: right;">{{new Date(item.createDate).format('yyyy-MM-dd hh:mm')}}
                                        </td>
                                        <td>{{(item.foreignName == null || item.foreignName == "")? "-":item.foreignName
                                            }}
                                        </td>
                                        <td>{{item.clickCount }}</td>
                                        <td>
                                            <a class='tableBtn hidden-mobile'
                                               v-bind:href="'${pageContext.request.contextPath}/adv/edit?typeCode=${typeCode}&id='+ item.id"
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
        var table = new ListTable("#advs_widget-grid", {
            listEl: "#dt_basic_advList",
            ajax: {url: "${pageContext.request.contextPath}/api/adv/advList", method: "POST"}
        });
        table.vm.query.typeCode = '${typeCode}';
        table.vm.query.cityName = "";
        table.reload();
        table.render();
        pageSetUp();
    });





</script>