package com.mirzaakhena.batchsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinishWIPDto {

	private String wipCode;
	
	private double finishQuantity;
	
	private double convertionCostLabour;
	
	private double convertionCostOverhead;
	
}
