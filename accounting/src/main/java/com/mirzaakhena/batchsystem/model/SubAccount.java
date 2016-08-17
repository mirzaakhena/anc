package com.mirzaakhena.batchsystem.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class SubAccount {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@OneToOne
	private Account account;
	
	public String getName() {
		return account.getName();
	}
	
	public String getCode() {
		return account.getCode();
	}
	
}
