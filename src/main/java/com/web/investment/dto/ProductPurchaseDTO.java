package com.web.investment.dto;

import java.time.Instant;

// import java.time.Instant;

public class ProductPurchaseDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private double price;
    private Instant purchaseTime; // Timestamp of purchase
    private int duration; // Countdown time for the product
    private String productName;

    // Constructor
    public ProductPurchaseDTO(Long id, Long userId, Long productId, double price, Instant purchaseTime, int duration, String productName) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.duration = duration;
        this.productName = productName; 
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Instant purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
