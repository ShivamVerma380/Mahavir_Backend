package com.brewingjava.mahavir.entities.test;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "products")
@Component
public class ProductDetail {
    
    @Id
    public String modelNumber;

    public String productName;

    public Binary productImage1,productImage2,productImage3,productImage4,productImage5;

    public String productPrice;
    
    public String offerPrice;

    public String category;

    public String productHighlights;

    public ProductDetail() {
    }

    public ProductDetail(String modelNumber, String productName, Binary productImage1, Binary productImage2,
            Binary productImage3, Binary productImage4, Binary productImage5, String productPrice, String offerPrice,
            String category, String productHighlights) {
        this.modelNumber = modelNumber;
        this.productName = productName;
        this.productImage1 = productImage1;
        this.productImage2 = productImage2;
        this.productImage3 = productImage3;
        this.productImage4 = productImage4;
        this.productImage5 = productImage5;
        this.productPrice = productPrice;
        this.offerPrice = offerPrice;
        this.category = category;
        this.productHighlights = productHighlights;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Binary getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(Binary productImage1) {
        this.productImage1 = productImage1;
    }

    public Binary getProductImage2() {
        return productImage2;
    }

    public void setProductImage2(Binary productImage2) {
        this.productImage2 = productImage2;
    }

    public Binary getProductImage3() {
        return productImage3;
    }

    public void setProductImage3(Binary productImage3) {
        this.productImage3 = productImage3;
    }

    public Binary getProductImage4() {
        return productImage4;
    }

    public void setProductImage4(Binary productImage4) {
        this.productImage4 = productImage4;
    }

    public Binary getProductImage5() {
        return productImage5;
    }

    public void setProductImage5(Binary productImage5) {
        this.productImage5 = productImage5;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductHighlights() {
        return productHighlights;
    }

    public void setProductHighlights(String productHighlights) {
        this.productHighlights = productHighlights;
    }

    @Override
    public String toString() {
        return "ProductDetail [category=" + category + ", modelNumber=" + modelNumber + ", offerPrice=" + offerPrice
                + ", productHighlights=" + productHighlights + ", productImage1=" + productImage1 + ", productImage2="
                + productImage2 + ", productImage3=" + productImage3 + ", productImage4=" + productImage4
                + ", productImage5=" + productImage5 + ", productName=" + productName + ", productPrice=" + productPrice
                + "]";
    }

   


    

}
