package com.brewingjava.mahavir.entities.orders;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.entities.user.UserAddress;

@Document(collection = "order_details")
@Component
public class OrderDetails {
    
    @Id
    private int orderId;

    private HashMap<String,Integer> products;

    private HashMap<String,Boolean> isProductRated; // GL-39822 => Yes, HF-23884 => No

    private String buyerEmail;

    private String buyDate;

    private String deliveryDate;

    private UserAddress userAddress;

    private String paymentMode; // Cash On Delivery or UPI

    private String paymentAmount;

    private String paymentId;

    private boolean isOrderCompleted = false;


    public OrderDetails() {
    
    }


    


    public OrderDetails(int orderId, HashMap<String, Integer> products, HashMap<String, Boolean> isProductRated,
            String buyerEmail, String buyDate, String deliveryDate, UserAddress userAddress, String paymentMode,
            String paymentAmount, String paymentId, boolean isOrderCompleted) {
        this.orderId = orderId;
        this.products = products;
        this.isProductRated = isProductRated;
        this.buyerEmail = buyerEmail;
        this.buyDate = buyDate;
        this.deliveryDate = deliveryDate;
        this.userAddress = userAddress;
        this.paymentMode = paymentMode;
        this.paymentAmount = paymentAmount;
        this.paymentId = paymentId;
        this.isOrderCompleted = isOrderCompleted;
    }





    public int getOrderId() {
        return orderId;
    }


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public HashMap<String, Integer> getProducts() {
        return products;
    }


    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }


    public String getBuyerEmail() {
        return buyerEmail;
    }


    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
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


    public UserAddress getUserAddress() {
        return userAddress;
    }


    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }


    public String getPaymentMode() {
        return paymentMode;
    }


    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    public String getPaymentAmount() {
        return paymentAmount;
    }


    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    public HashMap<String, Boolean> getIsProductRated() {
        return isProductRated;
    }


    public void setIsProductRated(HashMap<String, Boolean> isProductRated) {
        this.isProductRated = isProductRated;
    }


    public boolean isOrderCompleted() {
        return isOrderCompleted;
    }
    


    public void setOrderCompleted(boolean isOrderCompleted) {
        this.isOrderCompleted = isOrderCompleted;
    }





    public String getPaymentId() {
        return paymentId;
    }





    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }





    @Override
    public String toString() {
        return "OrderDetails [buyDate=" + buyDate + ", buyerEmail=" + buyerEmail + ", deliveryDate=" + deliveryDate
                + ", isOrderCompleted=" + isOrderCompleted + ", isProductRated=" + isProductRated + ", orderId="
                + orderId + ", paymentAmount=" + paymentAmount + ", paymentId=" + paymentId + ", paymentMode="
                + paymentMode + ", products=" + products + ", userAddress=" + userAddress + "]";
    }

    
    

    

        


}
