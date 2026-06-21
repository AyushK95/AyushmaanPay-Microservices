package com.ayushPay.microservices.transaction_service.exception;

public class CompensationException extends RuntimeException
{
	public CompensationException(String message)
	{
		super(message);
	}
}
