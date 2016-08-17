package com.mirzaakhena.batchsystem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
public class Account extends BaseId {

	@JsonView({ Views.AccountBalanceView.class, Views.AccountView.class, Views.JournalView.class, Views.TemplateView.class })
	@Override
	public Long getId() {
		return super.getId();
	}

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class, Views.AccountView.class, Views.JournalView.class, Views.InventoryView.class, Views.TemplateView.class })
	@Column(length = 80)
	private String name;

	@JsonView({ Views.AccountBalanceView.class, Views.JournalAccountBalanceView.class, Views.AccountView.class, Views.JournalView.class, Views.InventoryView.class, Views.TemplateView.class })
	@Column(length = 20)
	private String code;

	@JsonView({ Views.JournalAccountBalanceView.class, Views.AccountView.class, Views.AccountBalanceView.class, Views.TemplateView.class })
	@Enumerated(EnumType.ORDINAL)
	private AccountSide accountSide;

	@JsonView({ Views.JournalView.class })
	@ManyToOne
	private Account accountParent;

	@JsonView({ Views.AccountBalanceView.class, Views.AccountView.class, Views.JournalView.class,  })
	@Column
	private int level;

	@JsonIgnore
	@Column
	private boolean deleted;

	// @JsonView({ Views.AccountBalanceView.class, Views.AccountView.class, })
	// @Column
	// private boolean childAsInventory;

	@JsonView({ Views.AccountBalanceView.class, Views.AccountView.class, Views.TemplateView.class })
	@Enumerated(EnumType.ORDINAL)
	private AccountType accountType;

	// @JsonIgnore
	// @Column
	// private int childCurrentIndex;
	//
	// @JsonView({ Views.AccountBalanceView.class, Views.AccountView.class,
	// Views.GeneralLedgerView.class })
	// @Column
	// private int childCount;

	@OneToMany(mappedBy = "accountParent", cascade = CascadeType.ALL)
	private List<Account> childAccount;

	@PrePersist
	private void onCreated() {
		// childCount = 0
		// childCurrentIndex = 0;
		deleted = false;
	}

}
