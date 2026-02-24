package com.ecommerce.discount;

import com.ecommerce.model.Product;
import com.ecommerce.model.enums.ProductCategory;
import java.util.HashMap;
import java.util.Map;

public class DiscountService {

    private Map<ProductCategory, DiscountStrategy> discountStrategies;

    public DiscountService() {
        discountStrategies = new HashMap<>();
        discountStrategies.put(ProductCategory.ELECTRONICS, new ElectronicsDiscountStrategy());
        discountStrategies.put(ProductCategory.CLOTHING, new ClothingDiscountStrategy());
        discountStrategies.put(ProductCategory.FOOD, new NoDiscountStrategy());
    }

    public double calculateDiscountedPrice(Product product) {
        DiscountStrategy strategy = discountStrategies.getOrDefault(
                product.getCategory(),
                new NoDiscountStrategy()
        );
        return strategy.applyDiscount(product, product.getPrice());
    }
}
