package com.mirzaakhena.batchsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto extends UserDto {

	private String username;

	private String password;

	private String clientName;

}
