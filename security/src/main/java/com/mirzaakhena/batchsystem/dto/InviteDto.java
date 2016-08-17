package com.mirzaakhena.batchsystem.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InviteDto extends UserDto {

	private Long clientId;
	
	private List<Long> accessRightIds;
	
}
