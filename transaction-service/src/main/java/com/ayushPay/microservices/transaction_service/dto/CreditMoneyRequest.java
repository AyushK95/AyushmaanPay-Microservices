package com.ayushPay.microservices.transaction_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditMoneyRequest {

    private Long userId;

    private BigDecimal amount;

}