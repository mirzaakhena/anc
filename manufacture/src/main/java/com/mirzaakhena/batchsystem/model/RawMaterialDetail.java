package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
public class RawMaterialDetail {

	@JsonIgnore
	@EmbeddedId
	private RawMaterialDetailKey id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private FinishedGoods finishedGoods;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private RawMaterial rawMaterial;

	@Column
	private double quantity;

	@PrePersist
	private void onCreated() {
		id = new RawMaterialDetailKey(finishedGoods.getId(), rawMaterial.getId());
	}

}
