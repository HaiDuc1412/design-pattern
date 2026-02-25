# Assignment 01: SOLID Principles Refactoring

### Overview

This project demonstrates the application of **SOLID Principles** to refactor a poorly designed e-commerce order processing system. The original code suffered from tight coupling, violation of Single Responsibility Principle, and lack of extensibility. This refactored version showcases clean architecture with proper separation of concerns.

### Problem Analysis

#### Original Issues

1. **God Class / SRP Violation**: `OrderService` handled too many responsibilities:
   - Product validation and stock management
   - Discount calculation with category-based logic
   - Payment processing for multiple payment methods
   - Order creation and management
   - Notifications (email, SMS, logging, analytics)

2. **OCP Violation**: Discount and payment logic used if-else chains, requiring code modification for new features

3. **Poor Domain Modeling**: Used `Object[]` arrays instead of type-safe domain models

4. **Tight Coupling**: Business logic, infrastructure concerns, and data storage were tightly coupled

### Solution Architecture

#### SOLID Principles Applied

##### 1. Single Responsibility Principle (SRP)
Each class has one clear responsibility:
- **Domain Models** (`Product`, `Order`, `OrderItem`): Represent business entities
- **DiscountService**: Manages discount calculations
- **PaymentProcessor**: Handles payment processing
- **StockManager**: Manages inventory
- **NotificationService**: Handles all notifications
- **OrderRepository**: Manages order persistence
- **OrderService**: Orchestrates the workflow

##### 2. Open/Closed Principle (OCP)
- **Strategy Pattern for Discounts**: New discount rules can be added without modifying existing code
  - `ElectronicsDiscountStrategy`: 10% off electronics
  - `ClothingDiscountStrategy`: 5% off clothing
  - `NoDiscountStrategy`: No discount for food
- **Strategy Pattern for Payments**: New payment methods can be added easily
  - `CreditCardPayment`: 2% processing fee
  - `PayPalPayment`: 3% processing fee
  - `BankTransferPayment`: No fee

##### 3. Liskov Substitution Principle (LSP)
- All discount strategies are interchangeable through `DiscountStrategy` interface
- All payment methods are interchangeable through `PaymentMethod` interface

##### 4. Interface Segregation Principle (ISP)
- Small, focused interfaces: `DiscountStrategy`, `PaymentMethod`, `NotificationService`, `StockManager`
- Classes only depend on methods they actually use

##### 5. Dependency Inversion Principle (DIP)
- High-level modules depend on abstractions (interfaces), not concrete implementations
- Dependencies injected through constructor for testability

### Project Structure

```
src/main/java/com/ecommerce/
├── Main.java                          # Entry point
├── OrderService.java                  # Main orchestrator
│
├── model/                             # Domain models
│   ├── Product.java
│   ├── Order.java
│   ├── OrderItem.java
│   └── enums/
│       ├── ProductCategory.java       # ELECTRONICS, CLOTHING, FOOD
│       └── OrderStatus.java           # CONFIRMED, CANCELLED, SHIPPED
│
├── discount/                          # Discount strategies (OCP)
│   ├── DiscountStrategy.java         # Interface
│   ├── DiscountService.java          # Strategy manager
│   ├── ElectronicsDiscountStrategy.java
│   ├── ClothingDiscountStrategy.java
│   └── NoDiscountStrategy.java
│
├── payment/                           # Payment strategies (OCP)
│   ├── PaymentMethod.java            # Interface
│   ├── PaymentProcessor.java         # Strategy manager
│   ├── CreditCardPayment.java
│   ├── PayPalPayment.java
│   └── BankTransferPayment.java
│
├── service/                           # Business services (SRP)
│   ├── NotificationService.java      # Interface
│   ├── StockManager.java             # Interface
│   └── impl/
│       ├── NotificationServiceImpl.java
│       └── StockManagerImpl.java
│
└── repository/                        # Data access (SRP)
    ├── OrderRepository.java
    └── ProductRepository.java
```

### How It Works

#### Order Creation Flow

1. **Validate Products**: Check if all products exist
2. **Reserve Stock**: Ensure products are available
3. **Calculate Discounts**: Apply category-specific discounts via Strategy Pattern
4. **Process Payment**: Handle payment with appropriate fee via Strategy Pattern
5. **Create Order**: Save order to repository
6. **Send Notifications**: Email confirmation, logging, analytics

#### Example Usage

```java
OrderService service = new OrderService();

String orderId = service.createOrder(
    "CUST-001",                          // Customer ID
    "john@example.com",                  // Email
    Arrays.asList("P001", "P002"),       // Product IDs
    "CREDIT_CARD",                       // Payment method
    "123 Main St, City"                  // Shipping address
);

service.shipOrder(orderId, "TRACK-12345");
```

#### Sample Products

| ID | Name | Price | Category | Discount |
|----|------|-------|----------|----------|
| P001 | Laptop | $999.99 | ELECTRONICS | 10% |
| P002 | T-Shirt | $29.99 | CLOTHING | 5% |
| P003 | Coffee Beans | $15.99 | FOOD | 0% |
| P004 | Headphones | $149.99 | ELECTRONICS | 10% |

### Running the Application

```bash
# Compile
javac -d bin src/main/java/com/ecommerce/**/*.java

# Run
java -cp bin com.ecommerce.Main
```

### Benefits Achieved

1. **Maintainability**: Each class has a single, clear responsibility
2. **Testability**: Components can be unit tested with mock dependencies
3. **Extensibility**: Add new discounts/payments without modifying existing code
4. **Type Safety**: Compile-time checking prevents runtime errors
5. **Readability**: Clear structure makes code easy to understand
6. **Flexibility**: Easy to swap implementations via dependency injection

### Adding New Features

#### Add a New Discount Strategy

```java
public class BooksDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(Product product, double price) {
        return price * 0.85; // 15% off books
    }
}

// Register in DiscountService constructor
discountStrategies.put(ProductCategory.BOOKS, new BooksDiscountStrategy());
```

#### Add a New Payment Method

```java
public class CryptoPayment implements PaymentMethod {
    @Override
    public double calculateFee(double amount) {
        return amount * 0.01; // 1% fee
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing crypto payment: $" + amount);
        return true;
    }
}

// Register in PaymentProcessor constructor
paymentMethods.put("CRYPTO", new CryptoPayment());
```
---
**Author**: HaiDD69  
**Date**: February 2026  
**Purpose**: Assignment 01 - Design Patterns & SOLID Principles
