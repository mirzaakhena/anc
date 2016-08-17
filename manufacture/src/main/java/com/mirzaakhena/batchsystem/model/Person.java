package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Person extends SubAccount {

	@Column(length = 140)
	private String address;

	@Column(length = 30)
	private String phone;

	@Column(length = 40)
	private String email;

}
