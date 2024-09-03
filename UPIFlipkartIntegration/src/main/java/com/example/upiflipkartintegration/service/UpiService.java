package com.example.upiflipkartintegration.service;

import com.example.upiflipkartintegration.model.UpiAccount;
import com.example.upiflipkartintegration.model.User;

public interface UpiService {
    UpiAccount linkUpiAccount(User user, String bankName, String accountNumber, String carnNumber);

    User getUpiAccount(String upiId);
}
