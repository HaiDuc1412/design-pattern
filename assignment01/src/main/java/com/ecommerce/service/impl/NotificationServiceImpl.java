package com.ecommerce.service.impl;

import com.ecommerce.service.NotificationService;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendOrderConfirmationEmail(String email, String orderId, double total) {
        System.out.println("Sending email to " + email + "...");
        System.out.println("Subject: Order Confirmed - " + orderId);
        System.out.println("Body: Your order total is $" + String.format("%.2f", total));
    }

    @Override
    public void sendOrderCancellationEmail(String orderId) {
        System.out.println("[EMAIL] Your order " + orderId + " has been cancelled.");
    }

    @Override
    public void sendShippingNotification(String orderId, String trackingNumber) {
        System.out.println("[EMAIL] Your order " + orderId + " has been shipped!");
        System.out.println("Tracking: " + trackingNumber);
        System.out.println("[SMS] Order shipped: " + trackingNumber);
    }

    @Override
    public void logOrderCreation(String orderId, String customerId) {
        System.out.println("[LOG] Order created: " + orderId + " for customer " + customerId);
    }

    @Override
    public void logOrderCancellation(String orderId) {
        System.out.println("[LOG] Order cancelled: " + orderId);
    }

    @Override
    public void logOrderShipment(String orderId) {
        System.out.println("[LOG] Order shipped: " + orderId);
    }

    @Override
    public void recordAnalytics(double total, String paymentMethod) {
        System.out.println("[ANALYTICS] New order: $" + total + " via " + paymentMethod);
    }
}
