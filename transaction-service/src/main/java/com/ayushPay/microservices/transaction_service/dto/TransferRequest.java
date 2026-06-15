package com.ayushPay.microservices.transaction_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferRequest {

	private Long senderWalletId;

	private Long receiverWalletId;

	private BigDecimal amount;

}
