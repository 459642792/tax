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
    <style>
        .apkEditor {
            position: relative;
            opacity: 1;
            background-color: #ffffff;
            padding: 10px;
            margin-top: 10px;
        }

        .apkTitle {
            border-bottom: 1px solid #EAEAEA;
            padding: 0 0 10px 0;
            color: #666;
            font-family: '微软雅黑';
        }

        .apkForm {
            padding: 20px 0;
        }
    </style>
</head>
<body>
<div id="elApp">
    <div class="apkEditor">
        <div class="apkTitle">Apk设置</div>
        <form class="smart-form client-form apkForm">
            <section>
                <label class="label">Apk名称</label>
                <label class="input">
                    <input id="apkName" name="names" type="text" v-model="data.apk_name" maxlength="15">
                </label>
            </section>
            <section>
                <label class="label">Apk描述</label>
                <label class="input">
                    <input id="apkDetail" name="names" v-model="data.apk_des" type="text" maxlength="15">
                </label>
            </section>
            <section>
                <label class="label">Apk路径</label>
                <label class="input">
                    <input id="apkUrl" name="names" type="text" v-model="data.apk_url" maxlength="15">
                </label>
            </section>
            <section>
                <label class="label">Apk版本号</label>
                <label class="input">
                    <input id="apkVersion" name="names" type="text" v-model="data.apk_version" maxlength="15">
                </label>
            </section>
            <section>
                <footer>
                    <a class="btn btn-success header-btn fa fa-plus xxx atncol_a" @click="addOrEditApk(0)" v-if="state"
                       href="javascript:void(0)">新增</a>
                    <a class="btn btn-primary atncol_c" id="btnSearch" @click="addOrEditApk(data.apk_id)" v-else>保存</a>
                </footer>
            </section>
        </form>
    </div>
</div>
</body>
<script>
    (function () {
        new Vue({
            el: "#elApp",
            data: {
                url: '${pageContext.request.contextPath}',
                state: false,
                data: {apk_name: '', apk_des: '', apk_url: '', apk_version: ''}
            },
            methods: {
                addOrEditApk: function (id) {
                    var obj = {
                        apk_id: id,
                        apk_name: this.data.apk_name,
                        apk_des: this.data.apk_des,
                        apk_url: this.data.apk_url,
                        apk_version: this.data.apk_version
                    };
                    $.ajax({
                        url: this.url + "/ApkManager/apkAddOrEdit",
                        type: "POST",
                        data: obj,
                        success: function (data) {
                            if (data.message) {
                                simpleNotify(id == 0 ? "新增成功" : '修改成功');
                                window.location.reload();
                            }
                        }
                    })
                }
            },
            mounted: function () {
                var that = this;
                $.ajax({
                    url: this.url + "/ApkManager/getapk",
                    type: "get",
                    success: function (data) {
                        if (data.message) {
                            data.data == null ? that.state = true : that.data = data.data;
                        }
                    }

                })
            }
        })
    })()
</script>
</html>
