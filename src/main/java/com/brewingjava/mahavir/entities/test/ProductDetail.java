package com.brewingjava.mahavir.entities.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "products")
@Component
public class ProductDetail {
    
    @Id
    public String modelNumber;

    public String productName;

    public String img1, img2, img3, img4, img5;

    public String productPrice;
    
    public String offerPrice;

    public String category;

    public String productHighlights;

    public ProductDetail() {
    }

    public ProductDetail(String modelNumber, String productName, String img1, String img2, String img3, String img4,
            String img5, String productPrice, String offerPrice, String category, String productHighlights) {
        this.modelNumber = modelNumber;
        this.productName = productName;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
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
        return "ProductDetail [category=" + category + ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3
                + ", img4=" + img4 + ", img5=" + img5 + ", modelNumber=" + modelNumber + ", offerPrice=" + offerPrice
                + ", productHighlights=" + productHighlights + ", productName=" + productName + ", productPrice="
                + productPrice + "]";
    }


    

}
