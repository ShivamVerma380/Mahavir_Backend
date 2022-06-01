package com.brewingjava.mahavir.entities.product;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="reviews")
public class ProductReviews {
    
    @Id
    private String modelNumber;

    HashMap<String, Review> reviews; // name : review

    private String productRating;

    public ProductReviews() {
    }

    public ProductReviews(String modelNumber, HashMap<String, Review> reviews, String productRating) {
        this.modelNumber = modelNumber;
        this.reviews = reviews;
        this.productRating = productRating;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public HashMap<String, Review> getReviews() {
        return reviews;
    }

    public void setReviews(HashMap<String, Review> reviews) {
        this.reviews = reviews;
    }

    public String getProductRating() {
        return productRating;
    }

    public void setProductRating(String productRating) {
        this.productRating = productRating;
    }

    @Override
    public String toString() {
        return "ProductReviews [modelNumber=" + modelNumber + ", productRating=" + productRating + ", reviews="
                + reviews + "]";
    }

    
}
