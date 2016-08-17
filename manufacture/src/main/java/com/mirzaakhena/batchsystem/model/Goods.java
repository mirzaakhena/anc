package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@MappedSuperclass
public abstract class Goods extends SubAccount {

	@Column
	private double minimalQuantity;
	
	@Column(length = 160)
	private String description;

	@JsonIgnore
	@ManyToOne
	private Unit unit;
	
	public String getUnitName() {
		return unit.getName();
	}

}
