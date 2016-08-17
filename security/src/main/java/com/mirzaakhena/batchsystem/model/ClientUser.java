package com.mirzaakhena.batchsystem.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ClientUser {

	@EmbeddedId
	private ClientUserKey id;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private Client client;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, nullable = true)
	private AccessRight accessRight;

	@PrePersist
	private void onCreated() {
		id = new ClientUserKey(client.getId(), user.getId(), accessRight.getId());
	}

}
