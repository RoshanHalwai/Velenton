package com.example.rhalwai.velenton.home;

public class SubProduct{

    private String productId;
    private String productImage;
    private String productSize;
    private int productPrice;

    public SubProduct() {
    }

    public SubProduct(String productId, String productImage, String productSize, int productPrice) {
        this.productId = productId;
        this.productImage = productImage;
        this.productSize = productSize;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductSize() {
        return productSize;
    }

    public int getProductPrice() {
        return productPrice;
    }
}
