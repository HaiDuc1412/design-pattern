package com.ecommerce.service;

public interface NotificationService {

    void sendOrderConfirmationEmail(String email, String orderId, double total);

    void sendOrderCancellationEmail(String orderId);

    void sendShippingNotification(String orderId, String trackingNumber);

    void logOrderCreation(String orderId, String customerId);

    void logOrderCancellation(String orderId);

    void logOrderShipment(String orderId);

    void recordAnalytics(double total, String paymentMethod);
}
