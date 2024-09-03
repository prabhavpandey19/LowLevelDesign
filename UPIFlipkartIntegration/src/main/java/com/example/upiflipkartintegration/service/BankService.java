package com.example.upiflipkartintegration.service;

import com.example.upiflipkartintegration.model.Bank;
import com.example.upiflipkartintegration.model.BankAccount;

import java.util.List;

public interface BankService {
    void addBanks(String bankName);
    List<Bank> getBanks();

    BankAccount getBankAccount(String bankName, String accountNumber, String cardNumber);

    String processTransaction(BankAccount receiverAccount, BankAccount senderAccount, double amount);

}
