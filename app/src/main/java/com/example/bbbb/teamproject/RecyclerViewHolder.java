package com.example.bbbb.teamproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by LAPTOP on 2018-06-06.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView store_name;
    private ImageView image;
    private TextView review_content;
    private TextView user_name;

    private Context context;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        store_name=itemView.findViewById(R.id.reviewlist_store);
        image=itemView.findViewById(R.id.review_image);
        review_content=itemView.findViewById(R.id.reviewlist_text);
        user_name=itemView.findViewById(R.id.review_username);
        // 뷰와 인스턴스 연결
    }

    public void bindData(RecyclerItem recyclerItem) {
        store_name.setText(recyclerItem.getName());
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("restaurantImage/" + recyclerItem.getName() + ".jpg");
        Glide.with(context).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(image);
        user_name.setText(recyclerItem.getUser_name());
        review_content.setText(recyclerItem.getReview_content());
        // 뷰와 데이터 바인딩
    }
}