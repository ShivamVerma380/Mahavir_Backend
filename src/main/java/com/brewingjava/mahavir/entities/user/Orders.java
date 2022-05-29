package com.brewingjava.mahavir.entities.user;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "Orders")
public class Orders {
    
    private String productName;

    private String productPrice;

    private Binary ProductImage;

    private String DateOfDelivery;

    @Id
    private String OrderId;


    private String BuyDate;

    private String BuyerEmail;

    public Orders() {
    }

    public Orders(String productName, String productPrice, Binary productImage, String dateOfDelivery, String OrderId
            , String buyDate, String BuyerEmail) {
        this.productName = productName;
        this.productPrice = productPrice;
        
        this.OrderId = OrderId;
        this.BuyerEmail = BuyerEmail;
        ProductImage = productImage;
        DateOfDelivery = dateOfDelivery;
       
        BuyDate = buyDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Binary getProductImage() {
        return ProductImage;
    }

    public void setProductImage(Binary productImage) {
        ProductImage = productImage;
    }

    public String getDateOfDelivery() {
        return DateOfDelivery;
    }

    public void setDateOfDelivery(String dateOfDelivery) {
        DateOfDelivery = dateOfDelivery;
    }

    

    

    public String getBuyerEmail() {
        return BuyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        BuyerEmail = buyerEmail;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        this.OrderId = orderId;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    @Override
    public String toString() {
        return "Orders [BuyDate=" + BuyDate + ", BuyerEmail=" + BuyerEmail + ", DateOfDelivery=" + DateOfDelivery
                + ", OrderId=" + OrderId + ", ProductImage=" + ProductImage + ", productName=" + productName
                + ", productPrice=" + productPrice + "]";
    }

    

    

    
    
    

    
}
