package com.brewingjava.mahavir.entities.product;

import org.springframework.stereotype.Component;

@Component
public class Review {
    String review;
    String rating;

    public Review() {
    }

    public Review(String review, String rating) {
        this.review = review;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review [rating=" + rating + ", review=" + review + "]";
    }
    
}
