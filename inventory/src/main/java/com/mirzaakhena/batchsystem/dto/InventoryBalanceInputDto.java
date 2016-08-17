package com.mirzaakhena.batchsystem.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryBalanceInputDto {

	private Long journalId;
	
	private List<InventoryBalanceDto> accounts;
	
}
