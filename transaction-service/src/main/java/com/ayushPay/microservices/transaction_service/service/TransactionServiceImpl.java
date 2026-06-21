package com.ayushPay.microservices.transaction_service.service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import com.ayushPay.microservices.transaction_service.dto.CreditRequest;
import com.ayushPay.microservices.transaction_service.dto.TransactionResponse;
import com.ayushPay.microservices.transaction_service.entity.TransactionsStatus;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import com.ayushPay.microservices.transaction_service.dto.DebitRequest;
import com.ayushPay.microservices.transaction_service.dto.TransferRequest;
import com.ayushPay.microservices.transaction_service.entity.Transaction;
import com.ayushPay.microservices.transaction_service.repository.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import static java.awt.SystemColor.info;

@Service
@RequiredArgsConstructor
@Builder
public class TransactionServiceImpl implements TransactionService {

    public  static Logger logger  = Logger.getLogger(TransactionServiceImpl.class.getName());

    private final TransactionRepository transactionRepository;

    private final WalletClient walletClient;

    @Override
    @Transactional
    @CircuitBreaker(
            name = "walletService",
            fallbackMethod = "fallbackTransfer"
    )
    @Retry(name = "walletRetry")
    public TransactionResponse transferMoney(
            TransferRequest request
    ) {

        Transaction transaction =
                createTransaction(request);

        boolean debitDone = false;

        try {
            logger.info("Transfer Started");

            walletClient.debit(
                    new DebitRequest(
                            request.getSenderWalletId(),
                            request.getAmount()
                    )
            );
            logger.info("Debit is completed");

            debitDone = true;

            logger.info("Credit Started");
            walletClient.credit(
                    new CreditRequest(
                            request.getReceiverWalletId(),
                            request.getAmount()
                    )
            );
            logger.info("Credit is completed");

            // SUCCESS ONLY
            transaction.setStatus(
                    TransactionsStatus.SUCCESS
            );

            transactionRepository.save(
                    transaction
            );

            return buildResponse(
                    transaction
            );

        }
        catch (Exception ex) {

            if (debitDone) {
                compensate(
                        transaction,
                        request
                );
            }

            transaction.setStatus(
                    TransactionsStatus.FAILED
            );

            transactionRepository.save(
                    transaction
            );

            throw ex;
        }

    }
    public TransactionResponse fallbackTransfer(

            TransferRequest request,

            Exception ex

    ) {

        Transaction failed =

                Transaction.builder()

                        .senderWalletId(
                                request.getSenderWalletId()
                        )

                        .receiverWalletId(
                                request.getReceiverWalletId()
                        )

                        .amount(
                                request.getAmount()
                        )

                        .status(
                                TransactionsStatus.FAILED
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        transactionRepository.save(
                failed
        );

        return TransactionResponse
                .builder()

                .message(
                        "Wallet service unavailable"
                )

                .status(
                        "FAILED"
                )

                .build();
    }

    private Transaction process(
            Transaction transaction,
            TransferRequest request) {

            walletClient.debit(
                    new DebitRequest(
                            request.getSenderWalletId(),
                            request.getAmount()
                    )
            );

            walletClient.credit(
                    new CreditRequest(
                            request.getReceiverWalletId(),
                            request.getAmount()
                    )
            );

            transaction.setStatus(
                    TransactionsStatus.SUCCESS
            );

            compensate(
                    transaction,
                    request
            );
            return transactionRepository.save(
                transaction
        );
    }
    private void compensate(Transaction transaction, TransferRequest request) {
        try {

            transaction.setStatus(TransactionsStatus.COMPENSATING);
            transactionRepository.save(transaction);
            walletClient.rollback(new CreditRequest(request.getUserId(), request.getAmount()));

            transaction.setStatus(TransactionsStatus.ROLLBACK);
        }
        catch (Exception e) {
            transaction.setStatus(TransactionsStatus.FAILED);

            throw e;
        }
       transactionRepository.save(transaction);
    }

    //Helper method for the Response of TransferMoney method
    private TransactionResponse buildResponse(
            Transaction transaction
    ) {

        return TransactionResponse
                .builder()

                .transactionId(
                        transaction.getId()
                )

                .message(
                        transaction.getStatus()
                                ==
                                TransactionsStatus.SUCCESS

                                ?

                                "Money transferred successfully"

                                :

                                "Transfer Failed"
                )

                .status(
                        transaction
                                .getStatus()
                                .name()
                )

                .senderWalletId(
                        transaction
                                .getSenderWalletId()
                )

                .receiverWalletId(
                        transaction
                                .getReceiverWalletId()
                )

                .amount(
                        transaction
                                .getAmount()
                )

                .createdAt(
                        transaction
                                .getCreatedAt()
                )

                .build();
    }

    private Transaction createTransaction(TransferRequest request) {

        Transaction transaction = Transaction.builder()
                .senderWalletId(request.getSenderWalletId())
                .receiverWalletId(request.getReceiverWalletId())
                .amount(request.getAmount())
                .createdAt(LocalDateTime.now())
                .status(TransactionsStatus.PENDING)
                .build();

        return transactionRepository.save(transaction);
    }
}
