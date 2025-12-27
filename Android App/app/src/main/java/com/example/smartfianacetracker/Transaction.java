package com.example.smartfianacetracker;

public class Transaction {
    private String type;
    private double amount;
    private String accountNumber;
    private String merchantName;
    private String transactionMode;
    private String upiId;
    private long timestamp;
    private long createdAt;

    public Transaction() {
        this.type = "";
        this.amount = 0.0;
        this.accountNumber = "";
        this.merchantName = "";
        this.transactionMode = "";
        this.upiId = "";
        this.timestamp = System.currentTimeMillis();
        this.createdAt = System.currentTimeMillis();
    }

    public Transaction(String type, double amount, String accountNumber, String merchantName,
                      String transactionMode, String upiId) {
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.merchantName = merchantName;
        this.transactionMode = transactionMode;
        this.upiId = upiId;
        this.timestamp = System.currentTimeMillis();
        this.createdAt = System.currentTimeMillis();
    }

    // Getters
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getAccountNumber() { return accountNumber; }
    public String getMerchantName() { return merchantName; }
    public String getTransactionMode() { return transactionMode; }
    public String getUpiId() { return upiId; }
    public long getTimestamp() { return timestamp; }
    public long getCreatedAt() { return createdAt; }

    // Setters
    public void setType(String type) { this.type = type; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
    public void setTransactionMode(String transactionMode) { this.transactionMode = transactionMode; }
    public void setUpiId(String upiId) { this.upiId = upiId; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
