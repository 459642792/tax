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
    <h4 class="modal-title">新增店铺</h4>
</div>
<div class="modal-body no-padding" id="formCarriers">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">店铺名称</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model="model.name" name="title" class="input" maxlength="20"
                                   placeholder="店铺名称"/>
                            <b class="tooltip tooltip-top-right">请输入店铺名称</b>
                        </label>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">联系人</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model="model.contactPerson" name="title" class="input" maxlength="15"
                                   placeholder="联系人姓名"/>
                            <b class="tooltip tooltip-top-right">请输入联系人姓名</b>
                        </label>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">手机号</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model="model.telephone" name="title" class="input" maxlength="11"
                                   placeholder="联系人手机号"/>
                            <b class="tooltip tooltip-top-right">请输入联系人手机号</b>
                        </label>
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">店铺地址</label>
                    <div class="col col-10">
                        <select class="input-sm" id="province" name="Province" style="width:200px" v-combox="_run('area.provinceCode',{
                url:'<%=Constants.APIS_WEBSITE%>/city/getProvinceList',
                method:'GET',
                defaultOption:{label:'全国',value:''},
                data:{},
                label:'name',
                value:'code'
            })">
                        </select>
                        <select class="input-sm Town" id="city" name="city" style="width:200px" v-combox="_run('area.cityCode',
                                               {
                url:'<%=Constants.APIS_WEBSITE%>/city/getCityByParent',
                method:'GET',
                data:{},
                defaultOption:{label:'全省',value:''},
                label:'name',
                value:'code',
                preLoad:false,
                deps:{
                    'area.provinceCode':{
                        paramter:'parent'
                    }
                }
            })">
                        </select>
                        <select class="input-sm Town" id="county" name="county" style="width:220px" v-combox="_run('area.districtCode',
                                               {
                url:'<%=Constants.APIS_WEBSITE%>/city/getCityByParent',
                method:'GET',
                defaultOption:{label:'全市',value:''},
                data:{},
                label:'name',
                value:'code',
                preLoad:false,
                deps:{
                    'area.cityCode':{
                        paramter:'parent'
                    }
                }
            })">
                        </select>


                    </div>
                </div>

            </section>

            <section>
                <div class="row">
                    <label class="label col col-2"></label>

                    <div class="col col-10" style="margin-left: 0px;">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model="model.addr" name="title" class="input" maxlength="40"
                                   placeholder="店铺详细地址"/>
                            <b class="tooltip tooltip-top-right">请输入店铺详细地址</b>
                        </label>
                    </div>
                </div>


            </section>


            <div class="row">
                <label class="label col col-2">店招图片</label>
                <div class="col col-10">
                    <!--图片提示信息，可不要-->
                    <div class="alert alert-info alert-block" style="margin-bottom:0px;">
                        <a class="close" data-dismiss="alert" href="javascript:;">×</a>
                        请上传1张店招图片。
                    </div>
                    <div v-img="_run('model.image',{
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

        var lm = new EditPage("#formCarriers", {
            getAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/get?id=0',
                method: 'GET'
            },
            editAjax: {
                url: '${pageContext.request.contextPath}/api/vendor/post',
                method: 'POST'
            },
            include: ["name", "contactPerson", "addr", "cityCode", "image", "telephone"],
            // exclude:["city","orderCount","orderAmount","zonghe","outScore","quyuName","quyuStatus","tradingArea","qjImage","legalPerson"],
            beforeBinding: function () {
//                lm.model = {personName:'',personPhone:''};
//                lm.vm.model = lm.model;
                lm.vm.area = this.vm.model.city ? this.vm.model.city : CommonBase.getCityInfo("");
                // lm.vm.model.area = this.vm.model.city?this.vm.model.city:CommonBase.getCityInfo("");
            },
            beginPost: function (data) {
                if ($.trim(data.name) === "") {
                    simpleNotify("请输入店铺名称");
                    return false;
                }

                if (!CommonBase.checkMobile(data.telephone)) {
                    simpleNotify("请输入正确的联系人电话");
                    return false;
                }
                if ($.trim(data.contactPerson) === "") {
                    simpleNotify("请输入负责人姓名");
                    return false;
                }
                if (this.vm.area.provinceCode === "" || this.vm.area.cityCode === "") {
                    simpleNotify("请选择店铺地址");
                    return false;
                }
                if ($("#county option").length > 1 && this.vm.area.districtCode === "") {
                    simpleNotify("请选择店铺地址");
                    return false;
                }

                if ($.trim(data.addr) === "") {
                    simpleNotify("请输入店铺详细地址");
                    return false;
                }

                if (data.image === "") {
                    simpleNotify("请上传店招图片");
                    return false;
                }

                data.cityCode = this.vm.area.districtCode === "" ? this.vm.area.cityCode : this.vm.area.districtCode;
                return true;
            }
        });
        lm.init();
    })();
</script>
</body>
</html>