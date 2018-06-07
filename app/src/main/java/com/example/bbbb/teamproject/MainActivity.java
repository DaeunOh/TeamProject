package com.example.bbbb.teamproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
    private ImageView rimage;
    private TextView textView1, textView2, textView3, textView4, textView5, textView6;

    private Button mapButton;
    private Button sortButton;

    private List<RecyclerItem> recyclerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.host);
        ImageButton rouletteButton = (ImageButton) findViewById(R.id.button_roulette);
        mapButton = (Button) findViewById(R.id.button_map);
        sortButton = (Button) findViewById(R.id.button_sort);
        Button reviewButton = (Button) findViewById(R.id.button_review);

        imageButton1 = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton6);

        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        // 상점 선택
        selectStore();


        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("tab1");
        spec.setContent(R.id.tab_content1);
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_home, null));
        host.addTab(spec);

        spec = host.newTabSpec("tab2");
        spec.setContent(R.id.tab_content2);
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_search, null));
        host.addTab(spec);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            Fragment2_1 firstFragment = new Fragment2_1();

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

        spec = host.newTabSpec("tab3");
        spec.setContent(R.id.tab_content3);
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_roulette, null));
        host.addTab(spec);


        //이미지를 직접 그려주는 방법

        rimage = findViewById(R.id.RouletteImage);

        new Roulette(MainActivity.this, rimage, rouletteButton, getApplicationContext()).setComponents(rouletteButton);


        spec = host.newTabSpec("tab4");
        spec.setContent(R.id.tab_content4);
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_pen, null));
        host.addTab(spec);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent myIntent = getIntent();
        // when user is signed in
        if(myIntent.getBooleanExtra("login", false)){

            View nav_header_view = navigationView.getHeaderView(0);

            // User information setting
            TextView userNameTV = nav_header_view.findViewById(R.id.userName);
            TextView userEmailTV = nav_header_view.findViewById(R.id.userEmail);
            ImageView userPhotoIV = nav_header_view.findViewById(R.id.userPhoto);

            userNameTV.setText(myIntent.getStringExtra("userName"));
            userEmailTV.setText(myIntent.getStringExtra("userEmail"));
            userPhotoIV.setImageURI(Uri.parse(myIntent.getStringExtra("userPhotoUrl")));
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerItems = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            recyclerItems.add(new RecyclerItem("아롤도그", R.drawable.alol, "참 맛나용"));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final RecyclerAdapter adapter = new RecyclerAdapter(recyclerItems);
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));

    }

    private void selectStore() {
        /*
        randomSelect 구현 필요
        database에 index만 적혀 있는 이미지 파일 추가 (ex. 0, 1, 2, ...)
        for문(count=0; count<6; count++)을 통해 count를 증가시키면서 총 6번의 for문 실행
        index라는 sting 변수를 배열로 관리하면서 똑같은 index가 존재하지 않도록...
        index string을 통해 database에 접근하고 그에 맞는 버튼과 텍스트뷰 나타나도록!
         */

        StorageReference mStorageRef;

        mStorageRef = FirebaseStorage.getInstance().getReference().child("squareRestaurantImage/" + "천애부" + ".jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).into(imageButton1);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("squareRestaurantImage/" + "아롤도그" + ".jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).into(imageButton2);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("squareRestaurantImage/" + "맘스터치" + ".jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).into(imageButton3);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("squareRestaurantImage/" + "깐돌이네" + ".jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).into(imageButton4);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("squareRestaurantImage/" + "육쌈냉면" + ".jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).into(imageButton5);

        mStorageRef = FirebaseStorage.getInstance().getReference().child("squareRestaurantImage/" + "무한통삼" + ".jpg");
        Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).into(imageButton6);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference();
        DatabaseReference mTitleRef = mDatabase.child("Restaurant").child("천애부");
        DatabaseReference mNameRef = mTitleRef.child("name");

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = dataSnapshot.getValue(String.class);
                textView1.setText(name);

                imageButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Store.class);
                        intent.putExtra("title", name);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTitleRef = mDatabase.child("Restaurant").child("아롤도그");
        mNameRef = mTitleRef.child("name");

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = dataSnapshot.getValue(String.class);
                textView2.setText(name);

                imageButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Store.class);
                        intent.putExtra("title", name);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTitleRef = mDatabase.child("Restaurant").child("맘스터치");
        mNameRef = mTitleRef.child("name");

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = dataSnapshot.getValue(String.class);
                textView3.setText(name);

                imageButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Store.class);
                        intent.putExtra("title", name);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTitleRef = mDatabase.child("Restaurant").child("깐돌이네");
        mNameRef = mTitleRef.child("name");

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = dataSnapshot.getValue(String.class);
                textView4.setText(name);

                imageButton4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Store.class);
                        intent.putExtra("title", name);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTitleRef = mDatabase.child("Restaurant").child("육쌈냉면");
        mNameRef = mTitleRef.child("name");

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = dataSnapshot.getValue(String.class);
                textView5.setText(name);

                imageButton5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Store.class);
                        intent.putExtra("title", name);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mTitleRef = mDatabase.child("Restaurant").child("무한통삼");
        mNameRef = mTitleRef.child("name");

        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String name = dataSnapshot.getValue(String.class);
                textView6.setText(name);

                imageButton6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Store.class);
                        intent.putExtra("title", name);
                        startActivity(intent);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void selectFragment(View view) {
        Fragment fr = null;

        switch (view.getId()) {
            case R.id.button_map:
                mapButton.setBackgroundColor(0xffD95550);
                sortButton.setBackgroundColor(0xffaaaaaa);
                fr = new Fragment2_1();
                break;
            case R.id.button_sort:
                mapButton.setBackgroundColor(0xffaaaaaa);
                sortButton.setBackgroundColor(0xffD95550);
                fr = new Fragment2_2();
                break;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void addReiew(View view) {
        Fragment fr = null;

        switch (view.getId()) {
            case R.id.add_review:
                fr = new AddreviewFragment();
                break;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.tab_content4, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("상점을 입력하시오.");
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "탐색완료!", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.lang) {
            Toast.makeText(getApplicationContext(), "Success English!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.colorch) {
            Toast.makeText(getApplicationContext(), "색변경!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_search) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_info) {
            Toast.makeText(getApplicationContext(), "내정보관리", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.my_review) {
            Toast.makeText(getApplicationContext(), "리뷰관리", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.my_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("check", "checkIsNotFirstOpen");
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}


