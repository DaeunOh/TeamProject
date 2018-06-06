package com.example.bbbb.teamproject;

/**
 * Created by LAPTOP on 2018-06-06.
 */

public class RecyclerItem {

    private String store_name;
    private int imageUrl;
    private String review_content;

    public RecyclerItem(String store_name, int imageUrl, String review_content) {
        this.store_name = store_name;
        this.imageUrl = imageUrl;
        this.review_content = review_content;
    }

    public String getName() {
        return store_name;
    }

    public void setName(String store_name) {
        this.store_name = store_name;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

}
