package com.mirzaakhena.batchsystem.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FinishedGoods extends Goods {

	@Column(precision = 38, scale = 2)
	private BigDecimal salePrice;
	
	@OneToMany(mappedBy = "finishedGoods", cascade = CascadeType.ALL)
	private List<RawMaterialDetail> listRawMaterialDetail;

	@Column(precision = 38, scale = 2)
	private BigDecimal costRawMaterial;
	
	@Column(precision = 38, scale = 2)
	private BigDecimal costOverhead;

	@Column(precision = 38, scale = 2)
	private BigDecimal costLabour;
	
}
