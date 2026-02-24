package com.ecommerce.repository;

import com.ecommerce.model.Product;
import com.ecommerce.model.enums.ProductCategory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    private Map<String, Product> products;

    public ProductRepository() {
        products = new HashMap<>();
        initializeProducts();
    }

    private void initializeProducts() {
        products.put("P001", new Product("P001", "Laptop", 999.99, 50, ProductCategory.ELECTRONICS));
        products.put("P002", new Product("P002", "T-Shirt", 29.99, 200, ProductCategory.CLOTHING));
        products.put("P003", new Product("P003", "Coffee Beans", 15.99, 100, ProductCategory.FOOD));
        products.put("P004", new Product("P004", "Headphones", 149.99, 75, ProductCategory.ELECTRONICS));
    }

    public Product findById(String productId) {
        return products.get(productId);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
