package com.brewingjava.mahavir.helper.orders;

import org.springframework.stereotype.Component;

@Component
public class OrderResponse {
    
    private String productId,productName;
    
    private int quantity;

    public OrderResponse() {
    }

    public OrderResponse(String productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderResponse [productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
                + "]";
    }

    

}
