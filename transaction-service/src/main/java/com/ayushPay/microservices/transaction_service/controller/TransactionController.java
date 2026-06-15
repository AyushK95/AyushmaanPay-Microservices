package com.ayushPay.microservices.transaction_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushPay.microservices.transaction_service.dto.TransferRequest;
import com.ayushPay.microservices.transaction_service.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public String transferMoney(
            @RequestBody TransferRequest request){

        return transactionService
                .transferMoney(request);
    }
}