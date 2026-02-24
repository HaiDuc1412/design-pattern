package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Object[]> orders = new ArrayList<>();
    private List<Object[]> products = new ArrayList<>();

    public OrderService() {
        // Initialize sample products: [id, name, price, stock, category]
        products.add(new Object[]{"P001", "Laptop", 999.99, 50, "ELECTRONICS"});
        products.add(new Object[]{"P002", "T-Shirt", 29.99, 200, "CLOTHING"});
        products.add(new Object[]{"P003", "Coffee Beans", 15.99, 100, "FOOD"});
        products.add(new Object[]{"P004", "Headphones", 149.99, 75, "ELECTRONICS"});
    }

    public String createOrder(String customerId, String customerEmail, List<String> productIds, 
                               String paymentMethod, String shippingAddress) {
        // Validate products and calculate total
        double total = 0;
        List<Object[]> orderItems = new ArrayList<>();
        
        for (String productId : productIds) {
            Object[] product = findProduct(productId);
            if (product == null) {
                System.out.println("ERROR: Product not found: " + productId);
                return null;
            }
            int stock = (Integer) product[3];
            if (stock <= 0) {
                System.out.println("ERROR: Out of stock: " + product[1]);
                return null;
            }
            
            double price = (Double) product[2];
            String category = (String) product[4];
            
            // Apply category-specific discounts
            if (category.equals("ELECTRONICS") && price > 500) {
                price = price * 0.95; // 5% off expensive electronics
            } else if (category.equals("CLOTHING")) {
                price = price * 0.90; // 10% off all clothing
            } else if (category.equals("FOOD")) {
                // No discount for food
            }
            
            total += price;
            orderItems.add(new Object[]{productId, product[1], price});
            
            // Update stock
            product[3] = stock - 1;
        }
        
        // Apply payment method fee
        if (paymentMethod.equals("CREDIT_CARD")) {
            total = total * 1.03; // 3% credit card fee
        } else if (paymentMethod.equals("PAYPAL")) {
            total = total * 1.025; // 2.5% PayPal fee
        } else if (paymentMethod.equals("BANK_TRANSFER")) {
            // No fee for bank transfer
        }
        
        // Process payment
        boolean paymentSuccess = false;
        if (paymentMethod.equals("CREDIT_CARD")) {
            System.out.println("Processing credit card payment...");
            paymentSuccess = Math.random() > 0.1; // 90% success rate simulation
        } else if (paymentMethod.equals("PAYPAL")) {
            System.out.println("Redirecting to PayPal...");
            paymentSuccess = Math.random() > 0.05; // 95% success rate
        } else if (paymentMethod.equals("BANK_TRANSFER")) {
            System.out.println("Waiting for bank transfer confirmation...");
            paymentSuccess = true; // Always succeeds (manual verification later)
        }
        
        if (!paymentSuccess) {
            System.out.println("ERROR: Payment failed!");
            // Restore stock
            for (String productId : productIds) {
                Object[] product = findProduct(productId);
                product[3] = (Integer) product[3] + 1;
            }
            return null;
        }
        
        // Create order
        String orderId = "ORD-" + System.currentTimeMillis();
        orders.add(new Object[]{orderId, customerId, orderItems, total, "CONFIRMED", shippingAddress});
        
        // Send notifications
        System.out.println("Sending email to " + customerEmail + "...");
        System.out.println("Subject: Order Confirmed - " + orderId);
        System.out.println("Body: Your order total is $" + String.format("%.2f", total));
        
        // Log to file (simulated)
        System.out.println("[LOG] Order created: " + orderId + " for customer " + customerId);
        
        // Update analytics
        System.out.println("[ANALYTICS] New order: $" + total + " via " + paymentMethod);
        
        return orderId;
    }

    public void cancelOrder(String orderId) {
        for (Object[] order : orders) {
            if (order[0].equals(orderId)) {
                if (order[4].equals("CONFIRMED")) {
                    order[4] = "CANCELLED";
                    
                    // Restore stock
                    List<Object[]> items = (List<Object[]>) order[2];
                    for (Object[] item : items) {
                        Object[] product = findProduct((String) item[0]);
                        product[3] = (Integer) product[3] + 1;
                    }
                    
                    System.out.println("[EMAIL] Your order " + orderId + " has been cancelled.");
                    System.out.println("[LOG] Order cancelled: " + orderId);
                } else {
                    System.out.println("ERROR: Cannot cancel order in status: " + order[4]);
                }
                return;
            }
        }
        System.out.println("ERROR: Order not found: " + orderId);
    }

    public void shipOrder(String orderId, String trackingNumber) {
        for (Object[] order : orders) {
            if (order[0].equals(orderId)) {
                if (order[4].equals("CONFIRMED")) {
                    order[4] = "SHIPPED";
                    System.out.println("[EMAIL] Your order " + orderId + " has been shipped!");
                    System.out.println("Tracking: " + trackingNumber);
                    System.out.println("[SMS] Order shipped: " + trackingNumber);
                    System.out.println("[LOG] Order shipped: " + orderId);
                }
                return;
            }
        }
    }

    private Object[] findProduct(String productId) {
        for (Object[] product : products) {
            if (product[0].equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void printAllOrders() {
        for (Object[] order : orders) {
            System.out.println("Order: " + order[0] + " - Status: " + order[4] + " - Total: $" + order[3]);
        }
    }
}
