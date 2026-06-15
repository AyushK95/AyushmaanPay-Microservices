package com.ayushPay.microservices.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String mobileNumber;
    private String email;
}