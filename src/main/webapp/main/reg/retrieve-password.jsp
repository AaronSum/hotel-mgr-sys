<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; UTF-8">
		<title>找回密码</title>
		<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger.css" rel="stylesheet" type="text/css">
		<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger-theme-flat.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div ng-app="retrievePasswordApp">
			<div>
				<div ui-view></div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-1.3.5.js"></script>
	<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-router-0.2.13/angular-ui-router.min.js"></script>
	<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/js/bootstrap.min.js"></script>
	<script type="text/javascript">var contentPath = "<%=urlPath %>";</script>
	<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/js/messenger.min.js"></script>
	<script type="text/javascript" src="<%=urlPath %>/main/reg/js/retrievepassword-module.js?rel=1.0"></script>
	<script type="text/javascript" src="<%=urlPath %>/js/tools.js"></script>
	<!-- <script type="text/javascript" src="/js/base/fileUpload.js" /></script> -->
</html>