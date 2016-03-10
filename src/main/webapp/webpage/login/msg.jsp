<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String urlPath = request.getContextPath();
	String status = request.getParameter("status");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>提示信息</title>
		<link rel="icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" /> 
		<link rel="shortcut icon" href="<%=urlPath %>/favicon.ico" type="image/x-icon" />
		<link href="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<style type="text/css">
			.hms-msg-modal{
				text-align: center;
			}
			.hms-msg-modal button{
				width: 50%;
			}
		</style>
	</head>
	<body>
		<!-- Modal -->
		<div class="modal fade" id="hmsSysModal" tabindex="-1" role="dialog" aria-labelledby="hmsSysModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h4 class="modal-title" id="hmsSysModalLabel">系统提示</h4>
		      </div>
		      <div class="modal-body">
		        <p id="errorMag">${context}</p>
		      </div>
		      <div class="modal-footer hms-msg-modal">
		        <button type="button" class="btn btn-danger btn-lg" code="#hmsSysModal" id="hmsSysModalOk">确定</button>
		      </div>
		    </div>
		  </div>
		</div>	
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/jquery/jquery-2.1.3/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="<%=urlPath %>/main/plug-in/bootstrap/bootstrap-3.3.4/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			window.onload = function() {
				var contentPath = "<%=urlPath %>";
				var status = "${status}" || "<%=status %>";
				var reloadUrl = "";
				if (status === "1") { //session过滤器判断
					jQuery("#hmsSysModal").modal("show");
					//点击确认按钮跳转页面
					jQuery("#hmsSysModalOk").on("click", function () {
						jQuery("#hmsSysModal").modal("hide");
						reloadUrl = "<%=urlPath %>/";
						location.href = "<%=urlPath %>/";
					});
				} else if (status === "2") {
					jQuery("#hmsSysModal").modal("show");
					//点击确认按钮跳转页面
					jQuery("#hmsSysModalOk").on("click", function () {
						jQuery("#hmsSysModal").modal("hide");
						reloadUrl = "<%=urlPath %>/main/reg/reglogin.jsp#/hotelMsg";
						location.href = "<%=urlPath %>/main/reg/reglogin.jsp#/hotelMsg";
					});
				} else if (status === "3") {
					jQuery("#errorMag").val("用户登录超时或在别处登录");
					jQuery("#hmsSysModal").modal("show");
					//点击确认按钮跳转页面
					jQuery("#hmsSysModalOk").on("click", function () {
						jQuery("#hmsSysModal").modal("hide");
						reloadUrl = "<%=urlPath %>/";
						location.href = "<%=urlPath %>/";
					});
				} else {
					reloadUrl = "<%=urlPath %>/";
					location.href = "<%=urlPath %>/";
				}
				$('#hmsSysModal').on('hidden.bs.modal', function (e) {
					location.href = reloadUrl;
				});
			};
		</script>
	</body>
</html>