package com.ayushPay.microservices.transaction_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushPay.microservices.transaction_service.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
