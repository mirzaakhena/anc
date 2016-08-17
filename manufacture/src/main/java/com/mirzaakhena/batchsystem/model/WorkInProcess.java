package com.mirzaakhena.batchsystem.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WorkInProcess extends SubAccount {

	@ManyToOne
	private FinishedGoods finishedGoods;

}
