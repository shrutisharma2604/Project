package com.example.EcommerceApp.dto;

import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Range;

public class ReviewDTO {
    private String review;

    @NotNull
    @Range(min = 0, max = 10)
    private Integer rating;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
