package com.example.bbbb.teamproject;

/**
 * Created by LAPTOP on 2018-06-06.
 */

public class RecyclerItem {

    private String store_name;
    private int imageUrl;
    private String review_content;
    private String user_name;

    public RecyclerItem(String username, String store_name, String review_content) {
        this.user_name = username;
        this.store_name = store_name;
        this.review_content = review_content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String name) {
        this.user_name=name;
    }

    public String getName() {
        return store_name;
    }

    public void setName(String store_name) {
        this.store_name = store_name;
    }

    public String getReview_content() {
        return review_content;
    }

    public void setReview_content(String review_content) {
        this.review_content = review_content;
    }

}