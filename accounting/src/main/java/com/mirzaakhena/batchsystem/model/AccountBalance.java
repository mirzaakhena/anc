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

import com.fasterxml.jackson.annotation.JsonView;

@Getter
@Setter
@Entity
public class AccountBalance {

	@EmbeddedId
	private AccountBalanceKey id;

	@JsonView({ Views.AccountBalanceView.class, Views.JournalView.class, Views.JournalAccountBalanceView.class, Views.LastBalanceView.class })
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private Account account;

	@JsonView({ Views.AccountBalanceView.class })
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false)
	private Date date;

	@Column(precision = 38, scale = 2)
	private BigDecimal amount;

	@JsonView({Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class, Views.LastBalanceView.class })
	@Column(precision = 38, scale = 2)
	private BigDecimal balance;

	@PrePersist
	private void onCreated() {
		id = new AccountBalanceKey(account.getId(), date);
	}

	@JsonView({ Views.AccountBalanceView.class, Views.JournalView.class, Views.JournalAccountBalanceView.class })
	public BigDecimal getDebit() {
		if (account.getAccountSide() == AccountSide.ACTIVA && amount.compareTo(BigDecimal.ZERO) > 0) {
			return amount.abs();
		}
		if (account.getAccountSide() == AccountSide.PASSIVA && amount.compareTo(BigDecimal.ZERO) < 0) {
			return amount.abs();
		}
		return null;
	}

	@JsonView({ Views.AccountBalanceView.class, Views.JournalView.class, Views.JournalAccountBalanceView.class })
	public BigDecimal getCredit() {
		if (account.getAccountSide() == AccountSide.ACTIVA && amount.compareTo(BigDecimal.ZERO) < 0) {
			return amount.abs();
		}
		if (account.getAccountSide() == AccountSide.PASSIVA && amount.compareTo(BigDecimal.ZERO) > 0) {
			return amount.abs();
		}
		return null;
	}

}
