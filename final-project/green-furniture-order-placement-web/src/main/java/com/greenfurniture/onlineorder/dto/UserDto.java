package com.greenfurniture.onlineorder.dto;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.greenfurniture.onlineorder.constant.Constants;
import com.greenfurniture.onlineorder.domain.Authority;
import com.greenfurniture.onlineorder.domain.User;

public class UserDto {

	private Long id;

	@NotBlank
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 100)
	private String email;

	private boolean activated = false;

	private String createdBy;

	private Instant createdDate;

	private String lastModifiedBy;

	private Instant lastModifiedDate;

	private Set<String> authorities;

	public UserDto() {

	}

	public UserDto(User user) {
		this(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getActivated(), user.getCreatedBy(), user.getCreatedDate(), user.getLastModifiedBy(),
				user.getLastModifiedDate(),
				user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
	}

	public UserDto(Long id, String login, String firstName, String lastName, String email, boolean activated,
			String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
			Set<String> authorities) {

		this.id = id;
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.activated = activated;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.authorities = authorities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public boolean isActivated() {
		return activated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	@Override
	public String toString() {
		return "UserDto{" + "login='" + login + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + ", email='" + email + '\'' + ", activated=" + activated + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", lastModifiedBy='" + lastModifiedBy + '\'' + ", lastModifiedDate="
				+ lastModifiedDate + ", authorities=" + authorities + "}";
	}
}
