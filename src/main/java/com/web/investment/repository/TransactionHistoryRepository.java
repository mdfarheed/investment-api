package com.web.investment.repository;

import com.web.investment.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    // Find all transactions by user id (useful to fetch a user's transaction history)
    List<TransactionHistory> findByUserId(Long userId);

    // Find all transactions by transaction type (use the enum type here)
    List<TransactionHistory> findByType(TransactionHistory.TransactionType type);
}
