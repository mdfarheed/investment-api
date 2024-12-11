package com.web.investment.dto;

import java.time.Instant;

public class TransactionHistoryDTO {
    private Long id;
    private String type;
    private double amount;
    private Instant timestamp;
    private Long userId;  // Add userId field

    // Constructor
    public TransactionHistoryDTO(Long id, String type, double amount, Instant timestamp, Long userId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.userId = userId;  // Initialize userId in the constructor
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {  // Add getter for userId
        return userId;
    }

    public void setUserId(Long userId) {  // Add setter for userId
        this.userId = userId;
    }
}
