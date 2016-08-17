package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
public class AccessRight implements GrantedAuthority {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 100, unique = true)
	private String authority;

}
