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

    private String buyerEmail;

    private String buyDate;

    private String deliveryDate;

    private UserAddress userAddress;

    private String paymentMode; // Cash On Delivery or UPI

    private String paymentAmount;


    public OrderDetails() {
    
    }


    public OrderDetails(int orderId, HashMap<String, Integer> products, String buyerEmail, String buyDate,
            String deliveryDate, UserAddress userAddress, String paymentMode, String paymentAmount) {
        this.orderId = orderId;
        this.products = products;
        this.buyerEmail = buyerEmail;
        this.buyDate = buyDate;
        this.deliveryDate = deliveryDate;
        this.userAddress = userAddress;
        this.paymentMode = paymentMode;
        this.paymentAmount = paymentAmount;
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


    @Override
    public String toString() {
        return "OrderDetails [buyDate=" + buyDate + ", buyerEmail=" + buyerEmail + ", deliveryDate=" + deliveryDate
                + ", orderId=" + orderId + ", paymentAmount=" + paymentAmount + ", paymentMode=" + paymentMode
                + ", products=" + products + ", userAddress=" + userAddress + "]";
    }  


}
