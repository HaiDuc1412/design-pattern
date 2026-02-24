package com.ecommerce.service.impl;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.StockManager;
import java.util.List;

public class StockManagerImpl implements StockManager {

    private final ProductRepository productRepository;

    public StockManagerImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean reserveStock(List<String> productIds) {
        // Check if all products are available
        for (String productId : productIds) {
            Product product = productRepository.findById(productId);
            if (product == null || !product.isInStock()) {
                return false;
            }
        }

        // Reserve stock
        for (String productId : productIds) {
            Product product = productRepository.findById(productId);
            product.decreaseStock();
        }

        return true;
    }

    @Override
    public void releaseStock(List<String> productIds) {
        for (String productId : productIds) {
            Product product = productRepository.findById(productId);
            if (product != null) {
                product.increaseStock();
            }
        }
    }

    @Override
    public boolean isProductAvailable(String productId) {
        Product product = productRepository.findById(productId);
        return product != null && product.isInStock();
    }
}
