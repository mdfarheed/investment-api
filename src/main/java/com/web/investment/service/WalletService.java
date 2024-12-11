package com.web.investment.service;

import com.web.investment.model.BankInfo;
import com.web.investment.model.Wallet;
import com.web.investment.model.Withdraw;
import com.web.investment.repository.WalletRepository;
import com.web.investment.repository.WithdrawRepository;

import jakarta.transaction.Transactional;

import com.web.investment.repository.BankInfoRepository;
import com.web.investment.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

     @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private BankInfoRepository bankInfoRepository;


    public Wallet depositMoney(Long userId, double amount) {
        Wallet wallet = walletRepository.findByUserId(userId).orElse(null);
        if (wallet == null) {
            // Create a new wallet if it doesn't exist
            wallet = new Wallet();
            wallet.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found")));
            wallet.setBalance(0); // Initialize balance
        }
        wallet.setBalance(wallet.getBalance() + amount); // Add deposit amount
        return walletRepository.save(wallet); // Save wallet
    }
    

    // Withdraw Money from Wallet
    @Transactional
    public Wallet withdrawMoney(Long userId, double amount) {
        // Fetch the user's wallet
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // Check for sufficient balance
        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }

        // Deduct the amount from the wallet balance
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);

        // Fetch the user's bank information
        BankInfo bankInfo = bankInfoRepository.findByUserId(userId);
        if (bankInfo == null) {
            throw new RuntimeException("Please add bank before withdarw");
        }

        // Create and save a new Withdraw entry
        Withdraw withdraw = new Withdraw();
        withdraw.setUserId(userId);
        withdraw.setAmount(amount);
        withdraw.setHolderName(bankInfo.getHolderName());
        withdraw.setAccountNumber(bankInfo.getAccountNumber());
        withdraw.setIfscCode(bankInfo.getIfscCode());
        withdraw.setPhoneNumber(bankInfo.getPhoneNumber());
        withdrawRepository.save(withdraw);

        return wallet; // Return the updated wallet
    }
    

    public List<Withdraw> getAllWithdraws() {
        return withdrawRepository.findAll();
    }


    public boolean deleteWithdraw(Long id) {
        Withdraw withdraw = withdrawRepository.findById(id).orElse(null);
        if (withdraw != null) {
            withdrawRepository.delete(withdraw);
            return true;
        }
        return false;  // Return false if the withdraw entry doesn't exist
    }
    
    // Get Wallet Balance
    public double getWalletBalance(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }
}
