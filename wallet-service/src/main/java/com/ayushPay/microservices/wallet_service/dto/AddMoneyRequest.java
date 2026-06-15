package com.ayushPay.microservices.wallet_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddMoneyRequest {
	
	private Long walletId;
	
	private BigDecimal amount;

}
