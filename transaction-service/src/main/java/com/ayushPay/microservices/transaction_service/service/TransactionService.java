package com.ayushPay.microservices.transaction_service.service;

import com.ayushPay.microservices.transaction_service.dto.TransferRequest;

public interface TransactionService {

	String transferMoney(TransferRequest transferRequest);
}
