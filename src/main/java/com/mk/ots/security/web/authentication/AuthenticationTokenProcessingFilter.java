package com.mk.ots.security.web.authentication;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mk.ots.security.web.UserDetailsServiceImpl;

public class AuthenticationTokenProcessingFilter extends AbstractAuthenticationProcessingFilter {

	public final String HEADER_SECURITY_TOKEN = "X-CustomToken";

	protected AuthenticationTokenProcessingFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		this.setAuthenticationManager(new NoOpAuthenticationManager());
		this.setRememberMeServices(new RemberMeService("at", new UserDetailsServiceImpl()));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String token = request.getHeader(this.HEADER_SECURITY_TOKEN);
		this.logger.info("token found:" + token);
		AbstractAuthenticationToken userAuthenticationToken = this.authUserByToken(token);
		if (userAuthenticationToken == null) {
			throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
		}
		return userAuthenticationToken;
	}

	/**
	 * authenticate the user based on token
	 *
	 * @return
	 */
	private AbstractAuthenticationToken authUserByToken(String token) {
		if (token == null) {
			return null;
		}
		AbstractAuthenticationToken authToken = new MKAbstractAuthenticationToken(null);
		try {
			return authToken;
		} catch (Exception e) {
			this.logger.error("Authenticate user by token error: ", e);
		}
		return authToken;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		super.doFilter(req, res, chain);
	}

}
