package com.ayushPay.microservices.transaction_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ayushPay.microservices.transaction_service.dto.CreditRequest;
import com.ayushPay.microservices.transaction_service.dto.DebitRequest;
import com.ayushPay.microservices.transaction_service.dto.TransferRequest;
import com.ayushPay.microservices.transaction_service.entity.Transaction;
import com.ayushPay.microservices.transaction_service.entity.TransactionsStatus;
import com.ayushPay.microservices.transaction_service.repository.TransactionRepository;


import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Builder
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;

	private final WalletClient walletClient;

	@Override
	@Transactional
	public String transferMoney(TransferRequest request) {

		try {
			walletClient.debit(new DebitRequest(request.getSenderWalletId(), request.getAmount()));

			walletClient.credit(new CreditRequest(request.getReceiverWalletId(), request.getAmount()));

			Transaction transaction = Transaction.builder().senderWalletId(request.getSenderWalletId())
					.receiverWalletId(request.getReceiverWalletId()).amount(request.getAmount())
					.status(TransactionsStatus.SUCCESS).createdAt(LocalDateTime.now()).build();

			transactionRepository.save(transaction);

			return "Transfer Successful";
		} catch (Exception e) {

			Transaction transaction = Transaction.builder().senderWalletId(request.getSenderWalletId())
					.receiverWalletId(request.getReceiverWalletId()).amount(request.getAmount())
					.status(TransactionsStatus.FAILED).createdAt(LocalDateTime.now()).build();

			transactionRepository.save(transaction);

			throw e;
		}
	}
}
