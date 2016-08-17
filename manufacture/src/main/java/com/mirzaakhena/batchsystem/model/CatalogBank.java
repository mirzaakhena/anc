package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CatalogBank extends SubAccount {

	@Column(length = 60)
	private String address;

	@Column(length = 60)
	private String accountNumber;

}
