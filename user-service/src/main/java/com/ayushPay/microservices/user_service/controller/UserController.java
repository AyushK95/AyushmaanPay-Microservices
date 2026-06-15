package com.ayushPay.microservices.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushPay.microservices.user_service.dto.UserRequest;
import com.ayushPay.microservices.user_service.dto.UserResponse;
import com.ayushPay.microservices.user_service.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser( @RequestBody UserRequest request)
	{
		return ResponseEntity.ok(userService.createUser(request));
		
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUsers( @PathVariable Long  id)
	{
		return ResponseEntity.ok(userService.getUser(id));
		
	}
}
