package com.mk.ots.security.web.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MKAbstractAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 5200269798608797765L;

	public MKAbstractAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	@Override
	public Object getCredentials() {
		return "Credentials";
	}

	@Override
	public Object getPrincipal() {
		return "Principal";
	}

}
