package com.web.investment.service;

import com.web.investment.model.TransactionHistory;
import com.web.investment.model.Wallet;
import com.web.investment.model.User;
import com.web.investment.repository.TransactionHistoryRepository;
import com.web.investment.repository.WalletRepository;
import com.web.investment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ProfitService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    /**
     * Adds profit to the user's wallet and creates a transaction history entry.
     *
     * @param userId       ID of the user
     * @param profitAmount Profit amount to be added
     */
    @Transactional
    public void addProfitToWallet(Long userId, double profitAmount) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch or create the wallet for the user
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for the user"));

        // Add profit to the wallet balance
        wallet.setBalance(wallet.getBalance() + profitAmount);
        walletRepository.save(wallet); // Save updated wallet balance

        // Create and save transaction history entry for profit
        TransactionHistory transaction = new TransactionHistory();
        transaction.setUser(user);
        transaction.setType(TransactionHistory.TransactionType.PROFIT); // Set transaction type to PROFIT
        transaction.setAmount(profitAmount); // Set profit amount
        transaction.setTimestamp(Instant.now()); // Set current timestamp
        transactionHistoryRepository.save(transaction); // Save transaction history
    }
}

