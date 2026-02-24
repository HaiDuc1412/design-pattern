package com.ecommerce.payment;

public class PayPalPayment implements PaymentMethod {

    private static final double FEE_RATE = 0.025;
    private static final double SUCCESS_RATE = 0.95;

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Redirecting to PayPal...");
        return Math.random() > (1 - SUCCESS_RATE);
    }

    @Override
    public double calculateFee(double amount) {
        return amount * FEE_RATE;
    }
}
