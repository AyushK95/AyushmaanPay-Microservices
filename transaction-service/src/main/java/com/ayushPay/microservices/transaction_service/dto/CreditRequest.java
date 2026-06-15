package com.ayushPay.microservices.transaction_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditRequest {
	
	private Long walletId;
	
	private BigDecimal amount;
	

}
