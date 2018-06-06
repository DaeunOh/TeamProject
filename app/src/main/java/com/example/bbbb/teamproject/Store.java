package com.example.bbbb.teamproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by bbbb_ on 2018-05-23.
 */

public class Store extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    DatabaseReference mTitleRef;
    DatabaseReference mAddressRef;
    DatabaseReference mNameRef;
    DatabaseReference mPriceRef;
    DatabaseReference mTelRef;
    DatabaseReference mTypeRef;

    StorageReference mStorageRef;

    TextView nameTextView;
    TextView addressTextView;
    TextView telTextView;
    ImageView image;
    ImageButton callButton;

    String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mTitleRef = mDatabase.child("Restaurant").child(title);
        mAddressRef = mTitleRef.child("address");
        mNameRef = mTitleRef.child("name");
        mPriceRef = mTitleRef.child("price");
        mTelRef = mTitleRef.child("tel");
        mTypeRef = mTitleRef.child("type");

        // 가게 이미지
        mStorageRef = FirebaseStorage.getInstance().getReference().child("restaurantImage/" + title + ".jpg");
        image = findViewById(R.id.image_restaurant);
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).into(image);

        nameTextView = findViewById(R.id.store_name);
        addressTextView = findViewById(R.id.store_address);
        telTextView=findViewById(R.id.store_tel);
        callButton=findViewById(R.id.call_button);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                nameTextView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAddressRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address = dataSnapshot.getValue(String.class);
                addressTextView.setText(address);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTelRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tel = dataSnapshot.getValue(String.class);
                telTextView.setText(tel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onclick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.call_button:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel));
                break;
        }
        if (intent!=null) {
            startActivity(intent);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_right);
    }
}
