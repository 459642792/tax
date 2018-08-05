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
    <h4 class="modal-title">${title}</h4>
</div>
<div class="modal-body no-padding" id="formToutus">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">发布人</label>
                    <div class="col col-10" style="padding-top: 7px;">
                        ${postAdmin}
                    </div>
                </div>
            </section>

            <section>
                <div class="row">
                    <label class="label col col-2">显示地区</label>
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
                    <label class="label col col-2">链接</label>
                    <div class="col col-10">
                        <div class="radio radio-success radio-single">
                            <input type="radio" name="dplj" v-model="isLink" checked="true" value="false"
                                   aria-label="Single    radio One" style="left:20px;"/> 不链接<br/>
                            <input type="radio" name="dplj" v-model="isLink" value="true"
                                   aria-label="Single   radio Wne" style="left:20px;"/> 链接
                            <select v-bind:disabled="isLink === 'false' || isLink === false" class="input-sm Town"
                                    name="city" style="width:200px" v-combox="_run('model.foreignKey',
                                               {
                url:'${pageContext.request.contextPath}/api/vendor/getAreaVendors',
                method:'GET',
                data:{},
                defaultOption:{value:'',label:'无链接'},
                label:'name',
                value:'id',
                preLoad:true,
                deps:{
                    'area.provinceCode':{
                        paramter:'cityCode'
                    }
                    ,
                     'area.districtCode':{
                        paramter:'cityCode'
                    }
                    ,
                    'area.cityCode':{
                        paramter:'cityCode'
                    }
                }
            })"></select>
                        </div>
                    </div>

                </div>

            </section>


            <section>
                <div class="row">
                    <label class="label col col-2">广告图片</label>
                    <div class="col col-10">
                        <!--图片提示信息，可不要-->
                        <div class="alert alert-info alert-block" style="margin-bottom:0px;">
                            <a class="close" data-dismiss="alert" href="javascript:;">×</a>
                            请上传1张广告图片。
                        </div>
                        <div v-img="_run('model.img',{
                                            url:'<%=Constants.IMAGE_UPLOAD_URL%>?type=fjjh_adv',
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

        var lm = new EditPage("#formToutus", {
            getAjax: {
                url: '${pageContext.request.contextPath}/api/adv/get?id=${id}',
                method: 'GET'
            },
            editAjax: {
                url: '${pageContext.request.contextPath}/api/adv/post',
                method: 'POST'
            },
            include: ["foreignKey", "cityCode", "img", "id", "typeCode"],
            beforeBinding: function () {
                lm.vm.area = this.vm.model.city ? this.vm.model.city : CommonBase.getCityInfo("");
                lm.vm.isLink = CommonBase.getTypeDefault(lm.vm.model.foreignKey, '') !== "";
            },
            beginPost: function (data) {
                debugger
                if ($.trim(data.img) === "") {
                    simpleNotify("请上传广告图片");
                    return false;
                }
                if (lm.vm.isLink === 'false' || this.vm.isLink === false)
                    data.foreignKey = "";

                data.cityCode = this.vm.area.districtCode === "" ? this.vm.area.cityCode : this.vm.area.districtCode;
                data.cityCode = data.cityCode === "" ? this.vm.area.provinceCode : data.cityCode;
                data.typeCode = '${typeCode}';
                return true;
            }
        });
        lm.init();
    })();
</script>
</body>
</html>