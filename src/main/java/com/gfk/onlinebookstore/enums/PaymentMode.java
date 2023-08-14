package com.gfk.onlinebookstore.enums;

public enum PaymentMode {
    DEBIT_CARD("Debit Card"),CREDIT_CARD("Credit Card"),CASH("Cash");

    private String mode;
    PaymentMode(String modeName) {
        this.mode = modeName;
    }
}
