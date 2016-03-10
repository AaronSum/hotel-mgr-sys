<%@page import="com.mk.hms.utils.UserAgentUtils"%>
<%
	String urlPath = request.getContextPath();
	boolean isMobileOrTablet = UserAgentUtils.isMobileOrTablet(request);
%>
<script type="text/javascript">
	window.location = '<%=urlPath %>/home/home';
</script>