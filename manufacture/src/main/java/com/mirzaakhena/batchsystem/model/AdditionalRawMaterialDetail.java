package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
public class AdditionalRawMaterialDetail {

	@JsonIgnore
	@EmbeddedId
	private AdditionalRawMaterialDetailKey id;

	@JsonIgnore
	@ManyToOne
	@JoinColumns(value = { 
			@JoinColumn(name = "account_id", insertable = false, updatable = false),
			@JoinColumn(name = "journal_id", insertable = false, updatable = false)
		})
	private WorkInProcessBalance workInProcessBalance;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private RawMaterial rawMaterial;

	@Column
	private double quantity;

	@PrePersist
	private void onCreated() {
		id = new AdditionalRawMaterialDetailKey(workInProcessBalance.getId(), rawMaterial.getId());
	}

}
