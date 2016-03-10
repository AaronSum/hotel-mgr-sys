package com.mk.hms.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mk.hms.model.OutModel;
import com.mk.hms.utils.UserAgentUtils;

public class HmsAccessSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication paramAuthentication) throws IOException,
			ServletException {
		// 判断是否是手机
		if (UserAgentUtils.isMobileOrTablet(request)) {
			response.setHeader("Access-Control-Allow-Origin", "*");  
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
			response.setHeader("Access-Control-Max-Age", "3600");  
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		}
		OutModel outModel = new OutModel(true);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(JSONObject.fromObject(outModel).toString());
        out.flush();
        out.close();
	}
}
