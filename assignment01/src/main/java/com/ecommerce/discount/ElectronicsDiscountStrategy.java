package com.ecommerce.discount;

import com.ecommerce.model.Product;

public class ElectronicsDiscountStrategy implements DiscountStrategy {

    private static final double EXPENSIVE_THRESHOLD = 500.0;
    private static final double DISCOUNT_RATE = 0.05;

    @Override
    public double applyDiscount(Product product, double price) {
        if (price > EXPENSIVE_THRESHOLD) {
            return price * (1 - DISCOUNT_RATE);
        }
        return price;
    }
}
