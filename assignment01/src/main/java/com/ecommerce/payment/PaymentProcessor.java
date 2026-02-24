package com.ecommerce.payment;

import java.util.HashMap;
import java.util.Map;

public class PaymentProcessor {

    private Map<String, PaymentMethod> paymentMethods;

    public PaymentProcessor() {
        paymentMethods = new HashMap<>();
        paymentMethods.put("CREDIT_CARD", new CreditCardPayment());
        paymentMethods.put("PAYPAL", new PayPalPayment());
        paymentMethods.put("BANK_TRANSFER", new BankTransferPayment());
    }

    public PaymentMethod getPaymentMethod(String paymentMethodType) {
        return paymentMethods.get(paymentMethodType);
    }

    public double calculateTotalWithFee(double amount, String paymentMethodType) {
        PaymentMethod method = getPaymentMethod(paymentMethodType);
        if (method != null) {
            return amount + method.calculateFee(amount);
        }
        return amount;
    }

    public boolean processPayment(double amount, String paymentMethodType) {
        PaymentMethod method = getPaymentMethod(paymentMethodType);
        if (method != null) {
            return method.processPayment(amount);
        }
        return false;
    }
}
