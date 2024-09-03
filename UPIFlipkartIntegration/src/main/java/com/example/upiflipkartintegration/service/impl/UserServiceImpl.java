package com.example.upiflipkartintegration.service.impl;

import com.example.upiflipkartintegration.exception.UserNotFoundException;
import com.example.upiflipkartintegration.model.UpiAccount;
import com.example.upiflipkartintegration.model.User;
import com.example.upiflipkartintegration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private List<User> users;
    private final BankServiceImpl bankService;
    private final UpiServiceImpl upiService;

    @Override
    public User userOnboard(String name, String phoneNumber) {
        if (users == null) {
            users = new ArrayList<>();
        }
        User user = new User(name, phoneNumber);
        users.add(user);
        return user;
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user;
            }
        }
        throw new UserNotFoundException("User not found with phoneNumber: " + phoneNumber);
    }

    @Override
    public User getByUpiId(String upiId) {
        User user = upiService.getUpiAccount(upiId);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("User not found with upiId: " + upiId);
        }
    }

    @Override
    public User byAccountNumber(String accountNumber) {
        for (User user : users) {
            for (int i = 0; i < user.getBankAccounts().size(); i++) {
                if (user.getBankAccounts().get(i).getAccountNumber().equals(accountNumber)) {
                    return user;
                }
            }
        }
        throw new UserNotFoundException("User not found with accountNumber: " + accountNumber);
    }
}
