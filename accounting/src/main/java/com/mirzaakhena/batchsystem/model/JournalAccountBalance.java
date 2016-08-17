package com.mirzaakhena.batchsystem.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonView;

@Getter
@Setter
@Entity
public class JournalAccountBalance {

	@EmbeddedId
	private JournalAccountBalanceKey id;

	@JsonView({ Views.JournalAccountBalanceView.class, Views.LastBalanceView.class, Views.AccountBalanceView.class })
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private Journal journal;

	@JsonView({ Views.JournalAccountBalanceView.class, Views.JournalView.class, Views.LastBalanceView.class, Views.AccountBalanceView.class })
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "account_id", insertable = false, updatable = false), @JoinColumn(name = "date", insertable = false, updatable = false) })
	private AccountBalance accountBalance;

	@PrePersist
	private void onCreated() {
		id = new JournalAccountBalanceKey(accountBalance.getId(), journal.getId());
	}

}
