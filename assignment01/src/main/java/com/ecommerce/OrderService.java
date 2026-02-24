package com.ecommerce;

import com.ecommerce.discount.DiscountService;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.payment.PaymentProcessor;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.NotificationService;
import com.ecommerce.service.StockManager;
import com.ecommerce.service.impl.NotificationServiceImpl;
import com.ecommerce.service.impl.StockManagerImpl;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private DiscountService discountService;
    private PaymentProcessor paymentProcessor;
    private StockManager stockManager;
    private NotificationService notificationService;

    public OrderService() {
        this.productRepository = new ProductRepository();
        this.orderRepository = new OrderRepository();
        this.discountService = new DiscountService();
        this.paymentProcessor = new PaymentProcessor();
        this.stockManager = new StockManagerImpl(productRepository);
        this.notificationService = new NotificationServiceImpl();
    }

    // Constructor for Dependency Injection (better for testing)
    public OrderService(ProductRepository productRepository,
            OrderRepository orderRepository,
            DiscountService discountService,
            PaymentProcessor paymentProcessor,
            StockManager stockManager,
            NotificationService notificationService) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.discountService = discountService;
        this.paymentProcessor = paymentProcessor;
        this.stockManager = stockManager;
        this.notificationService = notificationService;
    }

    public String createOrder(String customerId, String customerEmail, List<String> productIds,
            String paymentMethod, String shippingAddress) {
        // Validate products
        List<Product> products = validateProducts(productIds);
        if (products == null) {
            return null;
        }

        // Reserve stock
        if (!stockManager.reserveStock(productIds)) {
            System.out.println("ERROR: Failed to reserve stock");
            return null;
        }

        // Calculate total with discounts
        double subtotal = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Product product : products) {
            double discountedPrice = discountService.calculateDiscountedPrice(product);
            subtotal += discountedPrice;
            orderItems.add(new OrderItem(product.getId(), product.getName(), discountedPrice));
        }

        // Apply payment method fee
        double total = paymentProcessor.calculateTotalWithFee(subtotal, paymentMethod);

        // Process payment
        boolean paymentSuccess = paymentProcessor.processPayment(total, paymentMethod);

        if (!paymentSuccess) {
            System.out.println("ERROR: Payment failed!");
            stockManager.releaseStock(productIds);
            return null;
        }

        // Create and save order
        String orderId = generateOrderId();
        Order order = new Order(orderId, customerId, customerEmail, orderItems, total, shippingAddress);
        orderRepository.save(order);

        // Send notifications
        notificationService.sendOrderConfirmationEmail(customerEmail, orderId, total);
        notificationService.logOrderCreation(orderId, customerId);
        notificationService.recordAnalytics(total, paymentMethod);

        return orderId;
    }

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId);

        if (order == null) {
            System.out.println("ERROR: Order not found: " + orderId);
            return;
        }

        if (order.getStatus() != OrderStatus.CONFIRMED) {
            System.out.println("ERROR: Cannot cancel order in status: " + order.getStatus());
            return;
        }

        // Update order status
        orderRepository.updateOrderStatus(orderId, OrderStatus.CANCELLED);

        // Release stock
        List<String> productIds = order.getItems().stream()
                .map(OrderItem::getProductId)
                .toList();
        stockManager.releaseStock(productIds);

        // Send notifications
        notificationService.sendOrderCancellationEmail(orderId);
        notificationService.logOrderCancellation(orderId);
    }

    public void shipOrder(String orderId, String trackingNumber) {
        Order order = orderRepository.findById(orderId);

        if (order == null) {
            return;
        }

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            orderRepository.updateOrderStatus(orderId, OrderStatus.SHIPPED);
            notificationService.sendShippingNotification(orderId, trackingNumber);
            notificationService.logOrderShipment(orderId);
        }
    }

    public void printAllOrders() {
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            System.out.println("Order: " + order.getOrderId()
                    + " - Status: " + order.getStatus()
                    + " - Total: $" + order.getTotal());
        }
    }

    private List<Product> validateProducts(List<String> productIds) {
        List<Product> products = new ArrayList<>();

        for (String productId : productIds) {
            Product product = productRepository.findById(productId);
            if (product == null) {
                System.out.println("ERROR: Product not found: " + productId);
                return null;
            }
            if (!product.isInStock()) {
                System.out.println("ERROR: Out of stock: " + product.getName());
                return null;
            }
            products.add(product);
        }

        return products;
    }

    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }
}
