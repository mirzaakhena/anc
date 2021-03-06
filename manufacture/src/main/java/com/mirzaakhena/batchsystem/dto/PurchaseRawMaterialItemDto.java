package com.mirzaakhena.batchsystem.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRawMaterialItemDto {

	private String code;
	
	private BigDecimal price;

	private double quantity;
	
}
