package com.ecommerce.discount;

import com.ecommerce.model.Product;

public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(Product product, double price) {
        return price;
    }
}
