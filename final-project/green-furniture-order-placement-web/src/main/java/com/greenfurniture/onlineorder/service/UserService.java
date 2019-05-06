package com.greenfurniture.onlineorder.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenfurniture.onlineorder.constant.Constants;
import com.greenfurniture.onlineorder.domain.Authority;
import com.greenfurniture.onlineorder.domain.User;
import com.greenfurniture.onlineorder.dto.UserDto;
import com.greenfurniture.onlineorder.jparepository.AuthorityRepository;
import com.greenfurniture.onlineorder.jparepository.UserRepository;
import com.greenfurniture.onlineorder.security.SecurityUtils;

@Service
@Transactional
public class UserService {

	private final static Logger LOG = LoggerFactory.getLogger(UserService.class);
	private static final int DEF_COUNT = 20;
	@Inject
	UserRepository userRepository;
	@Inject
	PasswordEncoder passwordEncoder;
	@Inject
	AuthorityRepository authorityRepository;

	public User createUser(String login, String password, String firstName, String lastName, String email) {

		User newUser = new User();
		Authority authority = authorityRepository.findOneByName(Constants.USER);
		Set<Authority> authorities = new HashSet<>();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(login);
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setActivated(false);
		authorities.add(authority);
		newUser.setAuthorities(authorities);
		userRepository.save(newUser);
		LOG.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	public User createUser(UserDto userDto) {
		User user = new User();
		user.setLogin(userDto.getLogin());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		if (userDto.getAuthorities() != null) {
			Set<Authority> authorities = new HashSet<>();
			userDto.getAuthorities().forEach(authority -> authorities.add(authorityRepository.findOneByName(authority)));
			user.setAuthorities(authorities);
		}
		String encryptedPassword = passwordEncoder.encode("DEFAULT_PASSWORD");
		user.setPassword(encryptedPassword);
		user.setResetKey(generateResetKey());
		user.setResetDate(Instant.now());
		user.setActivated(true);
		userRepository.save(user);
		LOG.debug("Created Information for User: {}", user);
		return user;
	}

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName
	 *            first name of user
	 * @param lastName
	 *            last name of user
	 * @param email
	 *            email id of user
	 * @param langKey
	 *            language key
	 * @param imageUrl
	 *            image URL of user
	 */
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
		userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			LOG.debug("Changed Information for User: {}", user);
		});
	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDto
	 *            user to update
	 * @return updated user
	 */
	public Optional<UserDto> updateUser(UserDto userDto) {
		return Optional.of(userRepository.findOneById(userDto.getId())).map(user -> {
			user.setLogin(userDto.getLogin());
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setEmail(userDto.getEmail());
			user.setActivated(userDto.isActivated());
			Set<Authority> managedAuthorities = user.getAuthorities();
			managedAuthorities.clear();
			userDto.getAuthorities().stream().map(authorityRepository::findOneByName).forEach(managedAuthorities::add);
			LOG.debug("Changed Information for User: {}", user);
			return user;
		}).map(UserDto::new);
	}

	public void deleteUser(String login) {
		userRepository.findOneByLogin(login).ifPresent(user -> {
			userRepository.delete(user);
			LOG.debug("Deleted User: {}", user);
		});
	}

	public void changePassword(String password) {
		userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
			String encryptedPassword = passwordEncoder.encode(password);
			user.setPassword(encryptedPassword);
			LOG.debug("Changed password for User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public Page<UserDto> getAllManagedUsers(Pageable pageable) {
		return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDto::new);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneWithAuthoritiesByLogin(login);
	}

	@Transactional(readOnly = true)
	public User getUserWithAuthorities(Long id) {
		return userRepository.findOneWithAuthoritiesById(id);
	}

	@Transactional(readOnly = true)
	public User getUserWithAuthorities() {
		return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
	}

	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		List<User> users = userRepository
				.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
		for (User user : users) {
			LOG.debug("Deleting not activated user {}", user.getLogin());
			userRepository.delete(user);
		}
	}

	public List<String> getAuthorities() {
		return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
	}

	private String generateResetKey() {
		return RandomStringUtils.randomNumeric(DEF_COUNT);
	}
}
