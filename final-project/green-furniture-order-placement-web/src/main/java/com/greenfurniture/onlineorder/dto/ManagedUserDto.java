package com.greenfurniture.onlineorder.dto;

import java.time.Instant;
import java.util.Set;

import javax.validation.constraints.Size;

public class ManagedUserDto extends UserDto {

	public static final int PASSWORD_MIN_LENGTH = 4;
	public static final int PASSWORD_MAX_LENGTH = 100;

	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	private String password;

	public ManagedUserDto() {
	}

	public ManagedUserDto(Long id, String login, String password, String firstName, String lastName, String email,
			boolean activated, String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
			Set<String> authorities) {

		super(id, login, firstName, lastName, email, activated, createdBy, createdDate, lastModifiedBy,
				lastModifiedDate, authorities);

		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "ManagedUserDto{" + "} " + super.toString();
	}
}
