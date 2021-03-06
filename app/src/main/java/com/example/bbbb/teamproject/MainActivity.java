package com.example.bbbb.teamproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.reginald.swiperefresh.CustomSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;

    //searchview검색구현
    private ListView listView;
    private ArrayList<String> mMeetings = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    private ImageView rimage;
    private TextView textView1, textView2, textView3, textView4;

    private Button mapButton;
    private Button sortButton;
    private String username, reviewname;
    private String msg1, msg2;
    private String title;

    private List<RecyclerItem> recyclerItems;

    private List<String> storeList = new ArrayList<>();
    private List<Integer> randomInt;

    private CustomSwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent loadingIntent = new Intent(this, LoadingActivity.class);
        startActivity(loadingIntent);

        TabHost host = (TabHost) findViewById(R.id.host);
        ImageButton rouletteButton = (ImageButton) findViewById(R.id.button_roulette);
        mapButton = (Button) findViewById(R.id.button_map);
        sortButton = (Button) findViewById(R.id.button_sort);

        imageButton1 = findViewById(R.id.imageButton);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);

        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);

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

        Roulette roulette = new Roulette(MainActivity.this, rimage, rouletteButton);
        roulette.setComponents(rouletteButton);
        roulette.setViewGroup((ViewGroup) findViewById(R.id.popup));

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

        View nav_header_view = navigationView.getHeaderView(0);

        // User information setting
        TextView userNameTV = nav_header_view.findViewById(R.id.userName);
        TextView userEmailTV = nav_header_view.findViewById(R.id.userEmail);

        userNameTV.setText(myIntent.getStringExtra("userName"));
        userEmailTV.setText(myIntent.getStringExtra("userEmail"));
        username = (String) userNameTV.getText();

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerItems = new ArrayList<>();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Review/");

        //db읽어서 아이템 만들어 List에 추가하기
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerItems.clear();

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    reviewname = messageData.getKey().toString();

                    for (DataSnapshot messageData2 : dataSnapshot.child(reviewname).getChildren()) {
                        msg1 = messageData2.getKey().toString();
                        for (DataSnapshot messageData3 : dataSnapshot.child(reviewname).child(msg1).getChildren()) {
                            msg2 = (String) messageData3.getValue();
                            recyclerItems.add(new RecyclerItem(reviewname, msg1, msg2));
                        }

                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        layout = findViewById(R.id.swipe_refresh);
        layout.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        recyclerItems.clear();

                        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                            reviewname = messageData.getKey().toString();

                            for (DataSnapshot messageData2 : dataSnapshot.child(reviewname).getChildren()) {
                                msg1 = messageData2.getKey().toString();
                                for (DataSnapshot messageData3 : dataSnapshot.child(reviewname).child(msg1).getChildren()) {
                                    msg2 = (String) messageData3.getValue();
                                    recyclerItems.add(new RecyclerItem(reviewname, msg1, msg2));
                                }

                            }
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final RecyclerAdapter adapter = new RecyclerAdapter(MainActivity.this, recyclerItems);
                adapter.setListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(MainActivity.this, Store.class);
                        String store = recyclerItems.get(position).getName();
                        intent.putExtra("title", store);
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);
                layout.refreshComplete();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        final RecyclerAdapter adapter = new RecyclerAdapter(MainActivity.this, recyclerItems);
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, Store.class);
                String store = recyclerItems.get(position).getName();
                intent.putExtra("title", store);
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        //searchview 검색구현
        listView = findViewById(R.id.search_list);
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mMeetings);
        listView.setAdapter(arrayAdapter);
        FirebaseDatabase.getInstance().getReference().child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    String sname = childSnapShot.getKey();
                    mMeetings.add(sname);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new ListViewItemClickListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void selectStore() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = database.getReference();

        randomInt = new ArrayList<>();
        storeList = new ArrayList<>();

        DatabaseReference mStoreRef = mDatabase.child("Restaurant");

        mStoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeList.clear();

                for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                    storeList.add(String.valueOf(storeSnapshot.getKey()));
                }

                for (int i = 0; i < 4; i++) {
                    int num = (int) (Math.random() * storeList.size());

                    while (randomInt.contains(num)) {
                        num = (int) (Math.random() * storeList.size());
                    }
                    randomInt.add(num);
                }

                // image loading
                StorageReference mStorageRef;
                mStorageRef = FirebaseStorage.getInstance().getReference().child("restaurantImage/" + storeList.get(randomInt.get(0)) + ".jpg");
                Glide.with(MainActivity.this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageButton1);
                mStorageRef = FirebaseStorage.getInstance().getReference().child("restaurantImage/" + storeList.get(randomInt.get(1)) + ".jpg");
                Glide.with(MainActivity.this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageButton2);
                mStorageRef = FirebaseStorage.getInstance().getReference().child("restaurantImage/" + storeList.get(randomInt.get(2)) + ".jpg");
                Glide.with(MainActivity.this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageButton3);
                mStorageRef = FirebaseStorage.getInstance().getReference().child("restaurantImage/" + storeList.get(randomInt.get(3)) + ".jpg");
                Glide.with(MainActivity.this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageButton4);

                textView1.setText(storeList.get(randomInt.get(0)));
                textView2.setText(storeList.get(randomInt.get(1)));
                textView3.setText(storeList.get(randomInt.get(2)));
                textView4.setText(storeList.get(randomInt.get(3)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onImageButtonClicked(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.imageButton:
                intent = new Intent(MainActivity.this, Store.class);
                intent.putExtra("title", storeList.get(randomInt.get(0)));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                break;
            case R.id.imageButton2:
                intent = new Intent(MainActivity.this, Store.class);
                intent.putExtra("title", storeList.get(randomInt.get(1)));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                break;
            case R.id.imageButton3:
                intent = new Intent(MainActivity.this, Store.class);
                intent.putExtra("title", storeList.get(randomInt.get(2)));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                break;
            case R.id.imageButton4:
                intent = new Intent(MainActivity.this, Store.class);
                intent.putExtra("title", storeList.get(randomInt.get(3)));
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
                break;
        }
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
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                fr.setArguments(bundle);
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
        final FrameLayout searchCont = findViewById(R.id.search_frag_container);

        searchView.setQueryHint("상점을 입력하시오.");
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    searchCont.setVisibility(View.VISIBLE);
                } else {
                    searchCont.setVisibility(View.GONE);
                }
            }
        });
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "탐색완료!", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.arrayAdapter.getFilter().filter(newText);
                return false;
            }

        });


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_review) {
            Intent intent = new Intent(MainActivity.this, ReviewManageActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
        } else if (id == R.id.my_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("signOut", true);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //searchview검색 구현
    private class ListViewItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, Store.class);
            intent.putExtra("title", arrayAdapter.getItem(position));
            startActivity(intent);

        }
    }


}


