package com.brewingjava.mahavir.entities.user;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "Orders")
public class Orders {
    
    private String modelNumber;

    private String productPrice;

    private String ProductImage;

    private String DateOfDelivery;

    @Id
    private String OrderId;


    private String BuyDate;

    private String BuyerEmail;

    public Orders() {
    }

    public Orders(String modelNumber, String productPrice, String productImage, String dateOfDelivery, String orderId,
            String buyDate, String buyerEmail) {
        this.modelNumber = modelNumber;
        this.productPrice = productPrice;
        ProductImage = productImage;
        DateOfDelivery = dateOfDelivery;
        OrderId = orderId;
        BuyDate = buyDate;
        BuyerEmail = buyerEmail;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getDateOfDelivery() {
        return DateOfDelivery;
    }

    public void setDateOfDelivery(String dateOfDelivery) {
        DateOfDelivery = dateOfDelivery;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    public String getBuyerEmail() {
        return BuyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        BuyerEmail = buyerEmail;
    }

    @Override
    public String toString() {
        return "Orders [BuyDate=" + BuyDate + ", BuyerEmail=" + BuyerEmail + ", DateOfDelivery=" + DateOfDelivery
                + ", OrderId=" + OrderId + ", ProductImage=" + ProductImage + ", modelNumber=" + modelNumber
                + ", productPrice=" + productPrice + "]";
    }
    
}
