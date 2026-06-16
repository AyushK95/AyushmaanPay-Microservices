package com.ayushPay.microservices.wallet_service.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="wallets", uniqueConstraints = {
        @UniqueConstraint(
                name = "UK_USER_ID",
                columnNames = "user_id"
            )
        })
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long walletId;
	
	@Column(name="user_id",unique = true)
	private Long userId;
	
	@Column(nullable = false)
	private BigDecimal balance;
	
	private Long version;
	
	
	
}