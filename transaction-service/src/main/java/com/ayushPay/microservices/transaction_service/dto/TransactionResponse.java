package com.ayushPay.microservices.transaction_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {

    private Long transactionId;

    private String message;

    private String status;

    private Long senderWalletId;

    private Long receiverWalletId;

    private BigDecimal amount;

    private LocalDateTime createdAt;
}