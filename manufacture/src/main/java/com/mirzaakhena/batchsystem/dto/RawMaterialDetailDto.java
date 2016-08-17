package com.mirzaakhena.batchsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialDetailDto {

	private String rawMaterialCode;

	private double quantity;

}
