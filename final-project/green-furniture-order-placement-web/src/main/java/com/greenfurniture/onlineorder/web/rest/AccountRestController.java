package com.greenfurniture.onlineorder.web.rest;

import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenfurniture.onlineorder.dto.ManagedUserDto;
import com.greenfurniture.onlineorder.dto.UserDto;
import com.greenfurniture.onlineorder.jparepository.UserRepository;
import com.greenfurniture.onlineorder.service.UserService;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api/rest")
public class AccountRestController {

	@Inject
	UserRepository userRepository;
	@Inject
	UserService userService;
	private static final String PASSWORD_ERROR = "Incorrect password";

	private static final Logger LOG = LoggerFactory.getLogger(AccountRestController.class);

	@PostMapping(path = "/register", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> registerAccount(@Valid @RequestBody ManagedUserDto managedUserDto) {

		HttpHeaders textPlainHeaders = new HttpHeaders();
		textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
		if (!checkPasswordLength(managedUserDto.getPassword())) {
			return new ResponseEntity<>(PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
		}
		return userRepository.findOneByLogin(managedUserDto.getLogin().toLowerCase())
				.map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
				.orElseGet(() -> userRepository.findOneByEmail(managedUserDto.getEmail())
						.map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders,
								HttpStatus.BAD_REQUEST))
						.orElseGet(() -> {
							userService.createUser(managedUserDto.getLogin(), managedUserDto.getPassword(),
									managedUserDto.getFirstName(), managedUserDto.getLastName(),
									managedUserDto.getEmail().toLowerCase());

							return new ResponseEntity<>(HttpStatus.CREATED);
						}));
	}

	@GetMapping("/authenticate")
	public String isAuthenticated(HttpServletRequest request) {
		LOG.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	@PostMapping(path = "/account/change_password", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> changePassword(@RequestBody String password) {
		if (!checkPasswordLength(password)) {
			return new ResponseEntity<>(PASSWORD_ERROR, HttpStatus.BAD_REQUEST);
		}
		userService.changePassword(password);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/account")
	public ResponseEntity<UserDto> getAccount() {
		return Optional.ofNullable(userService.getUserWithAuthorities())
				.map(user -> new ResponseEntity<>(new UserDto(user), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	private boolean checkPasswordLength(String password) {
		return !password.isEmpty() && password.length() >= ManagedUserDto.PASSWORD_MIN_LENGTH
				&& password.length() <= ManagedUserDto.PASSWORD_MAX_LENGTH;
	}

}
