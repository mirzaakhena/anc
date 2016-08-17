package com.mirzaakhena.batchsystem.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Getter
@Setter
@Entity
public class InventoryBalance {

	@EmbeddedId
	private InventoryBalanceKey id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private Account account;

	@JsonIgnore
	@ManyToOne
	private Journal journal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false)
	private Date date;
	
	public Long getAccountId() {
		return account.getId();
	}
	
	public String getAccountName() {
		return account.getName();
	}

	public String getAccountCode() {
		return account.getCode();
	}

	public String getDescription() {
		return journal.getDescription();
	}

	// ----

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class })
	@Column
	private double amountQuantity;

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class })
	@Column(precision = 38, scale = 2)
	private BigDecimal amountPrice;

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class })
	@Column(precision = 38, scale = 2)
	private BigDecimal amountTotalPrice;

	// @JsonView({ Views.AccountBalanceView.class, Views.GeneralLedgerView.class
	// })
	// public BigDecimal getAmountTotalPrice() {
	// if (amountPrice == null) {
	// return BigDecimal.ZERO;
	// }
	// return amountPrice.multiply(new BigDecimal(amountQuantity));
	// }

	// ----

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class })
	@Column
	private double balanceQuantity;

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class })
	@Column(precision = 38, scale = 2)
	private BigDecimal balancePrice;

	// @JsonView({ Views.AccountBalanceView.class, Views.GeneralLedgerView.class
	// })
	// public BigDecimal getBalancePrice() {
	// if (balanceQuantity == 0) {
	// return BigDecimal.ZERO;
	// }
	// return balanceTotalPrice.divide(new BigDecimal(balanceQuantity),
	// RoundingMode.FLOOR);
	// }

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class })
	@Column(precision = 38, scale = 2)
	private BigDecimal balanceTotalPrice;

	@PrePersist
	private void onCreated() {
		id = new InventoryBalanceKey(account.getId(), date);
	}

}
