package com.brewingjava.mahavir.entities.user;

import org.bson.types.Binary;
import org.springframework.stereotype.Component;

@Component
public class ProductBought {
    
    private String productName;

    private String productPrice;

    private Binary ProductImage;

    private String DateOfDelivery;

    private String Color;

    private String BuyDate;

    public ProductBought() {
    }

    public ProductBought(String productName, String productPrice, Binary productImage, String dateOfDelivery,
            String color, String buyDate) {
        this.productName = productName;
        this.productPrice = productPrice;
        ProductImage = productImage;
        DateOfDelivery = dateOfDelivery;
        Color = color;
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

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        BuyDate = buyDate;
    }

    @Override
    public String toString() {
        return "ProductBought [BuyDate=" + BuyDate + ", Color=" + Color + ", DateOfDelivery=" + DateOfDelivery
                + ", ProductImage=" + ProductImage + ", productName=" + productName + ", productPrice=" + productPrice
                + "]";
    }

    
    
    

    
}
