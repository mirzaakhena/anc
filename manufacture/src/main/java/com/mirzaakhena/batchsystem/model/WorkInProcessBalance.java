package com.mirzaakhena.batchsystem.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
public class WorkInProcessBalance {

	@JsonIgnore
	@Id
	private WorkInProcessBalanceKey id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false)
	private Date date;

	@JsonIgnore
	@ManyToOne
	private Journal journalStart;

	@JsonIgnore
	@ManyToOne
	private Journal journalFinish;

	@JsonIgnore
	@Column
	private boolean active;

	@JsonIgnore
	@ManyToOne
	private WorkInProcess workInProcess;

	public String getCode() {
		return workInProcess.getCode();
	}

	public String getName() {
		return workInProcess.getName();
	}

	public Long getWipId() {
		return workInProcess.getId();
	}

	@Column(length = 30)
	private String productionCode;

	@Column
	private double prevQuantity;

	@Column
	private double newQuantity;

	@Column
	private double finishQuantity;

	public double getTotalQuantity() {
		return (prevQuantity + newQuantity);
	}

	public double getRemainingQuantity() {
		return getTotalQuantity() - finishQuantity;
	}

	public boolean isFinished() {
		return finishQuantity > 0;
	}

	// =================================================

	@Column
	private double convertionCostLabour;

	@Column
	private double convertionCostOverhead;

	// =================================================

	@Column(precision = 38, scale = 2)
	private BigDecimal prevCostRawMaterial;

	@Column(precision = 38, scale = 2)
	private BigDecimal prevCostOverhead;

	@Column(precision = 38, scale = 2)
	private BigDecimal prevCostLabour;

	// =================================================

	// @OneToMany(mappedBy = "workInProcessBalance", cascade = CascadeType.ALL)
	// private List<AdditionalRawMaterialDetail>
	// listAdditionalRawMaterialDetail;
	//
	// @Column(precision = 38, scale = 2)
	// private BigDecimal additionalCostRawMaterial;
	//
	// @Column(precision = 38, scale = 2)
	// private BigDecimal additionalCostOverhead;
	//
	// @Column(precision = 38, scale = 2)
	// private BigDecimal additionalCostLabour;

	// =================================================

	@Column(precision = 38, scale = 2)
	private BigDecimal costRawMaterial;

	@Column(precision = 38, scale = 2)
	private BigDecimal costLabour;

	@Column(precision = 38, scale = 2)
	private BigDecimal costOverhead;

	// =================================================

	public BigDecimal getTotalCost() {
		return costRawMaterial.add(costLabour).add(costOverhead).add(prevCostRawMaterial).add(prevCostLabour).add(prevCostOverhead);
	}
	
	@Column(precision = 38, scale = 2)
	private BigDecimal remainingTotalCost;

	@PrePersist
	private void onCreated() {
		id = new WorkInProcessBalanceKey(workInProcess.getId(), date);
	}

	// -----ALL TRANSIENT DATA

}
