package com.example.rhalwai.velenton.home;

import java.io.Serializable;

public class Product implements Serializable {

    private String productId;
    private String productName;
    private int productDesigns;
    private int productPrice;
    private String productImage;
    private String productDetails;

    public Product() {
    }

    public Product(String productId, String productName, int productDesigns, int productPrice, String productImage, String productDetails) {
        this.productId = productId;
        this.productName = productName;
        this.productDesigns = productDesigns;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.productDetails = productDetails;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductDesigns() {
        return productDesigns;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductDetails() {
        return productDetails;
    }
}
