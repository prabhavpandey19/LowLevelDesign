package com.example.upiflipkartintegration.service.impl;

import com.example.upiflipkartintegration.enums.TransactionStatus;
import com.example.upiflipkartintegration.exception.InsufficientMoneyException;
import com.example.upiflipkartintegration.exception.UserNotActiveException;
import com.example.upiflipkartintegration.exception.UserNotFoundException;
import com.example.upiflipkartintegration.model.BankAccount;
import com.example.upiflipkartintegration.model.Transaction;
import com.example.upiflipkartintegration.model.User;
import com.example.upiflipkartintegration.service.TrasactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class TrasactionServiceImpl implements TrasactionService {
    private final UserServiceImpl userService;
    private final UpiServiceImpl upiService;
    private final BankServiceImpl bankService;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public Transaction sendMoney(User currentUser, String receiverPhoneNumber, String upiId, String accountNumber, double amount) {
        lock.lock();
        try {
            User receiver;
            if (upiId != null) {
                receiver = upiService.getUpiAccount(upiId);
            } else if (receiverPhoneNumber != null) {
                receiver = userService.getUserByPhoneNumber(receiverPhoneNumber);
            } else {
                receiver = userService.byAccountNumber(accountNumber);
            }
            if (receiver == null) {
                throw new UserNotFoundException("Receiver not found");
            }

            if (!currentUser.isActive()) {
                throw new UserNotActiveException("Sender is not active");
            }

            if (!receiver.isActive()) {
                throw new UserNotActiveException("Receiver is not active");
            }

            // Check if the receiver is valid


            Transaction transaction = new Transaction(currentUser.getName(), receiver.getName(), amount);
            BankAccount senderAccount = currentUser.getPrimaryUpiAccount().getBankAccount();
            BankAccount receiverAccount = receiver.getPrimaryUpiAccount().getBankAccount();

            // Check if the sender has sufficient balance
            if (senderAccount.getBalance() < amount) {
                transaction.setStatus(TransactionStatus.FAILED);
                currentUser.addSentTransaction(transaction);
                receiver.addRecivedTransaction(transaction);
                throw new InsufficientMoneyException("Insufficient balance");
            }

            // Process the transaction
            String status = bankService.processTransaction(receiverAccount, senderAccount, amount);
            if (status.equals("Success")) {
                transaction.setStatus(TransactionStatus.COMPLETED);
            } else {
                transaction.setStatus(TransactionStatus.FAILED);
            }

            currentUser.addSentTransaction(transaction);
            receiver.addRecivedTransaction(transaction);
            return transaction;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Transaction> getSentTransactions(User currentUser) {
        return currentUser.getSentTransactions();
    }

    @Override
    public List<Transaction> getReceivedTransactions(User currentUser) {
        return currentUser.getRecivedTransactions();
    }

    @Override
    public List<Transaction> getTransactionsBySender(User currentUser, String senderName) {
        List<Transaction> result = new ArrayList<>();
        List<Transaction> transactions = currentUser.getRecivedTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getSender().equals(senderName)) {
                result.add(transaction);
            }
        }
        transactions = currentUser.getSentTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getReceiver().equals(senderName)) {
                result.add(transaction);
            }
        }
        return result;
    }
}
