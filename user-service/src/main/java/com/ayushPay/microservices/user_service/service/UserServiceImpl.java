package com.ayushPay.microservices.user_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ayushPay.microservices.user_service.dto.UserRequest;
import com.ayushPay.microservices.user_service.dto.UserResponse;
import com.ayushPay.microservices.user_service.entity.User;
import com.ayushPay.microservices.user_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserResponse createUser(UserRequest request) {
		if(userRepository.existsByMobileNumber(request.getMobileNumber()))
	
			throw new RuntimeException("Mobile Number already existed");
		
	User user= User.builder().fullName(request.getFullName())
			.mobileNumber(request.getMobileNumber()).
			email(request.getEmail()).
			createdAt(LocalDateTime.now()).build();
	
	System.out.println("Request Full Name : " + request.getFullName());
	System.out.println("Request Mobile    : " + request.getMobileNumber());
	System.out.println("Request Email     : " + request.getEmail());
	
	 User savedUser= userRepository.save(user);
		return UserResponse.builder().
				id(savedUser.getId()).
				mobileNumber(savedUser.getMobileNumber())
				.email(savedUser.getEmail()).
				fullName(savedUser.getFullName()).
				build();
	}

	@Override
	public UserResponse getUser(Long id) {
		
		User user =userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		return UserResponse.builder().
				id(user.getId()).
				mobileNumber(user.getMobileNumber())
				.email(user.getEmail()).
				fullName(user.getFullName()).
				build();
		
		
	
	}
	
	
	

}
