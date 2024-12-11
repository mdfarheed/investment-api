package com.web.investment.controller;

import com.web.investment.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profit")
public class ProfitController {

    @Autowired
    private ProfitService profitService;

    @PostMapping("/add")
    public ResponseEntity<String> addProfit(@RequestBody ProfitRequest profitRequest) {
        profitService.addProfitToWallet(profitRequest.getUserId(), profitRequest.getProfitAmount());
        return ResponseEntity.ok("Profit added successfully!");
    }
    
    // DTO Class
    public static class ProfitRequest {
        private Long userId;
        private double profitAmount;
    
        // Getters and Setters
        public Long getUserId() {
            return userId;
        }
    
        public void setUserId(Long userId) {
            this.userId = userId;
        }
    
        public double getProfitAmount() {
            return profitAmount;
        }
    
        public void setProfitAmount(double profitAmount) {
            this.profitAmount = profitAmount;
        }
    }
    
}
