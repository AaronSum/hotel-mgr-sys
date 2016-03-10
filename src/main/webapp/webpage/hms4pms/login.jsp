<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>眯客HMS</title>
		<link rel="icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" /> 
		<link rel="shortcut icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" /> 
	</head>
	<body>
		<div style="text-align: center; margin-top: 50px; font-weight: bold;">
			<h1 id="jump_tip">正在验证，请稍候……</h1>
		</div>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
		<script type="text/javascript">
			window.onload = function() {
				var contentPath = "<%=urlPath %>";
				if (${visible}) {
					jQuery.ajax({
						url: contentPath + "/resources/j_spring_security_check",
						type: "POST",
						dataType: "json",
					    data: {
					    	"j_username": "${loginname}",
							"j_password": "",
							"token": "${token}"
					    },
					    success: function(data) {
					    	jQuery("#jump_tip").html("正在跳转，请稍候……");
					    	jQuery.ajax({
					    		url: contentPath + "/login/isExit",
								type: "POST",
								dataType: "json",
					    		data: {
					    			"loginname": "${loginname}",
									"password": "111",
									"token": "${token}"
					    		},
					    		success: function() {
					    			location.href = contentPath + "/login/main";
					    		},
					    		error: function() {
					    			jQuery("#jump_tip").html("跳转失败，请关闭页面从新跳转");
					    		}
					    	});
					    },
					    error: function() {
					    	jQuery("#jump_tip").html("验证失败，请关闭页面从新验证");
					    }
					});
				} else {
					var htmlStr = "<p style='color: red;'>token失效</p><p>页面将在[3]秒内跳回登录页面。"
						, msg = "${msg}";
					if (msg) {
						htmlStr = "<p style='color: red;'>" + msg + "</p><p>页面将在[3]秒内跳回登录页面。";
					}
					jQuery("#jump_tip").html(htmlStr + "3……</p>");
					var times = 3;
					setInterval(function() {
						if (times == 2) {
							location.href = contentPath + "/resources/j_spring_security_logout";
						}
						times --;
						jQuery("#jump_tip").html(htmlStr + times + "……");
					}, 1000);
				}
			};
		</script>
	</body>
</html>