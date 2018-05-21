package com.example.bbbb.teamproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button mapButton;
    private Button sortButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.host);
        ImageButton rouletteButton = (ImageButton) findViewById(R.id.button_roulette);
        mapButton = (Button) findViewById(R.id.button_map);
        sortButton = (Button) findViewById(R.id.button_sort);
        Button reviewButton = (Button) findViewById(R.id.button_review);
        final EditText reviewText = findViewById(R.id.review_text);
        final AutoCompleteTextView searchText = findViewById(R.id.search_text);

        new Roulette(MainActivity.this).setComponents(rouletteButton);

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

        spec = host.newTabSpec("tab4");
        spec.setContent(R.id.tab_content4);
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.tab_pen, null));
        host.addTab(spec);

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("리뷰를 등록하시겠습니까?")
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "리뷰가 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                searchText.setText("");
                                reviewText.setText("");
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });

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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


