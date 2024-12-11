package com.web.investment.controller;

import com.web.investment.model.BankInfo;
import com.web.investment.service.BankInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankInfoController {

    @Autowired
    private BankInfoService bankInfoService;

    // Add Bank Information
@PostMapping("/add/{userId}")
public ResponseEntity<BankInfo> addBankInfo(@PathVariable Long userId, @RequestBody BankInfo bankInfo) {
    BankInfo savedBankInfo = bankInfoService.addBankInfo(userId, bankInfo);
    if (savedBankInfo != null) {
        return ResponseEntity.ok(savedBankInfo); // Send back the saved BankInfo
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle if user not found
    }
}

    // Update Bank Information
    @PutMapping("/update/{userId}")
    public BankInfo updateBankInfo(@PathVariable Long userId, @RequestBody BankInfo bankInfo) {
        return bankInfoService.updateBankInfo(userId, bankInfo);
    }

    // Get Bank Information
    @GetMapping("/get/{userId}")
    public BankInfo getBankInfo(@PathVariable Long userId) {
        return bankInfoService.getBankInfo(userId);
    }

    // Check Bank Information Status
    @GetMapping("/status/{userId}")
    public boolean checkBankInfoStatus(@PathVariable Long userId) {
        return bankInfoService.checkBankInfoStatus(userId);
    }
}
