package com.greenfurniture.onlineorder.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

	public String getMessage(String name) {
		return "Welcome, " + name + "\nThe quick brown fox jumps over the lazy dog";
	}
}
