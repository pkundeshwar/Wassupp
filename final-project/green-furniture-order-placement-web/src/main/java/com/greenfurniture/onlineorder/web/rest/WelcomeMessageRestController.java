package com.greenfurniture.onlineorder.web.rest;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenfurniture.onlineorder.service.MessageService;

@RestController
@RequestMapping("/api/rest")
public class WelcomeMessageRestController {
	@Inject
	MessageService messageService;

	@GetMapping(value = "/welcome", produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> welcome(@RequestParam(value = "name") String name) throws URISyntaxException {
		return new ResponseEntity<>(messageService.getMessage(name), HttpStatus.OK);

	}

}
