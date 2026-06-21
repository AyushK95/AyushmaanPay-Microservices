package com.ayushPay.microservices.wallet_service.service;

import java.math.BigDecimal;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ayushPay.microservices.wallet_service.dto.AddMoneyRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletRequest;
import com.ayushPay.microservices.wallet_service.dto.WalletResponse;
import com.ayushPay.microservices.wallet_service.entity.Wallet;
import com.ayushPay.microservices.wallet_service.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl  implements WalletService{

    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);
    private final WalletRepository walletRepository;

	@Override
	@Transactional
	public WalletResponse createWallet(WalletRequest request) 
	{
		if(walletRepository.existsByUserId(request.getUserId()))
		{
			throw new RuntimeException("Wallet is already exists for user");
		}
		Wallet wallet= Wallet.builder().userId(request.getUserId()).
				balance(BigDecimal.ZERO).build();
				
				Wallet savedWallet=walletRepository.save(wallet);
		return map(savedWallet);
	}

	@Override
	@Transactional
	public WalletResponse getWallet(
			Long walletId
	) {

		Wallet wallet =
				walletRepository
						.findById(walletId)
						.orElseThrow(
								() ->
										new RuntimeException(
												"Wallet not found"
										)
						);

		return map(wallet);
	}

	@Override
	@Transactional
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
	@Transactional
	public Wallet credit(
	        Long walletId,
	        BigDecimal amount) {

		log.info("Credit started for wallet{} amount{}", walletId, amount);

	    Wallet wallet = getWalletEntity(walletId);
		log.info("Balance before credit is {}", wallet.getBalance());

	    wallet.setBalance(
	            wallet.getBalance().add(amount));
        log.info("Balance after credit is {}", wallet.getBalance());
	    return walletRepository.save(wallet);
	}

	@Override
	@Transactional
	public Wallet rollback(Long walletId, BigDecimal amount) {
		Wallet wallet = getWalletEntity(walletId);
		wallet.setBalance(wallet.getBalance().add(amount));
		return walletRepository.save(wallet);
	}


	@Override
	@Transactional
	public Wallet debit(
	        Long walletId,
	        BigDecimal amount) {



	    Wallet wallet = getWalletEntity(walletId);
		System.out.println("Balance before debit is " +wallet.getBalance());

	    if (wallet.getBalance()
	            .compareTo(amount) < 0) {

	        throw new RuntimeException(
	                "Insufficient balance");
	    }

	    wallet.setBalance(
	            wallet.getBalance().subtract(amount));

		log.info("Balance after debit is {}", wallet.getBalance());
	    return walletRepository.save(wallet);


    }
	
	private Wallet getWalletEntity(Long walletId) {

	    return walletRepository.findById(walletId)
	            .orElseThrow(() ->
	                    new RuntimeException(
	                            "Wallet not found"));
	}


}
