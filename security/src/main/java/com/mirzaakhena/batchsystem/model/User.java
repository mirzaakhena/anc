package com.mirzaakhena.batchsystem.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 40, unique = true)
	private String username;

	@Column(length = 60)
	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<ClientUser> clientUsers;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<AccessRight> accessRight = new ArrayList<>();
		clientUsers.forEach(cu -> accessRight.add(cu.getAccessRight()));
		return accessRight;
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
