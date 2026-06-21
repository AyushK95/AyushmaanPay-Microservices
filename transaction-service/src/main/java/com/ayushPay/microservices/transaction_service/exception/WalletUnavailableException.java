package com.ayushPay.microservices.transaction_service.exception;

public class WalletUnavailableException extends RuntimeException{

    public  WalletUnavailableException(String message)
    {
        super(message);
    }
}
