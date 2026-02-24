package com.ecommerce.payment;

public class CreditCardPayment implements PaymentMethod {

    private static final double FEE_RATE = 0.03;
    private static final double SUCCESS_RATE = 0.9;

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment...");
        return Math.random() > (1 - SUCCESS_RATE);
    }

    @Override
    public double calculateFee(double amount) {
        return amount * FEE_RATE;
    }
}
