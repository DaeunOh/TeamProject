package com.example.bbbb.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by LAPTOP on 2018-05-19.
 */

public class SearchActivity extends AppCompatActivity {
    private final int SEPARATE = 100;
    private ListView lvArticleList;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handler = new Handler();
        lvArticleList = (ListView) findViewById(R.id.lvArticleList);

        // 뒤로가기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // 검색어 받아오기
        Intent intent = getIntent();
        final String query = intent.getStringExtra("query");

        // 검색어를 ActionBar에 보여주기
        setTitle("검색어 : " + query);

    }


}
