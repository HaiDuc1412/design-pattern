package com.ecommerce.discount;

import com.ecommerce.model.Product;

public interface DiscountStrategy {

    double applyDiscount(Product product, double price);
}
