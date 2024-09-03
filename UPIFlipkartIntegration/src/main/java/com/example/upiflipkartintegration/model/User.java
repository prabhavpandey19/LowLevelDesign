package com.example.upiflipkartintegration.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private Long userId;
    private String name;
    private String phoneNumber;
    private boolean active;
    private UpiAccount primaryUpiAccount;
    private List<BankAccount> bankAccounts;
    private List<Transaction> recivedTransactions;
    private List<Transaction> sentTransactions;

    private List<UpiAccount> upiAccounts;

    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.active = true;
        this.bankAccounts = new ArrayList<>();
        this.upiAccounts = new ArrayList<>();
        this.recivedTransactions = new ArrayList<>();
        this.sentTransactions = new ArrayList<>();
    }

    public void addBankAccount(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
    }

    public void addRecivedTransaction(Transaction transaction) {
        this.recivedTransactions.add(transaction);
    }

    public void addSentTransaction(Transaction transaction) {
        this.sentTransactions.add(transaction);
    }

    public void addUpiAccount(UpiAccount upiAccount) {
        this.upiAccounts.add(upiAccount);
    }
}
