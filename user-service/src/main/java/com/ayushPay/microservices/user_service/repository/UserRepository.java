package com.ayushPay.microservices.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushPay.microservices.user_service.entity.User;

public interface UserRepository  extends JpaRepository<User, Long>{
	boolean existsByMobileNumber(String mobileNumber);
	
	Optional<User> findByMobileNumber(String mobileNumber);

}
