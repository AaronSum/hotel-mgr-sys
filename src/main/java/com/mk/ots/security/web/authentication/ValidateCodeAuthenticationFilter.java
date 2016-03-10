package com.mk.ots.security.web.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mk.ots.security.web.SessionManager;
import com.mk.ots.utils.MD5Util;

public class ValidateCodeAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private boolean postOnly = true;
	private boolean allowEmptyValidateCode = false;
	private String sessionvalidateCodeField = ValidateCodeAuthenticationFilter.DEFAULT_SESSION_VALIDATE_CODE_FIELD;
	private String validateCodeParameter = ValidateCodeAuthenticationFilter.DEFAULT_VALIDATE_CODE_PARAMETER;
	public static final String DEFAULT_SESSION_VALIDATE_CODE_FIELD = "validateCode";
	public static final String DEFAULT_VALIDATE_CODE_PARAMETER = "validateCode";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		SessionManager.setSession(request.getSession());

		String username = StringUtils.trimToEmpty(this.obtainUsername(request));
		String password = this.obtainPassword(request);
		if (password == null) {
			password = StringUtils.EMPTY;
		}
		password = MD5Util.md5Hex(password);

		if (!this.isAllowEmptyValidateCode()) {
			this.checkValidateCode(request);
		}
		request.getSession().removeAttribute(this.sessionvalidateCodeField);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		this.setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 *
	 * <li>比较session中的验证码和用户输入的验证码是否相等</li>
	 *
	 */
	protected void checkValidateCode(HttpServletRequest request) {
		String sessionValidateCode = this.obtainSessionValidateCode(request);
		String validateCodeParameter = this.obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {// &&!"TTTT".equals(validateCodeParameter)
			throw new AuthenticationServiceException("验证码不正确");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		return request.getParameter(this.validateCodeParameter);
	}

	protected String obtainSessionValidateCode(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(this.sessionvalidateCodeField);
		return null == obj ? "" : obj.toString();
	}

	public boolean isPostOnly() {
		return this.postOnly;
	}

	@Override
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public String getValidateCodeName() {
		return this.sessionvalidateCodeField;
	}

	public void setValidateCodeName(String validateCodeName) {
		this.sessionvalidateCodeField = validateCodeName;
	}

	public boolean isAllowEmptyValidateCode() {
		return this.allowEmptyValidateCode;
	}

	public void setAllowEmptyValidateCode(boolean allowEmptyValidateCode) {
		this.allowEmptyValidateCode = allowEmptyValidateCode;
	}
}
