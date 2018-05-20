package com.example.bbbb.teamproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private Button mapButton;
    private Button sortButton;
    private android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost) findViewById(R.id.host);
        ImageButton rouletteButton = (ImageButton) findViewById(R.id.button_roulette);
        mapButton = (Button) findViewById(R.id.button_map);
        sortButton = (Button) findViewById(R.id.button_sort);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = findViewById(R.id.menu_search);
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.user);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        searchView.onActionViewExpanded();
        searchView.setQueryHint("이름을 검색하시오");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            //검색 버튼을 클릭했을 때 동작하는 이벤트 query:입력된 검색어
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("query", query);
                searchView.clearFocus();

                return false;
            }

            //검색어를 입력할 때 동작하는거
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//        dtToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        dtToggle.onConfigurationChanged(newConfig);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
                dlDrawer.setDrawerListener(dtToggle);

                dlDrawer.openDrawer(Gravity.START);
                return true;
            }
            case R.id.lang: {
                return true;
            }
            case R.id.colorch: {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onclick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.my_info: {
                dlDrawer.closeDrawer(Gravity.START);
            }
            case R.id.my_review: {
                dlDrawer.closeDrawer(Gravity.START);
            }
        }
    }


}

