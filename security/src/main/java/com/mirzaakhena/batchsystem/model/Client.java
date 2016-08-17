package com.mirzaakhena.batchsystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
public class Client implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 30)
	private String name;

}
