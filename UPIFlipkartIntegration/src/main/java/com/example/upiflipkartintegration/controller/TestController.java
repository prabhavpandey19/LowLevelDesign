package com.example.upiflipkartintegration.controller;

import com.example.upiflipkartintegration.model.User;
import com.example.upiflipkartintegration.service.BankService;
import com.example.upiflipkartintegration.service.TrasactionService;
import com.example.upiflipkartintegration.service.UpiService;
import com.example.upiflipkartintegration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class TestController {
    private final UpiService upiService;
    private final UserService userService;
    private final BankService bankService;
    private final TrasactionService trasactionService;

    private User user1;
    private User user2;
    private User user3;
    private boolean initflag = true;

    @GetMapping("/test")
    public void test() {
        if (initflag) {
            init();
            initflag = false;
        }
        System.out.println("User1: " + user1.getPrimaryUpiAccount().getBankAccount().getBalance());
        System.out.println("User2: " + user2.getPrimaryUpiAccount().getBankAccount().getBalance());
        System.out.println("User3: " + user3.getPrimaryUpiAccount().getBankAccount().getBalance());

        Runnable runnable = () -> {
            try {
                trasactionService.sendMoney(user1, "1234567891", null, null, 1000);
//                trasactionService.sendMoney(user3, "1234567891", null, null, 2000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        };
        runnable.run();

        System.out.println("Trasaction1" + user1.getSentTransactions());
        System.out.println("Trasaction2" + user2.getRecivedTransactions());
        System.out.println("Trasaction3" + user3.getSentTransactions());
        System.out.println("User1: " + user1.getPrimaryUpiAccount().getBankAccount().getBalance());
        System.out.println("User2: " + user2.getPrimaryUpiAccount().getBankAccount().getBalance());
        System.out.println("User3: " + user3.getPrimaryUpiAccount().getBankAccount().getBalance());
    }

    private void init() {
        user1 = userService.userOnboard("TestUser1","1234567890");
        user2 = userService.userOnboard("TestUser2","1234567891");
        user3 = userService.userOnboard("TestUser3","1234567892");
        user1.setActive(false);

        bankService.addBanks("TestBank1");
        bankService.addBanks("TestBank2");
        bankService.addBanks("TestBank3");

        upiService.linkUpiAccount(user1 , "TestBank1", "1234567890", "999999999");
        upiService.linkUpiAccount(user2 , "TestBank2", "1234567891", "888888888");
        upiService.linkUpiAccount(user3 , "TestBank3", "1234567892", "777777777");
    }
}
