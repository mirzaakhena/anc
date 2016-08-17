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
public class SaleFinishedGoodsItemDto {

	private String code;

	private double quantity;
	
	private double discountPercentage;
	
	private BigDecimal discountPrice;

}
