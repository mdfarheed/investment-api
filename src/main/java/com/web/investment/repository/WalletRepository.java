package com.web.investment.repository;

import com.web.investment.model.Wallet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userId);  // Custom method to find Wallet by userId
}





