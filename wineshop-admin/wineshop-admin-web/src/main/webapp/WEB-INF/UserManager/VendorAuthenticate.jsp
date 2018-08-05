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
<html>
<head>
    <base href="<%=basePath%>">
    <jsp:include page="${pageContext.request.contextPath}/index"></jsp:include>
</head>
<body>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title">审核店铺</h4>
</div>
<div class="modal-body no-padding" id="formAdminAuthVendors">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">店铺名称</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        {{model.name}}
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">法人姓名</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        <label class="input">
                            {{model.legalPerson}}
                        </label>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">法人身份证</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        <label class="input">
                            {{model.legalPersonIdCard}}
                        </label>
                    </div>
                </div>
            </section>


            <section>
                <div class="row">
                    <label class="label col col-2">手持身份证照</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        <img style="width:300px;height: 300px;" :src="model.idInHandImage"/>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">营业执照</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        <img style="width:300px;height: 300px;" :src="model.businessLicenseImg"/>

                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">特许证件</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        <img style="width:300px;height: 300px;" :src="model.licenseFileImg"/>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">拒绝理由</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model.trim="model.reason" class="input" maxlength="100"
                                   placeholder="拒绝理由"/>
                            <b class="tooltip tooltip-top-right">请输入拒绝理由</b>
                        </label>
                    </div>
                </div>
            </section>

        </fieldset>
        <footer>
            <button class="btn btn-danger" @click.prevent="post($event,30)">
                <i class="fa fa-save"></i>
                拒绝
            </button>
            <button class="btn btn-primary" @click.prevent="post($event,20)">
                <i class="fa fa-save"></i>
                通过
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
                取消
            </button>
        </footer>
    </form>
</div>


<script type="text/javascript">
    (function () {

        var lm = new EditPage("#formAdminAuthVendors", {
            getAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/get?id=${id}',
                method: 'GET'
            },
            editAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/adminAuthenticate',
                method: 'POST'
            },
            include: ["vendorId", "reason"],
            beforeBinding: function () {
                // this.vm.model.authenticationReason = "";
                this.vm.model.reason = "";
                this.vm.model.vendorId = this.vm.model.id;
            },
            beginPost: function (data, ev, status) {
                if (!confirm("您确定要" + (status == 30 ? "拒绝" : "通过") + "?")) {
                    return false;
                }

                if (status === 30 && data.reason === "") {
                    simpleNotify("请输入拒绝理由");
                    return false;
                }

                data.authStatus = status;


                return true;
            }
        });
        lm.init();
    })();
</script>
</body>
</html>