package com.mirzaakhena.batchsystem.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@MappedSuperclass
public abstract class DelayedPayment extends BaseId {

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expectationDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date actualDate;

	@Column(precision = 38, scale = 2)
	private BigDecimal amount;

	public abstract String getName();

	public abstract String getDescription();

	public boolean isPaid() {
		return actualDate != null;
	}

}
