<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mk.hms.utils.ContentUtils" %>
<%@ page import="com.mk.hms.utils.HmsFileUtils" %>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
	String sysLabel = HmsFileUtils.getSysContentItem(ContentUtils.SYS_LABEL);
	//if (sysLabel.startsWith("$") || (!sysLabel.equals("test") && !sysLabel.equals("product"))) {
		// sysLabel = "dev"; //开发模式
	//} else {
		sysLabel = "pro"; //压缩模式
	//}
%>
<%@ include file="/webpage/include/version.jsp" %>
<html ng-app="hmsApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; UTF-8">
		<title>眯客HMS</title>
		<%if (sysLabel.equals("dev")) {%>
			<link rel="icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" /> 
			<link rel="shortcut icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" />
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/font-awesome-animation/font-awesome-animation.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/css/messenger-theme-flat.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/angularJs/ui-grid/ui-grid-unstable.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/css/hms-home.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.min.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/account/css/finance.css" rel="stylesheet" type="text/css">
			<link href="<%=urlPath %>/main/shopping/css/shopping.css" rel="stylesheet" type="text/css">
		<%} else {%>
			<link href="<%=urlPath %>/js/grunt/hms.min.css?version=<%=version%>" rel="stylesheet" type="text/css">
		<%}%>
		<script type="text/ng-template" id="menu.html"></script>
	</head>
	<body class="wrap-out" >
		<div ng-controller="socketController"></div>
		<div class="center-body content">
			<div class="container-fluid" ng-controller="hmsController">
				<div class="row hms-home-title">
					<div class="col-md-12">
						<div>
							<img src="<%=urlPath %>/images/index-log.png">
							<p class="pull-right hms-home-title-right">
								<i class="fa fa-user"></i>&nbsp;您好， <span>${user.loginname}</span> |
								<span>&nbsp;<a href="{{loginOutAction}}" class="danger faa-parent animated-hover"><i class="fa fa-sign-out faa-horizontal text-danger"></i>退出</a></span>
							</p>
						</div>
					</div>
				</div>
				<div class="row hms-home-nav">
					<div class="col-md-12">
						<div class="dropdown pull-left">
						  <button class="btn btn-default dropdown-toggle hms-home-nav-btn" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true" hms-html="homeNavDropDownBtn">
						  </button>
						  <ul class="dropdown-menu left-dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
						    <li role="presentation"  ng-repeat="hotel in hotels">
						    	<a role="menuitem" tabindex="-1" ng-click="changeHotel($index)">{{hotel.hotelName}}</a>
						    </li>
						    <li role="presentation" ng-if="user.loginname == group.regphone">
						    	<a role="menuitem" tabindex="-1" ng-click="addNewHotel()">添加酒店</a>
						    </li>
						  </ul>
						</div>
						<div ng-show="hotelStatus.init" id="hotel-tooltip-label" class="pull-left label label-info" style="margin-top:13px;"><span class="glyphicon glyphicon-info-sign"></span></div>
						<div  ng-controller="menuController">
							<div ng-include="'menu.html'"></div>
						</div>	
					</div>
				</div>
				<div>
					<!--新功能引导页-遮罩-->
					<div class="preview-mask" ng-show="preview.show"></div>
					<div ng-show="preview.show" ng-include="preview.contentTmpl"></div>
				</div>
			</div>
			<!--工具条-扳手-->
			<div class="tool-bar-start fa fa-wrench fa-2x"></div>
		</div>
		<div class="center-body content">
			<div ui-view class="hms-home_view"></div>
			<hms-loading ng-show="hmsLoading.isShow"></hms-loading>
		</div>

		<script type="text/javascript">
			var contentPath = "<%=urlPath %>";
			var user = ${thisUser};
			var hotelList = ${hotels};
			var thisHotel = ${thisHotel};
				// 格式化当前酒店字段
			    thisHotel.openTime = thisHotel.fmtOpenTime;
			    thisHotel.repairTime = thisHotel.fmtRepairTime;
			var group = ${group};
			var hostAddress = "${hostAddress}";
			var qiniuDownloadAddress = "${qiniuDownloadAddress}";
			var ruleCode = ${ruleCode};
		</script>
		<%if (sysLabel.equals("dev")) {%>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/layer.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/extend/layer.ext.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-1.3.5.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-animate.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-router-0.2.13/angular-ui-router.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/js/bootstrap.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-table/bootstrap-table.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-datetimepicker/js/locale/bootstrap-datetimepicker.zh-CN.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/silviomoreto-bootstrap-select/js/bootstrap-select.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/silviomoreto-bootstrap-select/js/i18n/defaults-zh_CN.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-locale_zh-cn.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-messenger/js/messenger.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-file-upload/angular-file-upload.min.js"></script>
			<!--script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-bootstrap-0.12.1/ui-bootstrap.min.js"></script-->
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-bootstrap-0.12.1/ui-bootstrap-tpls.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/ui-grid/ui-grid-unstable.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/underscore/underscore-min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-strap/angular-strap.tpl.min.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angular-file-upload/angular-file-upload.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/echarts/echarts.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/plug-in/cookies-scotthamper/cookies.js"></script>
			<%--<script type="text/javascript" src="<%=urlPath %>/main/plug-in/socket-io/socket.io-1.3.5.js"></script> --%>
		<%} else {%>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/layer.js"></script>
			<!--ignore--><script type="text/javascript" src="<%=urlPath %>/main/plug-in/layer-v1.9.3/layer/extend/layer.ext.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/grunt/hms.min.js?version=<%=version%>"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/grunt/hms.tmpl.js?version=<%=version%>"></script>
		<%}%>
		<script type="text/javascript">
			layer.config({
			    extend: 'extend/layer.ext.js'
			});
			jQuery(".preview-mask")[0].addEventListener("mousewheel",function(e) {
				e.preventDefault();
				e.stopPropagation();
				e.stopImmediatePropagation();
				return false;
			},true);
		</script>
		<%if (sysLabel.equals("dev")) {%>
			<script type="text/javascript" src="<%=urlPath %>/js/tools.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/hms-dom.js"></script>
			<%--<script type="text/javascript" src="<%=urlPath %>/js/hms-socket.js"></script> --%>
			<script type="text/javascript" src="<%=urlPath %>/js/router-config.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/hms-angularLib.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/hms-controller.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/js/hms-service.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/home/js/home-module.js"></script>		
			<script type="text/javascript" src="<%=urlPath %>/main/order/js/order-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/order/js/border-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/rates/js/rates-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/shopping/js/shopping-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/account/js/account-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/message/js/message-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/users/js/user-module.js"></script>
			<script type="text/javascript" src="<%=urlPath %>/main/comment/js/comment-module.js"></script>
		<%}%>
	</body>
</html>
