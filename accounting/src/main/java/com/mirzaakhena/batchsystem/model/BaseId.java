package com.mirzaakhena.batchsystem.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseId implements Serializable {

	@Id
	@GeneratedValue
	protected Long id;

//	@ManyToOne
//	@JoinColumn(insertable = false, updatable = false, name = "client_id")
//	private Client client;

}
