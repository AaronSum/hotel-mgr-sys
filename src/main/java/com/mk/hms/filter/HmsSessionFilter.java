package com.mk.hms.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * session 过滤器
 * @author hdy
 *
 */
public class HmsSessionFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String uri = request.getRequestURI(); //请求的uri
		if (uri.contains("/webpage/") && (uri.endsWith(".jsp") || uri.endsWith(".html"))) { //直接url跳转
			printOutString(request, response);
			return;
		}
		filterChain.doFilter(request, response); //如果不执行过滤，则继续
	}
	
	/**
	 * 打印字符流
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void printOutString(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//如果session中不存在登录者实体，则弹出框提示重新登录
		//设置request和response的字符集，防止乱码
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\">");
		builder.append("location.href = '").append(request.getContextPath() + "/login/msg&status=1';");
		builder.append("</script>");
		out.print(builder.toString());
	}
}
