package com.brewingjava.mahavir.entities.product;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="reviews")
public class ProductReviews {
    
    @Id
    private String modelNumber;

    HashMap<String, String> reviews; // name : review

    private String rating;

    public ProductReviews() {
    }

    public ProductReviews(String modelNumber, HashMap<String, String> reviews, String rating) {
        this.modelNumber = modelNumber;
        this.reviews = reviews;
        this.rating = rating;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public HashMap<String, String> getReviews() {
        return reviews;
    }

    public void setReviews(HashMap<String, String> reviews) {
        this.reviews = reviews;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ProductReviews [modelNumber=" + modelNumber + ", rating=" + rating + ", reviews=" + reviews + "]";
    }

    
}
