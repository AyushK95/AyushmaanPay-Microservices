package com.ayushPay.microservices.wallet_service.service;

import java.math.BigDecimal;

import com.ayushPay.microservices.wallet_service.dto.AddMoneyRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletResponse;
import com.ayushPay.microservices.wallet_service.entity.Wallet;

public interface WalletService {

	WalletResponse createWallet(WalletRequest request);

	WalletResponse getWallet(Long walletId);

	WalletResponse addMoney(AddMoneyRequest addrequest);

	Wallet debit(Long walletId, BigDecimal amount);

	Wallet credit(Long walletId, BigDecimal amount);

	public Wallet rollback(Long walletId, BigDecimal amount);

}
