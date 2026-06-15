package com.ayushPay.microservices.user_service.dto;

import lombok.Data;

@Data
public class UserRequest {
	
	private String mobileNumber;
	
	private String fullName;
	
	private String  email;

	
}
