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
<%@ page import="com.blueteam.base.constant.Constants" %>
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
    <h4 class="modal-title">认证店铺</h4>
</div>
<div class="modal-body no-padding" id="formAuthVendors">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">认证店铺</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        {{model.name}}
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">法人姓名</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model.trim="model.legalPerson" name="title" class="input"
                                   maxlength="15" placeholder="法人人姓名"/>
                            <b class="tooltip tooltip-top-right">请输入法人姓名</b>
                        </label>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">法人身份证号</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model.trim="model.legalPersonIdCard" name="title" class="input"
                                   maxlength="18" minlength="15" placeholder="法人身份证号"/>
                            <b class="tooltip tooltip-top-right">请输入法人身份证号</b>
                        </label>
                    </div>
                </div>
            </section>


            <section>
                <div class="row">
                    <label class="label col col-2">手持身份证照</label>
                    <div class="col col-10">


                        <!--图片提示信息，可不要-->
                        <div class="alert alert-info alert-block" style="margin-bottom:0px;">
                            <a class="close" data-dismiss="alert" href="javascript:;">×</a>
                            请上传1张手持身份证。
                        </div>
                        <div v-img="_run('model.idInHandImage',{
                                            url:'<%=Constants.IMAGE_UPLOAD_URL%>?type=fjjh_vendor',
                                            method: 'POST',
                                            maxFiles: 1
                                        })" class="dropzone">
                            <div class="dz-message"
                                 style="font-size: 18px; font-weight: bold; text-align: center; margin-top: 10px; color: #B5B5B5;">
                                点我选图片，或把图片拖到这里<br/>
                                <i class="fa fa-upload" style="font-size: 53px;margin-top: 10px;"></i>
                            </div>
                        </div>


                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">营业执照</label>
                    <div class="col col-10">

                        <!--图片提示信息，可不要-->
                        <div class="alert alert-info alert-block" style="margin-bottom:0px;">
                            <a class="close" data-dismiss="alert" href="javascript:;">×</a>
                            请上传1张营业执照。
                        </div>
                        <div v-img="_run('model.businessLicenseImg',{
                                            url:'<%=Constants.IMAGE_UPLOAD_URL%>?type=fjjh_vendor',
                                            method: 'POST',
                                            maxFiles: 1
                                        })" class="dropzone">
                            <div class="dz-message"
                                 style="font-size: 18px; font-weight: bold; text-align: center; margin-top: 10px; color: #B5B5B5;">
                                点我选图片，或把图片拖到这里<br/>
                                <i class="fa fa-upload" style="font-size: 53px;margin-top: 10px;"></i>
                            </div>
                        </div>


                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">特许证件</label>
                    <div class="col col-10">

                        <!--图片提示信息，可不要-->
                        <div class="alert alert-info alert-block" style="margin-bottom:0px;">
                            <a class="close" data-dismiss="alert" href="javascript:;">×</a>
                            请上传1张特许证件。
                        </div>
                        <div v-img="_run('model.licenseFileImg',{
                                            url:'<%=Constants.IMAGE_UPLOAD_URL%>?type=fjjh_vendor',
                                            method: 'POST',
                                            maxFiles: 1
                                        })" class="dropzone">
                            <div class="dz-message"
                                 style="font-size: 18px; font-weight: bold; text-align: center; margin-top: 10px; color: #B5B5B5;">
                                点我选图片，或把图片拖到这里<br/>
                                <i class="fa fa-upload" style="font-size: 53px;margin-top: 10px;"></i>
                            </div>
                        </div>

                    </div>
                </div>
            </section>

        </fieldset>
        <footer>
            <button class="btn btn-primary" @click.prevent="post">
                <i class="fa fa-save"></i>
                保存
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
                取消
            </button>
        </footer>
    </form>
</div>


<script type="text/javascript">
    (function () {

        var lm = new EditPage("#formAuthVendors", {
            getAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/get?id=${id}',
                method: 'GET'
            },
            editAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/adminAuthenticateVendor',
                method: 'POST'
            },
            include: ["legalPerson", "id", "idInHandImage", "businessLicenseImg", "licenseFileImg", "legalPersonIdCard"],
            beforeBinding: function () {

            },
            beginPost: function (data) {
                if (data.legalPerson === "") {
                    simpleNotify("请输入法人姓名");
                    return false;
                }

                if (data.legalPersonIdCard === "" || !CommonBase.checkIdCard(data.legalPersonIdCard)) {
                    simpleNotify("请输入正确的法人身份证号码");
                    return false;
                }
                if ($.trim(data.idInHandImage) === "") {
                    simpleNotify("请上传法人手持身份证照片");
                    return false;
                }
                if ($.trim(data.businessLicenseImg) === "") {
                    simpleNotify("请上传营业执照图片");
                    return false;
                }
                if ($.trim(data.licenseFileImg) === "") {
                    simpleNotify("请上传特许证件");
                    return false;
                }
                return true;
            }
        });
        lm.init();
    })();
</script>
</body>
</html>