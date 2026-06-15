package com.ayushPay.microservices.wallet_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequest {
	
	private Long walletId;
	
	
	private BigDecimal amount;
	

}
