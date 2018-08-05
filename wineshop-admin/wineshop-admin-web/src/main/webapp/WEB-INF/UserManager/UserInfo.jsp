<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<style>
    .modal-content {
        width: 800px !important;
        margin: 0 auto !important;
    }

    .modal-dialog {
        width: auto !important;
    }

    .dt-toolbar-footer {
        width: 96.8% !important;
    }
</style>


<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title">用户信息</h4>
</div>
<div class="modal-body no-padding" id="userInfomain">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">微信昵称:</label>
                    <div class="col col-2" style="padding-top: 7px;">
                        <label class="input">
                            ${nikeName}
                        </label>
                    </div>
                    <label class="label col col-2">头像:</label>
                    <div class="col col-2">
                        <label class="input">
                            <img src="${headImage==''?'Content/themes/smart/img/default.png':headImage}"
                                 style="width:100px;height: 100px;"/>
                        </label>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div class="jarviswidget jarviswidget-color-darken jarviswidget-sortable">
                            <header role="heading">
                                <div class="sb">
                                    <span>交易记录</span>
                                </div>
                            </header>
                            <div role="content">
                                <div class="widget-body no-padding">
                                    <div id="dt_basic_wapper" class="dataTables_wrapper form-inline no-footer"
                                         style="position:relative;">
                                        <table id="dt_basic_consumeList"
                                               class="table table-striped table-bordered table-hover" width="96.8%">
                                            <thead>
                                            <tr>
                                                <th style="max-width:25%;text-align:center;width:15%;">交易时间</th>
                                                <th data-hide="tablet" style="width:20%;text-align:center">商家</th>
                                                <th data-hide="phone,tablet" style="width:15%;text-align:center">地区</th>
                                                <th data-hide="phone,tablet" style="width:7%;text-align:center">消费金额
                                                </th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr v-for="item in list">
                                                <td style="text-align: center">{{new
                                                    Date(item.paytime).format('yyyy-MM-dd hh:mm')}}
                                                </td>
                                                <td style="text-align: center">{{item.vendorName}}</td>
                                                <td style="text-align: center">{{item.city ==
                                                    null?'':item.city.fullName}}
                                                </td>
                                                <td style="text-align: center">{{item.discountamount}}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </section>
        </fieldset>

    </form>
</div>
<script>

    (function () {

        var table = new ListTable("#userInfomain", {
            listEl: "#dt_basic_consumeList",
            ajax: {url: "${pageContext.request.contextPath}/orderInfo/api/order/getUserOrders", method: "POST"}
        });
        table.vm.query.userId = ${userId};
        table.vm.model = {};
        table.reload();
        table.render();
    })();

</script>
