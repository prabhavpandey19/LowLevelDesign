package com.example.flipkart.model;
@Data
public class User {
    private Long userId
    String name;
    String phoneNumber;
    boolean active;
    List<BankAccount> bankAccounts;
    List<Transaction> transactions;

    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.active = true;
        this.bankAccounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }
}
