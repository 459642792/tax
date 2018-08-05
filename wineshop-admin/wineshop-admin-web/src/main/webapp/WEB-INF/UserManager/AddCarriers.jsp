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
    <h4 class="modal-title">新增运营商</h4>
</div>
<div class="modal-body no-padding" id="formCarriers">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">负责人</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model="model.personName" name="title" class="input" maxlength="15"
                                   placeholder="负责人姓名"/>
                            <b class="tooltip tooltip-top-right">请输入负责人姓名</b>
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
                            <input type="text" v-model="model.personPhone" name="title" class="input" maxlength="11"
                                   placeholder="请输入手机号"/>
                            <b class="tooltip tooltip-top-right">请输入负责人手机</b>
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">负责区域</label>
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
        debugger
        var lm = new EditPage("#formCarriers", {
            getAjax: {
                url: '${pageContext.request.contextPath}/api/carriers/get?id=0',
                method: 'GET'
            },
            editAjax: {
                url: '${pageContext.request.contextPath}/api/carriers/post',
                method: 'POST'
            },
            exclude: ["city"],
            beforeBinding: function () {
                lm.model = {personName: '', personPhone: ''};
                lm.vm.model = lm.model;
                lm.vm.area = this.vm.model.city ? this.vm.model.city : CommonBase.getCityInfo("");
                // lm.vm.model.area = this.vm.model.city?this.vm.model.city:CommonBase.getCityInfo("");
            },
            beginPost: function (data) {
                if (!CommonBase.checkMobile(data.personPhone)) {
                    simpleNotify("请输入正确的负责人电话");
                    return false;
                }
                if ($.trim(data.personName) === "") {
                    simpleNotify("请输入负责人姓名");
                    return false;
                }
                if (this.vm.area.provinceCode === "" || this.vm.area.cityCode === "") {
                    simpleNotify("请选择管辖区域");
                    return false;
                }
                if ($("#county option").length > 1 && this.vm.area.districtCode === "") {
                    simpleNotify("请选择管辖区域");
                    return false;
                }
                data.citycode = this.vm.area.districtCode === "" ? this.vm.area.cityCode : this.vm.area.districtCode;
                data.managementarea = data.citycode;
                var name = $("#province option:selected").text() + $("#city  option:selected").text();
                name = this.vm.area.districtCode === "" ? name : name + $("#county  option:selected").text();
                name += "运营商";
                data.name = name;
                return true;
            }
        });
        lm.init();
    })();
</script>
</body>
</html>