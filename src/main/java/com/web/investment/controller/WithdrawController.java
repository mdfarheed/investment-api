package com.web.investment.controller;

import com.web.investment.model.Withdraw;
import com.web.investment.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/withdraws")
public class WithdrawController {

    @Autowired
    private WalletService walletService;

    @GetMapping
    public ResponseEntity<?> getAllWithdraws() {
        try {
            List<Withdraw> withdraws = walletService.getAllWithdraws();
            
            if (withdraws.isEmpty()) {
                // If no withdrawals are found, return a message
                return ResponseEntity.status(HttpStatus.OK).body("No withdrawals");
            }
    
            return ResponseEntity.ok(withdraws);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fetching withdrawals");
        }
    }
    

    // Delete Withdraw Entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWithdraw(@PathVariable("id") Long id) {
        try {
            boolean isDeleted = walletService.deleteWithdraw(id);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Withdraw entry deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Withdraw entry not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting");
        }
    }
}
