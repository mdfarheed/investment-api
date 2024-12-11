package com.web.investment.service;

import com.web.investment.model.BankInfo;
import com.web.investment.model.User;
import com.web.investment.repository.BankInfoRepository;
import com.web.investment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankInfoService {

    @Autowired
    private BankInfoRepository bankInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public BankInfo addBankInfo(Long userId, BankInfo bankInfo) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            bankInfo.setUser(user); // Set the user to the bank info
            bankInfoRepository.save(bankInfo); // Save the bank info
            return bankInfo; // Return the saved bank info so it can be sent in the response
        }
        return null; // Return null if user is not found
    }

    // Update Bank Information
    public BankInfo updateBankInfo(Long userId, BankInfo bankInfo) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            BankInfo existingBankInfo = user.getBankInfo();
            if (existingBankInfo != null) {
                // Update the existing fields
                existingBankInfo.setHolderName(bankInfo.getHolderName());
                existingBankInfo.setAccountNumber(bankInfo.getAccountNumber());
                existingBankInfo.setIfscCode(bankInfo.getIfscCode());
                existingBankInfo.setPhoneNumber(bankInfo.getPhoneNumber());
                // Save updated bank info
                return bankInfoRepository.save(existingBankInfo);
            } else {
                throw new RuntimeException("No bank info found to update.");
            }
        }
        throw new RuntimeException("User not found.");
    }

    // Get Bank Information
    public BankInfo getBankInfo(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getBankInfo();
        }
        return null; // No bank info found
    }

    // Check Bank Information Status
    public boolean checkBankInfoStatus(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null && user.getBankInfo() != null;
    }
}
