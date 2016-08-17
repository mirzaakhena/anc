package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SubAccountRelation {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 60)
	private String className;

	@OneToOne
	private Account parentAccount;

}
