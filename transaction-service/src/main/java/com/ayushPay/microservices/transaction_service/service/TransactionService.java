package com.ayushPay.microservices.transaction_service.service;

import com.ayushPay.microservices.transaction_service.dto.TransactionResponse;
import com.ayushPay.microservices.transaction_service.dto.TransferRequest;

import java.math.BigDecimal;

public interface TransactionService {

	TransactionResponse transferMoney(TransferRequest transferRequest);


}
