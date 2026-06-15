package com.ayushPay.microservices.user_service.service;

import com.ayushPay.microservices.user_service.dto.UserRequest;
import com.ayushPay.microservices.user_service.dto.UserResponse;

public interface UserService {
	
	UserResponse createUser(UserRequest request);
	
	UserResponse getUser(Long id);

}
