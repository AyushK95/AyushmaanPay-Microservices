package com.ayushPay.microservices.wallet_service.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletResponse {
	
	private Long walletId;
	
	private Long userId;
	
	private BigDecimal balance;

}
