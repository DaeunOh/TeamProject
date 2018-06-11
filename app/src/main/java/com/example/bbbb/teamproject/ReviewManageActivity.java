package com.example.bbbb.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewManageActivity extends AppCompatActivity {

    String msg1,msg2;
    private List<RecyclerItem> recyclerItems;

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_manage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        RecyclerView recyclerView = findViewById(R.id.store_reviewList2);
        recyclerItems = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Review/");

        //db읽어서 아이템 만들어 List에 추가하기
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerItems.clear();
                    //recyclerItems에 리뷰 데이터 넣어주기!
                    for (DataSnapshot messageData2 : dataSnapshot.child(username).getChildren()) {
                        msg1 = messageData2.getKey();
                        for (DataSnapshot messageData3 : dataSnapshot.child(username).child(msg1).getChildren()) {
                            msg2 = (String) messageData3.getValue();
                            recyclerItems.add(new RecyclerItem(username, msg1, msg2));
                        }
                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final RecyclerAdapter reviewAdapter = new RecyclerAdapter(getApplicationContext(), recyclerItems);
        reviewAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), Store.class);
                String store = recyclerItems.get(position).getName();
                intent.putExtra("title", store);
                startActivity(intent);
            }
        });
        reviewAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), linearLayoutManager.getOrientation()));

        }

}
