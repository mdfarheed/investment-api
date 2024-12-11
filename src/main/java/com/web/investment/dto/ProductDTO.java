package com.web.investment.dto;

// import java.time.Instant;

public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private int time;  // Time in minutes

    // Constructor
    public ProductDTO(Long id, String name, double price, int time) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.time = time;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
