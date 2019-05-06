package com.greenfurniture.onlineorder.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenfurniture.onlineorder.constant.Constants;
import com.greenfurniture.onlineorder.domain.User;
import com.greenfurniture.onlineorder.dto.ManagedUserDto;
import com.greenfurniture.onlineorder.dto.UserDto;
import com.greenfurniture.onlineorder.jparepository.UserRepository;
import com.greenfurniture.onlineorder.service.UserService;
import com.greenfurniture.onlineorder.web.util.HeaderUtil;
import com.greenfurniture.onlineorder.web.util.PaginationUtil;


@RestController
@RequestMapping("/api")
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private static final String ENTITY_NAME = "userManagement";

    UserRepository userRepository;
    UserService userService;

    @PostMapping("/users")
    @Secured(Constants.ADMIN)
    public ResponseEntity<?> createUser(@Valid @RequestBody ManagedUserDto managedUserDto) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserDto);

        if (managedUserDto.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                .body(null);
        // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserDto.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(managedUserDto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            User newUser = userService.createUser(managedUserDto);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    @PutMapping("/users")
    @Secured(Constants.ADMIN)
    public ResponseEntity<?> updateUser(@Valid @RequestBody ManagedUserDto managedUserDto) {
        log.debug("REST request to update User : {}", managedUserDto);
        Optional<User> existingUser = userRepository.findOneByEmail(managedUserDto.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserDto.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(managedUserDto.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserDto.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<UserDto> updatedUser = userService.updateUser(managedUserDto);

        return new ResponseEntity<>(
        	updatedUser,
            HeaderUtil.createAlert("A user is updated with identifier " + managedUserDto.getLogin(), managedUserDto.getLogin()),
            HttpStatus.OK
            );
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(Pageable pageable) {
        final Page<UserDto> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/authorities")
    @Secured(Constants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<?> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return new ResponseEntity<>(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDto::new), HttpStatus.OK);
    }

    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Secured(Constants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + login, login)).build();
    }
}
