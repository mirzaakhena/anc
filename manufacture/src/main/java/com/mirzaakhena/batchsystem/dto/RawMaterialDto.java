package com.mirzaakhena.batchsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RawMaterialDto {

	private String name;
	
	private String description;
	
	private Long unitId;
	
	private double minimalQuantity;
	
}
