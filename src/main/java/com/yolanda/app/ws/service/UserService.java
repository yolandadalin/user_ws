package com.yolanda.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.yolanda.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
}
