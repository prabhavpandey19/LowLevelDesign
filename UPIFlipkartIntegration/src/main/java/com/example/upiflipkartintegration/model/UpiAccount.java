package com.example.upiflipkartintegration.model;

import lombok.Data;

import java.util.Random;

@Data
public class UpiAccount {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    String upiId;
    String phoneNumber;
    BankAccount bankAccount;

    public UpiAccount(String phoneNumber, User user, BankAccount bankAccount) {
        this.upiId = generateUPI() + "@phonepe";
        this.phoneNumber = phoneNumber;
        this.bankAccount = bankAccount;
    }


    public static String generateUPI() {
        StringBuilder upi = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            upi.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return upi.toString();
    }
}
