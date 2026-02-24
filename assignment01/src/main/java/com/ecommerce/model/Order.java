package com.ecommerce.model;

import com.ecommerce.model.enums.OrderStatus;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private String orderId;
    private String customerId;
    private String customerEmail;
    private List<OrderItem> items;
    private double total;
    private OrderStatus status;
    private String shippingAddress;

    public Order(String orderId, String customerId, String customerEmail,
            List<OrderItem> items, double total, String shippingAddress) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.items = new ArrayList<>(items);
        this.total = total;
        this.status = OrderStatus.CONFIRMED;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }
}
