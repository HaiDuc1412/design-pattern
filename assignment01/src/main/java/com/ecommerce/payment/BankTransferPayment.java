package com.ecommerce.payment;

public class BankTransferPayment implements PaymentMethod {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Waiting for bank transfer confirmation...");
        return true;
    }

    @Override
    public double calculateFee(double amount) {
        return 0;
    }
}
