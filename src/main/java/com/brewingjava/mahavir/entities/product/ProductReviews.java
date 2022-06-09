package com.brewingjava.mahavir.entities.product;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="reviews")
public class ProductReviews {
    
    @Id
    private String modelNumber;

    private long nosOfOneStars;

    private long nosOfTwoStars;

    private long nosOfThreeStars;

    private long nosOfFourStars;

    private long nosOfFiveStars;

    private long totalRatings;

    private long totalReviews;

    private double averageRatings;

    public List<Review> reviews;

    public ProductReviews() {
    }

    public ProductReviews(String modelNumber, long nosOfOneStars, long nosOfTwoStars, long nosOfThreeStars,
            long nosOfFourStars, long nosOfFiveStars, long totalRatings, long totalReviews, double averageRatings,
            List<Review> reviews) {
        this.modelNumber = modelNumber;
        this.nosOfOneStars = nosOfOneStars;
        this.nosOfTwoStars = nosOfTwoStars;
        this.nosOfThreeStars = nosOfThreeStars;
        this.nosOfFourStars = nosOfFourStars;
        this.nosOfFiveStars = nosOfFiveStars;
        this.totalRatings = totalRatings;
        this.totalReviews = totalReviews;
        this.averageRatings = averageRatings;
        this.reviews = reviews;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public long getNosOfOneStars() {
        return nosOfOneStars;
    }

    public void setNosOfOneStars(long nosOfOneStars) {
        this.nosOfOneStars = nosOfOneStars;
    }

    public long getNosOfTwoStars() {
        return nosOfTwoStars;
    }

    public void setNosOfTwoStars(long nosOfTwoStars) {
        this.nosOfTwoStars = nosOfTwoStars;
    }

    public long getNosOfThreeStars() {
        return nosOfThreeStars;
    }

    public void setNosOfThreeStars(long nosOfThreeStars) {
        this.nosOfThreeStars = nosOfThreeStars;
    }

    public long getNosOfFourStars() {
        return nosOfFourStars;
    }

    public void setNosOfFourStars(long nosOfFourStars) {
        this.nosOfFourStars = nosOfFourStars;
    }

    public long getNosOfFiveStars() {
        return nosOfFiveStars;
    }

    public void setNosOfFiveStars(long nosOfFiveStars) {
        this.nosOfFiveStars = nosOfFiveStars;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public double getAverageRatings() {
        return averageRatings;
    }

    public void setAverageRatings(double averageRatings) {
        this.averageRatings = averageRatings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ProductReviews [averageRatings=" + averageRatings + ", modelNumber=" + modelNumber + ", nosOfFiveStars="
                + nosOfFiveStars + ", nosOfFourStars=" + nosOfFourStars + ", nosOfOneStars=" + nosOfOneStars
                + ", nosOfThreeStars=" + nosOfThreeStars + ", nosOfTwoStars=" + nosOfTwoStars + ", reviews=" + reviews
                + ", totalRatings=" + totalRatings + ", totalReviews=" + totalReviews + "]";
    }

    
    
    
    
}
