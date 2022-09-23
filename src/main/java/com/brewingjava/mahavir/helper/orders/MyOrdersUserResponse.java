package com.brewingjava.mahavir.helper.orders;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.entities.user.UserAddress;

@Component
public class MyOrdersUserResponse {

    public int orderId;
    
    public String modelNumber;

    public String productId;

    private String productName;

    private String productHighlights;
    
    private String productImage1;

    private String OfferPrice;

    private String productPrice;

    private HashMap<String,String> subCategoryMap;

    private String category;

    private HashMap<String,String> filtercriterias;

    private int quantity;

    private String paymentAmount;

    private String paymentMode;

    private String buyDate;

    private String deliveryDate;

    private boolean isOrderCompleted;



    private boolean isProductRated;

    private UserAddress userAddress;

    private String paymentId;

    public MyOrdersUserResponse() {

    }

    public MyOrdersUserResponse(String modelNumber, String productId, String productName, String productHighlights,
            String productImage1, String offerPrice, String productPrice, HashMap<String, String> subCategoryMap,
            String category, HashMap<String, String> filtercriterias, int quantity, String paymentAmount,
            String paymentMode, String buyDate, String deliveryDate, boolean isOrderCompleted, boolean isProductRated,
            UserAddress userAddress) {
        this.modelNumber = modelNumber;
        this.productId = productId;
        this.productName = productName;
        this.productHighlights = productHighlights;
        this.productImage1 = productImage1;
        OfferPrice = offerPrice;
        this.productPrice = productPrice;
        this.subCategoryMap = subCategoryMap;
        this.category = category;
        this.filtercriterias = filtercriterias;
        this.quantity = quantity;
        this.paymentAmount = paymentAmount;
        this.paymentMode = paymentMode;
        this.buyDate = buyDate;
        this.deliveryDate = deliveryDate;
        this.isOrderCompleted = isOrderCompleted;
        this.isProductRated = isProductRated;
        this.userAddress = userAddress;
    }

    

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
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

    public String getProductHighlights() {
        return productHighlights;
    }

    public void setProductHighlights(String productHighlights) {
        this.productHighlights = productHighlights;
    }

    public String getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
    }

    public String getOfferPrice() {
        return OfferPrice;
    }

    public void setOfferPrice(String offerPrice) {
        OfferPrice = offerPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public HashMap<String, String> getSubCategoryMap() {
        return subCategoryMap;
    }

    public void setSubCategoryMap(HashMap<String, String> subCategoryMap) {
        this.subCategoryMap = subCategoryMap;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HashMap<String, String> getFiltercriterias() {
        return filtercriterias;
    }

    public void setFiltercriterias(HashMap<String, String> filtercriterias) {
        this.filtercriterias = filtercriterias;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isOrderCompleted() {
        return isOrderCompleted;
    }

    public void setOrderCompleted(boolean isOrderCompleted) {
        this.isOrderCompleted = isOrderCompleted;
    }

    public boolean isProductRated() {
        return isProductRated;
    }

    public void setProductRated(boolean isProductRated) {
        this.isProductRated = isProductRated;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }


    
    @Override
    public String toString() {
        return "MyOrdersUserResponse [OfferPrice=" + OfferPrice + ", buyDate=" + buyDate + ", category=" + category
                + ", deliveryDate=" + deliveryDate + ", filtercriterias=" + filtercriterias + ", isOrderCompleted="
                + isOrderCompleted + ", isProductRated=" + isProductRated + ", modelNumber=" + modelNumber
                + ", paymentAmount=" + paymentAmount + ", paymentMode=" + paymentMode + ", productHighlights="
                + productHighlights + ", productId=" + productId + ", productImage1=" + productImage1 + ", productName="
                + productName + ", productPrice=" + productPrice + ", quantity=" + quantity + ", subCategoryMap="
                + subCategoryMap + ", userAddress=" + userAddress + "]";
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

}
