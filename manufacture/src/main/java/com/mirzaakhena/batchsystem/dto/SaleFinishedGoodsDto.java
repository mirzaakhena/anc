package com.mirzaakhena.batchsystem.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleFinishedGoodsDto {

	private String extraDescription;

	private CashDto cash;

	private BankDto bank;

	private AccountReceivableDto accountReceivable;

	private List<SaleFinishedGoodsItemDto> listFinishedGoods;
	
}
