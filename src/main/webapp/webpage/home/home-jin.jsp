<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mk.hms.utils.ContentUtils" %>
<%@ page import="com.mk.hms.utils.HmsFileUtils" %>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>眯客HMS-管理平台</title>
		<%if (sysLabel.equals("dev")) {%>
			<link rel="icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" /> 
			<link rel="shortcut icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" />
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger-theme-flat.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/font-awesome-animation/font-awesome-animation.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/angularJs/ui-grid/ui-grid-unstable.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/home/css/home-jin.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.min.css" rel="stylesheet" type="text/css">
		<%} else {%>
			<link href="<%=urlPath %>/js/grunt/hms-jin.min.css?version=<%=version%>" rel="stylesheet" type="text/css">
		<%}%>
	</head>
	<body>
		<div class="container-fluid hms-home-jin-container">
			<div class="row hms-home-title" ng-controller="homeJinHeaderController">
				<div class="col-md-12">
					<div>
						<img src="<%=urlPath %>/images/index-log.png">
						<p class="pull-right hms-home-title-right">
							<!-- <a ng-click="toAccountMgr()">酒店财务结算</a> -->
							<i class="fa fa-user"></i>&nbsp;您好， <span>${user.loginname}</span> | 
							<span>&nbsp;<a href="{{loginOutAction}}" class="danger faa-parent animated-hover"><i class="fa fa-sign-out faa-horizontal text-danger"></i>退出</a></span>
							<span ng-show="$state.current.name!='none'"><a href="#/" class="danger"><i class="fa fa-home text-info"></i>&nbsp;返回主页</a></span>
						</p>
					</div>
				</div>
			</div>
			<hr/>
			<div ui-view class="hms-home_view" style="overflow:hidden;"></div>
			<div class="hms-home_view" ng-include="'<%=urlPath %>/main/home/template/home-jin.html'" ng-show="$state.current.name=='none'"></div>
			<hms-loading ng-show="hmsLoading.isShow"></hms-loading>
		</div>		
		<script type="text/javascript">
			var contentPath = "<%=urlPath %>";
			var user = ${thisUser};
			var isCheckUser = ${isCheckUser};
			var isForeignPerson = ${isForeignPerson};
			var qiniuDownloadAddress = "${qiniuDownloadAddress}";
		</script>
		<%if (sysLabel.equals("dev")) {%>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/layer.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/extend/layer.ext.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-1.3.5.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-animate.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-grid/ui-grid-unstable.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-router-0.2.13/angular-ui-router.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/js/bootstrap.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-table/bootstrap-table.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/js/messenger.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.tpl.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-bootstrap-0.12.1/ui-bootstrap.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-bootstrap-0.12.1/ui-bootstrap-tpls.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/underscore/underscore-min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/silviomoreto-bootstrap-select/js/bootstrap-select.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/silviomoreto-bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-locale_zh-cn.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-file-upload/angular-file-upload.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/hms-angularLib.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/hms-service.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/tools.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/router-config-jin.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/hms-dom.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/message/js/message-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/home/js/home-jin-module.js"></script>
			<!--ignore--><script src="http://webapi.amap.com/maps?v=1.3&key=7f744cdfe78aa7bb5d40f15eda4705cd" type="text/javascript"></script>
		<%} else {%>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/layer.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/extend/layer.ext.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/grunt/hms-jin.min.js?version=<%=version%>"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/grunt/hms-jin.tmpl.js?version=<%=version%>"></script>
			<!--ignore--><script src="http://webapi.amap.com/maps?v=1.3&key=7f744cdfe78aa7bb5d40f15eda4705cd" type="text/javascript"></script>
		<%}%>
		<script type="text/javascript">
			layer.config({
			    extend: 'extend/layer.ext.js'
			});
		</script>
	</body>
</html>