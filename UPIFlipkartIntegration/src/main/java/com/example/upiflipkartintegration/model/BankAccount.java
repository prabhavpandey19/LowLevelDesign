package com.example.upiflipkartintegration.model;

import lombok.Data;

@Data
public class BankAccount {
    String bankName;
    String accountNumber;
    String cardNumber;
    String ifscCode;
    double balance;
    boolean isActive;

    public BankAccount(String bankName, String accountNumber, double balance, String cardNumber, String ifscCode) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.ifscCode = ifscCode;
        this.isActive = true;
    }
}
