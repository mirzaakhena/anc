package com.mirzaakhena.batchsystem.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class InventoryBalanceKey implements Serializable {

	@Column
	private Long account_id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

}
