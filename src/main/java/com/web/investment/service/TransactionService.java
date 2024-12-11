package com.web.investment.service;

import com.web.investment.dto.TransactionHistoryDTO;
import com.web.investment.model.TransactionHistory;
import com.web.investment.model.TransactionHistory.TransactionType;
import com.web.investment.model.User;
import com.web.investment.repository.TransactionHistoryRepository;
import com.web.investment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private UserRepository userRepository;
    

    // Record a Transaction (Deposit, Withdraw, Purchase)
    public void recordTransaction(Long userId, double amount, String transactionTypeString) {
        // Fetch the User object
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new transaction entry
        TransactionHistory transaction = new TransactionHistory();
        transaction.setUser(user);  // Set the User object here
        transaction.setTimestamp(Instant.ofEpochMilli(System.currentTimeMillis()));
        transaction.setAmount(amount);

        // Set the transaction type using the provided string value
        try {
            transaction.setType(TransactionType.valueOf(transactionTypeString.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid transaction type: " + transactionTypeString);
        }

        // Save transaction in the database
        transactionHistoryRepository.save(transaction);
    }

    // Get Transaction History for a User
    public List<TransactionHistoryDTO> getTransactionHistory(Long userId) {
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findByUserId(userId);
    
        return transactionHistoryList.stream()
                .map(transaction -> {
                    TransactionHistoryDTO dto = new TransactionHistoryDTO(userId, null, userId, null, userId);
                    dto.setId(transaction.getId());
                    dto.setType(transaction.getType().toString());
                    dto.setAmount(transaction.getAmount());
                    dto.setTimestamp(transaction.getTimestamp());
    
                    // Null check for transaction.getUser()
                    if (transaction.getUser() != null) {
                        dto.setUserId(transaction.getUser().getId());  // Set the user ID
                    } else {
                        System.out.println("Warning: User is null for transaction ID: " + transaction.getId());
                        // You can either handle the null case or set a default value for userId
                        dto.setUserId(null);  // Set userId to null or some default value
                    }
    
                    return dto;
                })
                .collect(Collectors.toList());
            }
}
