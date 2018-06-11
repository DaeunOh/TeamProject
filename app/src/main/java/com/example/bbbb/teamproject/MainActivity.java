package com.example.bbbb.teamproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddreviewFragment.OnApplySelectedListener{

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    private ChildEventListener mChild;

    //searchview검색구현
    private ListView listView;
    private ArrayList<String> mMeetings = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;



    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    private ImageView rimage;
    private TextView textView1, textView2, textView3, textView4;

    private Button mapButton;
    private Button sortButton;
    public String  name, Name;
    String title,msg1,msg2;
    ImageView image;

    private List<RecyclerItem> recyclerItems;

    private List<String> storeList = new ArrayList<>();
    private List<Integer> randomInt;

    StorageReference mStorageRef;

    @Override
    public void onCatagoryApplySelected(String name){
        Name = name;
    }




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

        View nav_header_view = navigationView.getHeaderView(0);

        // User information setting
        TextView userNameTV = nav_header_view.findViewById(R.id.userName);
        TextView userEmailTV = nav_header_view.findViewById(R.id.userEmail);
        ImageView userPhotoIV = nav_header_view.findViewById(R.id.userPhoto);

        userNameTV.setText(myIntent.getStringExtra("userName"));
        userEmailTV.setText(myIntent.getStringExtra("userEmail"));
        //userPhotoIV.setImageURI(Uri.parse(myIntent.getStringExtra("userPhotoUrl")));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerItems = new ArrayList<>();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("Review/");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerItems.clear();

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    msg1 = messageData.getKey().toString();

//                    image = findViewById(R.id.review_image);
//                    mStorageRef = FirebaseStorage.getInstance().getReference().child("restaurantImage/" + msg1 + ".jpg");
//                    Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(mStorageRef).into(image);

                    for (DataSnapshot messageData2 : dataSnapshot.child(msg1).getChildren()) {
                        msg2 = (String) messageData2.getValue();
                        recyclerItems.add(new RecyclerItem(msg1, msg2));


                    }

                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

        //searchview 검색구현
        listView = findViewById(R.id.search_list);
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mMeetings);
        listView.setAdapter(arrayAdapter);
        FirebaseDatabase.getInstance().getReference().child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    String sname = childSnapShot.getKey();
                    mMeetings.add(sname);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener( new ListViewItemClickListener() );
    }

    private void initDatabase() {

        database = FirebaseDatabase.getInstance();

        mDatabase = database.getReference();
        mDatabase.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addChildEventListener(mChild);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.removeEventListener(mChild);
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

                for(int i=0; i<4; i++){
                    int num = (int) (Math.random()*(storeList.size()+1));

                    while(randomInt.contains(num)){
                        num = (int) (Math.random()*(storeList.size()+1));
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

        switch (view.getId()){
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
            Toast.makeText(getApplicationContext(), "리뷰관리", Toast.LENGTH_SHORT).show();
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
    private class ListViewItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(MainActivity.this, Store.class);
            intent.putExtra("title", mMeetings.get(position));
            startActivity(intent);

        }
    }



}


