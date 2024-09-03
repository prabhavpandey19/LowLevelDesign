package com.example.upiflipkartintegration.model;

import lombok.Data;

@Data
public class Bank {
    String bankName;
    String serverStatus;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.serverStatus = "up";
    }
}
