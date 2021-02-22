package com.uitgis.kras.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

	private static final long serialVersionUID = 3787040202655969764L;
	
	private String user_id;
	private String login_id;
	private String passwd;
	
	private List<String> role_type;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public List<String> getRole_type() {
		return role_type;
	}

	public void setRole_type(List<String> role_type) {
		this.role_type = role_type;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.role_type.stream()
			//.map(SimpleGrantedAuthority::new)
			.map(role -> {
				return new SimpleGrantedAuthority("ROLE_" + role);
			})
			.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.passwd;
	}

	@Override
	public String getUsername() {
		return this.login_id;
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

}
