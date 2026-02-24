package com.ecommerce.discount;

import com.ecommerce.model.Product;

public class ClothingDiscountStrategy implements DiscountStrategy {

    private static final double DISCOUNT_RATE = 0.10;

    @Override
    public double applyDiscount(Product product, double price) {
        return price * (1 - DISCOUNT_RATE);
    }
}
