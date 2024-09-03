package com.example.upiflipkartintegration.service.impl;

import com.example.upiflipkartintegration.model.BankAccount;
import com.example.upiflipkartintegration.model.UpiAccount;
import com.example.upiflipkartintegration.model.User;
import com.example.upiflipkartintegration.service.UpiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class UpiServiceImpl implements UpiService {
    private Map<String, User> upiIdvsUser;
    private final BankServiceImpl bankService;

    private Map<String, UpiAccount> phoneNoIdvsUser;


    @Override
    public UpiAccount linkUpiAccount(User user, String bankName, String accountNumber, String cardNumber) {
        BankAccount bankAccount = bankService.getBankAccount(bankName, accountNumber, cardNumber); // accountNumber or cardNumber is required to get details of bank account
        UpiAccount upiAccount = new UpiAccount(user.getPhoneNumber(), user, bankAccount);
        user.addUpiAccount(upiAccount);
        user.addBankAccount(bankAccount);
        if (user.getPrimaryUpiAccount() == null) {
            user.setPrimaryUpiAccount(upiAccount);
        }
        if (upiIdvsUser == null) {
            upiIdvsUser = new HashMap<>();
        }
        upiIdvsUser.put(upiAccount.getUpiId(), user);
        if (phoneNoIdvsUser == null) {
            phoneNoIdvsUser = new HashMap<>();
        }
        if (phoneNoIdvsUser.get(user.getPhoneNumber()) == null) {
            phoneNoIdvsUser.put(user.getPhoneNumber(), upiAccount);
        }
        return upiAccount;
    }

    @Override
    public User getUpiAccount(String upiId) {
        if (upiIdvsUser != null) {
            return upiIdvsUser.get(upiId);
        }
        return null;
    }



}
