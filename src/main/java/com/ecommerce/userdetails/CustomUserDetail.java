/**
 * 
 */
package com.ecommerce.userdetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 */
public class CustomUserDetail implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7244072920916330721L;

	private Long id;
	private String username;
	private String password;
	private Set<String> roles;

	public CustomUserDetail() {
	}

	/**
	 * @param username
	 * @param password
	 * @param id
	 * @param roles
	 */
	public CustomUserDetail(String username, String password, Long id, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Add "ROLE_" prefix if needed
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserDetail [username=" + username + ", password=" + password + ", id=" + id + ", roles=" + roles + "]";
	}

}
