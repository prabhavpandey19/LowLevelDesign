package com.example.upiflipkartintegration.service;

import com.example.upiflipkartintegration.model.User;

public interface UserService {
    User userOnboard(String name, String phoneNumber);
    User getUserByPhoneNumber(String phoneNumber);
    User getByUpiId(String upiId);
    User byAccountNumber(String accountNumber);
}
