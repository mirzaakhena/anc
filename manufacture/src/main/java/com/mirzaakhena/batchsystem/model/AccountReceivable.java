package com.mirzaakhena.batchsystem.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
public class AccountReceivable extends DelayedPayment {

	@ManyToOne
	private Customer customer;

	@JsonIgnore
	@OneToOne
	private Journal journal;
	
	@Override
	public String getName() {
		return customer.getName();
	}
	
	@Override
	public String getDescription() {
		return journal.getDescription();
	}
		

}
