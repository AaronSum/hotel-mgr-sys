package com.mk.ots.security.web;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetail implements UserDetails {

	public UserDetail(Long userId, String username, String password, Object user) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.user = user;
	}

	public UserDetail(Long userId, String userName, Object user) {
		this.user = user;
		this.userId = userId;
		this.username = userName;
	}

	public UserDetail(Long userId, String userName) {
		this.userId = userId;
		this.username = userName;
	}

	private static final long serialVersionUID = 1L;

	private Long userId;

	private String username;

	private String password;

	private Object user;

	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public String getToken() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public Object getUser() {
		return this.user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public Long getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		if (this.user != null) {
			return this.user.hashCode();
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDetail) {
			return this.getUser().equals(((UserDetail) obj).getUser());
		}
		return false;
	}
}
