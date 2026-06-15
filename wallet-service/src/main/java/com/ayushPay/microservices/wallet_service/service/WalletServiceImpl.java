package com.ayushPay.microservices.wallet_service.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.ayushPay.microservices.wallet_service.dto.AddMoneyRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletResponse;
import com.ayushPay.microservices.wallet_service.entity.Wallet;
import com.ayushPay.microservices.wallet_service.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl  implements WalletService{
	
	private final WalletRepository walletRepository;

	@Override
	public WalletResponse createWallet(WalletRequest request) {
		Wallet wallet= Wallet.builder().userId(request.getUserId()).
				balance(BigDecimal.ZERO).build();
				
				Wallet savedWallet=walletRepository.save(wallet);
		return map(savedWallet);
	}

	@Override
	public WalletResponse getWallet(Long walletId) {
		
		Wallet wallet=walletRepository.findByUserId(walletId).orElseThrow();
		
		return map(wallet);
	}

	@Override
	public WalletResponse addMoney(AddMoneyRequest addrequest) {
		
		Wallet wallet= walletRepository.findById(addrequest.getWalletId()). 
				orElseThrow();
		
		wallet.setBalance(wallet.getBalance().add(addrequest.getAmount()));
		
		return map(walletRepository.save(wallet));
	}

	
	//Utility method to map all the results and display it in the response
	private WalletResponse map(Wallet wallet)
	{
		return WalletResponse.builder().walletId(wallet.getWalletId()).
				userId(wallet.getUserId()).balance(wallet.getBalance()).build();
		
	}
	
	@Override
	public Wallet credit(
	        Long walletId,
	        BigDecimal amount) {

	    Wallet wallet = getWalletEntity(walletId);

	    wallet.setBalance(
	            wallet.getBalance().add(amount));

	    return walletRepository.save(wallet);
	}


	@Override
	public Wallet debit(
	        Long walletId,
	        BigDecimal amount) {

	    Wallet wallet = getWalletEntity(walletId);

	    if (wallet.getBalance()
	            .compareTo(amount) < 0) {

	        throw new RuntimeException(
	                "Insufficient balance");
	    }

	    wallet.setBalance(
	            wallet.getBalance().subtract(amount));

	    return walletRepository.save(wallet);
	}
	
	private Wallet getWalletEntity(Long walletId) {

	    return walletRepository.findById(walletId)
	            .orElseThrow(() ->
	                    new RuntimeException(
	                            "Wallet not found"));
	}
}
