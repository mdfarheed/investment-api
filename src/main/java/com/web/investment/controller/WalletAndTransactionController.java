package com.web.investment.controller;

import com.web.investment.model.Wallet;
// import com.web.investment.model.TransactionHistory;
import com.web.investment.service.WalletService;
import com.web.investment.service.TransactionService;
import com.web.investment.dto.TransactionHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletAndTransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    // Get Wallet Balance API
    @GetMapping("/balance/{userId}")
public ResponseEntity<Double> getWalletBalance(@PathVariable Long userId) {
    try {
        // Attempt to fetch the balance
        double balance = walletService.getWalletBalance(userId);

        // If the balance is found, return it
        return ResponseEntity.ok(balance);
    } catch (Exception e) {
        // If the wallet is not found, return 200 OK with 0 as the balance
        return ResponseEntity.status(200).body(0.0);
    }
}


    // Deposit Money API (and log the transaction)
    @PostMapping("/deposit/{userId}")
    public ResponseEntity<String> depositMoney(@PathVariable Long userId, @RequestBody TransactionHistoryDTO transactionRequest) {
        try {
            double amount = transactionRequest.getAmount();
            if (amount <= 0) {
                return ResponseEntity.status(400).body("Deposit amount must be positive.");
            }
    
            // Update wallet balance
            Wallet updatedWallet = walletService.depositMoney(userId, amount);
    
            // Record transaction in TransactionHistory
            transactionService.recordTransaction(userId, amount, "DEPOSIT");
    
            return ResponseEntity.ok("Successfully deposited " + amount + ". New balance: " + updatedWallet.getBalance());
        } catch (RuntimeException e) {
            // Handle specific exception cases
            return ResponseEntity.status(400).body("Failed to deposit money: " + e.getMessage());
        } catch (Exception e) {
            // General exception catch
            return ResponseEntity.status(500).body("Internal server error. Please try again.");
        }
    }
    

    // Withdraw Money API (and log the transaction)
    @PostMapping("/withdraw/{userId}")
public ResponseEntity<String> withdrawMoney(@PathVariable Long userId, @RequestBody TransactionHistoryDTO transactionRequest) {
    try {
        double amount = transactionRequest.getAmount();
        if (amount <= 0) {
            return ResponseEntity.status(400).body("Withdraw amount must be positive.");
        }

        // Update wallet balance
        Wallet updatedWallet = walletService.withdrawMoney(userId, amount);

        // Record transaction in TransactionHistory
        transactionService.recordTransaction(userId, amount, "WITHDRAW");

        return ResponseEntity.ok("Successfully withdrew " + amount + ". New balance: " + updatedWallet.getBalance());
    } catch (Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
    // Get Transaction History API
    @GetMapping("/transaction/history/{userId}")
public ResponseEntity<?> getTransactionHistory(@PathVariable Long userId) {
    try {
        // Fetch the transaction history as DTOs
        List<TransactionHistoryDTO> transactionHistoryList = transactionService.getTransactionHistory(userId);

        if (transactionHistoryList.isEmpty()) {
            return ResponseEntity.status(200).body("No Transactions");
        }

        // Return the simplified response
        return ResponseEntity.ok(transactionHistoryList);
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body("User not found: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(500).body("An error occurred while retrieving transaction history.");
    }
}

}
