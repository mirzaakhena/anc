package com.mirzaakhena.batchsystem.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartWIPDto {

	private String wipCode;

	private String productionCode;

	private double newQuantity;

	// -----------

	private double prevQuantity = 0;

	private BigDecimal prevCostRawMaterial = BigDecimal.ZERO;

	private BigDecimal prevCostLabour = BigDecimal.ZERO;
	
	private BigDecimal prevCostOverhead = BigDecimal.ZERO;

}
