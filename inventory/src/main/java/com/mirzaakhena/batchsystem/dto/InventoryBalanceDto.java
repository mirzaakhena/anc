package com.mirzaakhena.batchsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBalanceDto {
	
	private String code;
	
	private double quantity;
	
}
