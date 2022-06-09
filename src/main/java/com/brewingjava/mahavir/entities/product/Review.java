package com.brewingjava.mahavir.entities.product;

import org.springframework.stereotype.Component;

@Component
public class Review {
    
    public String reviewer_name;

    public String review;

    public String date;

    public long rating;

    public Review(String reviewer_name, String review, String date, long rating) {
        this.reviewer_name = reviewer_name;
        this.review = review;
        this.date = date;
        this.rating = rating;
    }

    public Review() {
    }

    public String getReviewer_name() {
        return reviewer_name;
    }

    public void setReviewer_name(String reviewer_name) {
        this.reviewer_name = reviewer_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review [date=" + date + ", rating=" + rating + ", review=" + review + ", reviewer_name=" + reviewer_name
                + "]";
    }
}
