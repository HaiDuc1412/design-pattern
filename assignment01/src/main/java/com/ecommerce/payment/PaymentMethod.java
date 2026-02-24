package com.ecommerce.payment;

public interface PaymentMethod {

    boolean processPayment(double amount);

    double calculateFee(double amount);
}
