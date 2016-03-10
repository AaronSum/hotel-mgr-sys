package com.mk.ots.security.web.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class RemberMeService extends TokenBasedRememberMeServices {

	public RemberMeService(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	public final String HEADER_SECURITY_TOKEN = "X-CustomToken";

	/**
	 * Locates the Spring Security remember me token in the request and returns
	 * its value.
	 *
	 * @param request
	 *            the submitted request which is to be authenticated
	 * @return the value of the request header (which was originally provided by
	 *         the cookie - API expects it in header)
	 */
	// @Override
	// protected String extractRememberMeCookie(HttpServletRequest request) {
	// String token = request.getHeader(this.HEADER_SECURITY_TOKEN);
	// if ((token == null) || (token.length() == 0)) {
	// return null;
	// }
	//
	// return token;
	// }
}
