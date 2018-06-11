package com.example.bbbb.teamproject;

import android.graphics.drawable.Drawable;

/**
 * Created by LAPTOP on 2018-05-23.
 */

public class ListViewItem {
    private String review;
    private String reviewUserName;

    public void setReview(String review) {
        this.review = review;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getReview() {
        return this.review;
    }

    public String getReviewUserName() {
        return this.reviewUserName;
    }
}
