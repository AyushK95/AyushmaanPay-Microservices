package com.ayushPay.microservices.transaction_service.entity;

public enum TransactionsStatus {

    PENDING,

    SUCCESS,

    FAILED,

    ROLLBACK,

    COMPENSATING
}
