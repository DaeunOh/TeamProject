package com.example.bbbb.teamproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LAPTOP on 2018-06-06.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView store_name;
    private ImageView image;
    private TextView review_content;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        store_name=itemView.findViewById(R.id.reviewlist_store);
        image=itemView.findViewById(R.id.review_image);
        review_content=itemView.findViewById(R.id.reviewlist_text);
        // 뷰와 인스턴스 연결
    }

    public void bindData(RecyclerItem recyclerItem) {
        store_name.setText(recyclerItem.getName());
        image.setImageResource(recyclerItem.getImageUrl());
        review_content.setText(recyclerItem.getReview_content());

        // 뷰와 데이터 바인딩
    }
}