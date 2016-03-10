<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
%>
<html login="true" ng-app="loginApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>眯客HMS</title>
		<link rel="icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" /> 
		<link rel="shortcut icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" /> 
		<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<link href="<%=urlPath %>/webpage/login/css/hms-login.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="hms-login-container" ng-controller="loginController">
			<div class="left"></div>
			<div class="right">
				<div class="hms-pms-login">
					<a href="{{pmsLogin}}">管理员登录</a>
				</div>
				<div class="hms-login-form">
					<p ng-class="{false: 'error-msg', true: 'success-msg'}[success]" class="msg" ng-bind="errorMsg"></p>
					<!-- <p class="title">欢迎回来，海量订单等你处理</p> -->
					<form class="form-horizontal" method="post">
						 <div class="form-group">
						    <div class="col-md-12">
						    	<input type="text" class="form-control" value="" id="loginName" name="loginName" ng-model="loginName" placeholder="登录名">
						    </div>
						 </div>
						 <div class="form-group">
						    <div class="col-md-12">
						    	<input type="password" class="form-control" value="" id="password" name="password" ng-model="password" placeholder="密码" ng-keyup="passwordEnter($event)">
						    </div>
						 </div>
					  	<div class="form-group submit-btn">
						    <div class="col-md-12">
						      <button type="button" class="btn btn-primary" ng-click="isExitLoginUser()"><i class="glyphicon glyphicon-home"></i>&nbsp;登录</button>
						    </div>
					  </div>
					</form>
					<div class="bottom">
						<div class="pull-left">
							<a href="{{regLogin}}">没有账号免费开店</a>
						</div>
						<div class="pull-right">
							<a href="<%=urlPath %>/main/reg/retrieve-password.jsp">忘记密码</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">使用提示</h4>
		      </div>
		      <div class="modal-body">
		        <p>请使用webkit内核的浏览器访问本网站</p>
		        <p>推荐<a href="http://rj.baidu.com/soft/detail/14744.html?ald" target="_blank" class="">下载Chrome</a>浏览器访问</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		      </div>
		    </div>
		  </div>
		</div>	
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/angularJs/angularJs-3.1.5/angular-1.3.5.min.js"></script>
		<script type="text/javascript">var contentPath = "<%=urlPath %>";</script>
		<script type="text/javascript" src="<%=urlPath %>/webpage/login/js/login-module.js"></script>
		<script type="text/javascript">
			function isWebkitForHms(){
				return document.body.style.WebkitBoxShadow !== undefined;
			}
		</script>
	</body>
</html>