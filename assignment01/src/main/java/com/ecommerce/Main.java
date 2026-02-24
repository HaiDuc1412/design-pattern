package com.ecommerce;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        
        String orderId = service.createOrder(
            "CUST-001",
            "john@example.com",
            Arrays.asList("P001", "P002"),
            "CREDIT_CARD",
            "123 Main St, City"
        );
        
        if (orderId != null) {
            System.out.println("\n=== Order Created: " + orderId + " ===\n");
            service.shipOrder(orderId, "TRACK-12345");
        }
        
        System.out.println("\n=== All Orders ===");
        service.printAllOrders();
    }
}
