package com.ayushPay.microservices.transaction_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ayushPay.microservices.transaction_service.dto.CreditRequest;
import com.ayushPay.microservices.transaction_service.dto.DebitRequest;



@FeignClient(name="wallet-service")
public interface WalletClient {

	@PostMapping("/api/wallets/debit")
	void debit(@RequestBody DebitRequest debitRequest);
	
	@PostMapping("/api/wallets/credit")
	void credit(@RequestBody CreditRequest creditRequest);

	@PostMapping("/api/wallets/rollback")
	void rollback(@RequestBody CreditRequest creditRequest);
}
