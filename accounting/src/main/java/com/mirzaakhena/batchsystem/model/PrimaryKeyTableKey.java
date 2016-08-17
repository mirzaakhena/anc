package com.mirzaakhena.batchsystem.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class PrimaryKeyTableKey implements Serializable {

	private String table_name;
	
	private Long client_id;
	
}
