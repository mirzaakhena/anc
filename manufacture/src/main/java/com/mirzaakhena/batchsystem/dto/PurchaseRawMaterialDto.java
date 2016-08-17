package com.mirzaakhena.batchsystem.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRawMaterialDto {

	private String extraDescription;

	private CashDto cash;

	private BankDto bank;

	private AccountPayableDto accountPayable;

	private List<PurchaseRawMaterialItemDto> listRawMaterial;

}
