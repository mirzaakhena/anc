package com.mirzaakhena.batchsystem.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JournalTransactionDto {

	private String description;
	
	private List<TransactionDto> transactions;
	
}
