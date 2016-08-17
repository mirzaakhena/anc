package com.mirzaakhena.batchsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AccountChildIndex {

	@Id
	private Long account_id;

	@OneToOne
	@JoinColumn(insertable = false, updatable = false)
	private Account account;

	@Column
	private int childCurrentIndex;

	@Column
	private int childCount;

	@PrePersist
	private void onCreated() {
		account_id = account.getId();
	}

}
