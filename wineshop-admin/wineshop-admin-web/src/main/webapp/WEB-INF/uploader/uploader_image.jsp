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
<title>交易数据1</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Language" content="ja"/>
<meta http-equiv="Content-Script-Type" content="text/javascript"/>
<meta http-equiv="Content-Style-Type" content="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/uploader/js/uploader_image.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/uploader/css/uploader_image.css"/>
<link rel="stylesheet" type="text/css" href="webuploader文件夹/webuploader.css">
<!--引入JS-->
<script type="text/javascript" src="webuploader文件夹/webuploader.js"></script>
</head>
<body>

</body>
</html>