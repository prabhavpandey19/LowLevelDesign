package com.example.upiflipkartintegration.service;

import com.example.upiflipkartintegration.model.Transaction;
import com.example.upiflipkartintegration.model.User;

import java.util.List;

public interface TrasactionService {
    Transaction sendMoney(User currentUser, String receiverPhoneNumber, String upiId, String accountNumber,double amount);
    List<Transaction> getSentTransactions(User currentUser);
    List<Transaction> getReceivedTransactions(User currentUser);
    List<Transaction> getTransactionsBySender(User currentUser, String senderName);
}
