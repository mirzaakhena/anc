package com.mirzaakhena.batchsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentAccountPayableDto {

	private Long id;
	
	private CashDto cash;

	private BankDto bank;

	private String nextPaymentDate;
	
}
