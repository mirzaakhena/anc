package com.mirzaakhena.batchsystem.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonView;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
public class Journal extends BaseId {

	public Journal() {
		date = new Date();
	}
	
	@JsonView({ Views.JournalAccountBalanceView.class})
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@JsonView({ Views.JournalAccountBalanceView.class, Views.JournalView.class, Views.LastBalanceView.class, Views.AccountBalanceView.class })
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@JsonView({ Views.JournalAccountBalanceView.class, Views.JournalView.class, Views.AccountBalanceView.class })
	@Column
	private String description;

	@JsonView(Views.JournalView.class)
	@OneToMany(mappedBy = "journal", cascade = CascadeType.ALL)
	private List<JournalAccountBalance> listJournalAccountBalance;

	@Override
	public String toString() {
		return id + " : " + description;
	}

}
