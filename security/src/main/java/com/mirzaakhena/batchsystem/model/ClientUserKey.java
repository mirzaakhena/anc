package com.mirzaakhena.batchsystem.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ClientUserKey implements Serializable {

	private Long client_id;
	
	private Long user_id;
	
	private Long authority_id;
	
}
