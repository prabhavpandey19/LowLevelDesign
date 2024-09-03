package com.example.upiflipkartintegration.service.impl;

import com.example.upiflipkartintegration.exception.BankNotActiveException;
import com.example.upiflipkartintegration.exception.InsufficientMoneyException;
import com.example.upiflipkartintegration.model.Bank;
import com.example.upiflipkartintegration.model.BankAccount;
import com.example.upiflipkartintegration.service.BankService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankServiceImpl implements BankService {
    List<Bank> banks;

    @Override
    public void addBanks(String bankName) {
        if (banks == null) {
            banks = new ArrayList<>();
        }
        Bank bank = new Bank(bankName);
        banks.add(bank);
    }

    @Override
    public List<Bank> getBanks() {
        return banks == null ? new ArrayList<>() : banks;
    }

    @Override
    public BankAccount getBankAccount(String bankName, String accountNumber, String cardNumber) {
        return new BankAccount(bankName, accountNumber, 5000, cardNumber, "IFSC1234");
    }

    @Override
    public String processTransaction(BankAccount receiverAccount, BankAccount senderAccount, double amount) {
        if (!receiverAccount.isActive()) {
            throw new BankNotActiveException("Receiver account is not active");
        }
        if (!senderAccount.isActive()) {
            throw new BankNotActiveException("Sender account is not active");
        }
        if (senderAccount.getBalance() < amount) {
            throw new InsufficientMoneyException("Insufficient balance");
        }
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        return "Success";
    }
}
