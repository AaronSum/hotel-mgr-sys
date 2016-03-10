<%
	String urlPath = request.getContextPath();
%>
<script type="text/javascript">
	window.location = '<%=urlPath %>/resources/j_spring_security_logout';
</script>