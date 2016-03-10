package com.mk.ots.security.web.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.mk.hms.enums.ErrorCodeEnum;
import com.mk.hms.model.OutModel;

/**
 * <p>
 * In the pre-authenticated authentication case (unlike CAS, for example) the
 * user will already have been identified through some external mechanism and a
 * secure context established by the time the security-enforcement filter is
 * invoked.
 * <p>
 * Therefore this class isn't actually responsible for the commencement of
 * authentication, as it is in the case of other providers. It will be called if
 * the user is rejected by the AbstractPreAuthenticatedProcessingFilter,
 * resulting in a null authentication.
 * <p>
 * The <code>commence</code> method will always return an
 * <code>HttpServletResponse.SC_FORBIDDEN</code> (403 error).
 *
 * @see org.springframework.security.web.access.ExceptionTranslationFilter
 *
 * @author Luke Taylor
 * @author Ruud Senden
 * @since 2.0
 */
@Component
public class Http403ForbiddenEntryPoint implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(Http403ForbiddenEntryPoint.class);

	@Autowired
	SecurityContextHolderStrategy securityContextHolderStrategy;

	/**
	 * Always returns a 403 error code to the client.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException {
		if (this.securityContextHolderStrategy.getContext().getAuthentication() == null) {
			String headerName = request.getHeader("Accept"); // 异步请求
			if (headerName.startsWith("application/json") && !request.getRequestURI().equals("/login/main") && !request.getRequestURI().equals("/home/home")) {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out;
				OutModel model = new OutModel(true);
				model.setErrorCode(ErrorCodeEnum.ERROR_SESSION_2001.getValue());
				model.setErrorMsg(ErrorCodeEnum.ERROR_SESSION_2001.getText());
				model.setSystem(true);
				JSONObject object = JSONObject.fromObject(model);
				try {
					out = response.getWriter();
					out.write(object.toString());
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/login/main");
			}
			return;
		}
		if (Http403ForbiddenEntryPoint.logger.isDebugEnabled()) {
			Http403ForbiddenEntryPoint.logger.debug("Pre-authenticated entry point called. Rejecting access");
		}
		HttpServletResponse httpResponse = response;
		httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");

	}

}
