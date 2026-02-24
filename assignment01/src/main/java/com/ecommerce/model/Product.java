package com.ecommerce.model;

import com.ecommerce.model.enums.ProductCategory;

public class Product {

    private String id;
    private String name;
    private double price;
    private int stock;
    private ProductCategory category;

    public Product(String id, String name, double price, int stock, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public boolean isInStock() {
        return stock > 0;
    }

    public void decreaseStock() {
        if (stock > 0) {
            stock--;
        }
    }

    public void increaseStock() {
        stock++;
    }
}
