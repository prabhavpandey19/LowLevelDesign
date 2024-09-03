package com.example.upiflipkartintegration.model;

import com.example.upiflipkartintegration.enums.TransactionStatus;
import com.example.upiflipkartintegration.enums.TransactionType;
import lombok.Data;

@Data
public class Transaction {
    String sender;
    String receiver;
    double amount;
    TransactionStatus status;

    public Transaction(String sender, String receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.status = TransactionStatus.INPROGRESS;
    }
}
