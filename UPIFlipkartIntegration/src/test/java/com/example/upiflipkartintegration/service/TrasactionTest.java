package com.example.upiflipkartintegration.service;

import com.example.upiflipkartintegration.enums.TransactionStatus;
import com.example.upiflipkartintegration.model.Transaction;
import com.example.upiflipkartintegration.model.User;
import com.example.upiflipkartintegration.service.impl.BankServiceImpl;
import com.example.upiflipkartintegration.service.impl.TrasactionServiceImpl;
import com.example.upiflipkartintegration.service.impl.UpiServiceImpl;
import com.example.upiflipkartintegration.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TrasactionTest {
    @InjectMocks
    private TrasactionServiceImpl transactionService;
    @InjectMocks
    private UpiServiceImpl upiService;
    @InjectMocks
    private BankServiceImpl bankService;
    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = userService.userOnboard("TestUser1","1234567890");
        user2 = userService.userOnboard("TestUser2","1234567891");

        bankService.addBanks("TestBank1");
        bankService.addBanks("TestBank2");

        upiService.linkUpiAccount(user1 , "TestBank1", "1234567890", "999999999");
        upiService.linkUpiAccount(user2 , "TestBank2", "1234567891", "888888888");
    }

    @Test
    public void testSendMoney() {
        Transaction transaction = transactionService.sendMoney(user1, "1234567891", null, null, 1000);
        assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);
    }

}
