package com.mirzaakhena.batchsystem.model;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalInventory {

	private Long id;
	
	private String description;
	
	private Date date;
	
	private List<JournalAccountSign> accounts;
	
}
