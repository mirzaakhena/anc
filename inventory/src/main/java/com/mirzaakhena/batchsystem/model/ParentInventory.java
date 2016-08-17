package com.mirzaakhena.batchsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
public class ParentInventory {

	@Id
	private Long account_id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private Account account;
	
	public String getName() {
		return account.getName();
	}
	
	public String getCode() {
		return account.getCode();
	}
	
	@PrePersist
	private void onCreated() {
		account_id = account.getId();
	}

}
