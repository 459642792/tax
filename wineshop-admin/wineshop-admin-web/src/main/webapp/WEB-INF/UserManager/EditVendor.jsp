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
    <h4 class="modal-title">商家</h4>
</div>
<div class="modal-body no-padding">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">店铺名称</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" v-model="model.name" name="title" class="input" maxlength="15"
                                   placeholder="商家名称"/>
                            <b class="tooltip tooltip-top-right">请输入商家名称</b>
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
                            <input type="number" v-model="model." name="money" class="input" maxlength="6"
                                   placeholder="2"/>
                            <b class="tooltip tooltip-top-right">请输入联系人</b>
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
                            <input type="number" v-model="" name="count" class="input" maxlength="11"
                                   placeholder="130.."/>
                            <b class="tooltip tooltip-top-right">请输入手机号</b>
                        </label>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">店招图片</label>
                    <div class="col col-10">
                        <input type="radio" name="term" id="term" style="width:15px;height:15px">&nbsp;无条件使用</br>
                    </div>
                </div>
            </section>
            <section>
                <div class="row">
                    <label class="label col col-2">店铺地址</label>
                    <div class="col col-10">
                        <select class="input-sm" id="Province" name="Province" onchange="chg()" style="width:200px">
                        </select>
                        <select class="input-sm Town" id="city" name="city" style="width:200px">
                        </select>
                        <select class="input-sm Town" id="county" name="county" style="width:220px">
                        </select>
                    </div>
                    <div class="row">
                        <label class="label col col-2"></label>
                        <div class="col col-10">
                            <input type="radio" name="term" id="term" style="width:15px;height:15px">&nbsp;无条件使用</br>
                        </div>
                    </div>
                </div>
            </section>

        </fieldset>
        <footer>
            <button type="submit" class="btn btn-primary" id="submit">
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
//         var em = new EditPage("#",{
//             getAjax:{url:'',method:'GET'},
//             editAjax:{url:'',method:'POST'}
//         });

    })();
</script>
</body>
</html>