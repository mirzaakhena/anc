package com.mirzaakhena.batchsystem.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
public class Purchase {

	@Id
	private Long id;

	@ManyToOne
	private Supplier supplier;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Journal journal;
	
	@Column(precision = 38, scale = 2)
	private BigDecimal amount;
	
	public Date getDate() {
		return journal.getDate();
	}
	
	public String getDescription() {
		return journal.getDescription();
	}

	@PrePersist
	private void onCreated() {
		id = journal.getId();
	}

}
