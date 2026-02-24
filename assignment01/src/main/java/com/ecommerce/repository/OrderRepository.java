package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.enums.OrderStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {

    private Map<String, Order> orders;

    public OrderRepository() {
        this.orders = new HashMap<>();
    }

    public void save(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Order findById(String orderId) {
        return orders.get(orderId);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    public boolean updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            return true;
        }
        return false;
    }
}
