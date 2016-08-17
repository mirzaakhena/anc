package com.mirzaakhena.batchsystem.model;

import java.io.Serializable;

import javax.persistence.Column;
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
public class AdditionalRawMaterialDetailKey implements Serializable {

	@Column
	private WorkInProcessBalanceKey id;

	@Column
	private Long raw_material_id;

}
