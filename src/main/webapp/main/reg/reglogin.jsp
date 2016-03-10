<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mk.hms.utils.HmsFileUtils"%>
<%@ page import="com.mk.hms.utils.ContentUtils"%>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
	String qiniuDownloadAddress = HmsFileUtils.getSysContentItem(ContentUtils.QINIU_DOWNLOAD_ADDRESS);
	String sysLabel = HmsFileUtils.getSysContentItem(ContentUtils.SYS_LABEL);
	//if (sysLabel.startsWith("$") || (!sysLabel.equals("test") && !sysLabel.equals("product"))) {
	//	sysLabel = "dev"; //开发模式
	//} else {
		sysLabel = "pro"; //压缩模式
	//}
%>
<%@ include file="/webpage/include/version.jsp" %>
<html ng-app="hmsApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>首页</title>
		<%if (sysLabel.equals("dev")) {%>
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
			<link rel="stylesheet" href="<%=urlPath %>/main/reg/css/reg.css" />
			<link href="<%=urlPath %>/main/plug-in/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/font-awesome-animation/font-awesome-animation.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger-theme-flat.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.min.css" rel="stylesheet" type="text/css">
		<%} else {%>
			<link href="<%=urlPath %>/js/grunt/hms-reg.min.css?version=<%=version%>" rel="stylesheet" type="text/css">
		<%}%>
	</head>
	<body>
		<div ui-view></div>	
	</body>
	<script type="text/javascript">
		var contentPath = "<%=urlPath %>";
		var qiniuDownloadAddress = "<%=qiniuDownloadAddress%>";
	</script>
	<%if (sysLabel.equals("dev")) {%>
		<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
		<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/layer.js"></script>
		<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/extend/layer.ext.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-1.3.5.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-animate.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-sanitize.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-router-0.2.13/angular-ui-router.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-locale_zh-cn.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/js/hms-service.js"></script>
	<%} else {%>
		<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
		<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/layer.js"></script>
		<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/extend/layer.ext.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/js/grunt/hms-reg.min.js?version=<%=version%>"></script>
		<script type="text/javascript" src="<%=urlPath %>/js/grunt/hms-reg.tmpl.js?version=<%=version%>"></script>
	<%}%>
	<script type="text/javascript">
		layer.config({
		    extend: 'extend/layer.ext.js'
		});
	</script>
	<%if (sysLabel.equals("dev")) {%>
		<script type="text/javascript" src="<%=urlPath %>/main/reg/js/regloginModule.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/js/hms-angularLib.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/reg/js/regloginControllers.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.tpl.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-file-upload/angular-file-upload.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/js/messenger.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/js/tools.js"></script>
		<!-- <script type="text/javascript" src="/js/base/fileUpload.js" /></script> -->
	<%}%>
</html>