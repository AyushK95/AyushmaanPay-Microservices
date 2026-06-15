package com.ayushPay.microservices.wallet_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushPay.microservices.wallet_service.dto.AddMoneyRequest;
import com.ayushPay.microservices.wallet_service.dto.CreditRequest;
import com.ayushPay.microservices.wallet_service.dto.DebitRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletResponse;
import com.ayushPay.microservices.wallet_service.service.WalletService;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

	private final WalletService walletService;

	public WalletController(WalletService walletService) {
		super();
		this.walletService = walletService;
	}

	@PostMapping
	public ResponseEntity<WalletResponse> createWallet(@RequestBody WalletRequest request) {
		return ResponseEntity.ok(walletService.createWallet(request));

	}

	@GetMapping("/{id}")
	public ResponseEntity<WalletResponse> getWallet(@PathVariable Long id) {
		return ResponseEntity.ok(walletService.getWallet(id));

	}

	@PostMapping("/addMoney")
	public ResponseEntity<WalletResponse> addMoney(@RequestBody AddMoneyRequest addRequest) {
		return ResponseEntity.ok(walletService.addMoney(addRequest));
	}

	@PostMapping("/debit")
	public ResponseEntity<Void> debit(@RequestBody DebitRequest request) {

		walletService.debit(request.getWalletId(), request.getAmount());

		return ResponseEntity.ok().build();
	}

	// NEW
	@PostMapping("/credit")
	public ResponseEntity<Void> credit(@RequestBody CreditRequest request) {

		walletService.credit(request.getWalletId(), request.getAmount());

		return ResponseEntity.ok().build();
	}

}
