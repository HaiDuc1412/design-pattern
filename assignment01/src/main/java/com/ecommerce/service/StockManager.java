package com.ecommerce.service;

import java.util.List;

public interface StockManager {

    boolean reserveStock(List<String> productIds);

    void releaseStock(List<String> productIds);

    boolean isProductAvailable(String productId);
}
