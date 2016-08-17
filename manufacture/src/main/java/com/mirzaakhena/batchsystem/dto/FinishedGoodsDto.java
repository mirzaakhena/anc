package com.mirzaakhena.batchsystem.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinishedGoodsDto {

	private String name;

	private String description;

	private Long unitId;

	private double minimalQuantity;

	private BigDecimal overhead;

	private BigDecimal labour;

	private BigDecimal salePrice;

	private List<RawMaterialDetailDto> rawMaterialDetails;

}
