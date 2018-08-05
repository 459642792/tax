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
    <h4 class="modal-title">修改品牌</h4>
</div>
<div class="modal-body no-padding">
    <form class="smart-form">
        <fieldset style="max-height:600px; overflow-x: hidden; overflow-y: auto;">
            <section>
                <div class="row">
                    <label class="label col col-2">品牌名称</label>
                    <div class="col col-10">
                        <label class="input">
                            <i class="icon-append fa fa-fw fa-info-circle"></i>
                            <input type="text" id="title" name="title" class="input" maxlength="15"
                                   placeholder="例:茅台..."/>
                            <b class="tooltip tooltip-top-right">请输入品牌名称</b>
                        </label>
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
    //对页面进行保存操作
    $(function () {
        $.ajax({
            type: "get",
            url: "${pageContext.request.contextPath}/pinpaiMain/pinpaiDetail",
            data: {
                Id: '${Id}'
            },
            success: function (result) {
                $('#title').val(result.data.name);
            }
        })

        $('#submit').click(function () {
            if ($.trim($('#title').val()) == '') {
                window.simpleNotify("请输入品牌名称。", "提示", "error");
                return false;
            }
            var obj = {
                Name: $.trim($('#title').val()),
                Id: '${Id}'
            };
            $.get("${pageContext.request.contextPath}/pinpaiMain/AddorEditPinpai", obj, function (res) {
                if (res.success) {
                    window.simpleNotify("保存成功", '提示', "success");
                    $("#remoteModal").modal("hide");
                    if (window.model && typeof (window.model.refreshTable) == 'function') {
                        //window.model.refreshTable();
                        window.location.reload();
                    }
                } else {
                    window.simpleNotify("保存失败。", "提示", "error");
                    return false;
                }
            });
        });
    })
</script>
</body>
</html>
