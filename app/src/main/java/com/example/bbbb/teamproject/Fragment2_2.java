package com.example.bbbb.teamproject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SUNSOOMIN on 2018-05-19.
 */

public class Fragment2_2 extends Fragment implements View.OnClickListener {
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    DatabaseReference mTitleRef;

    ListView storeListView;
    ArrayAdapter adapter;

    private List<String> koStoreList = new ArrayList<>();
    private List<String> chStoreList = new ArrayList<>();
    private List<String> jpStoreList = new ArrayList<>();
    private List<String> buStoreList = new ArrayList<>();
    private List<String> ffStoreList = new ArrayList<>();
    private List<String> asoList = new ArrayList<>();


    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View layout = inflater.inflate(R.layout.fragmnet2_2, container, false);

        storeListView = layout.findViewById(R.id.store_listview);
        radioGroup1 = layout.findViewById(R.id.radio_group1);
        radioGroup2 = layout.findViewById(R.id.radio_group2);

        RadioButton koRadioButton = layout.findViewById(R.id.type_ko);
        koRadioButton.setOnClickListener(this);
        RadioButton chRadioButton = layout.findViewById(R.id.type_ch);
        chRadioButton.setOnClickListener(this);
        RadioButton jpRadioButton = layout.findViewById(R.id.type_jp);
        jpRadioButton.setOnClickListener(this);
        RadioButton buRadioButton = layout.findViewById(R.id.type_bu);
        buRadioButton.setOnClickListener(this);
        RadioButton ffRadioButton = layout.findViewById(R.id.type_ff);
        ffRadioButton.setOnClickListener(this);
        RadioButton asoRadioButton = layout.findViewById(R.id.aso);
        asoRadioButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mTitleRef = mDatabase.child("Restaurant");

        mTitleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                koStoreList.clear();
                chStoreList.clear();
                jpStoreList.clear();
                buStoreList.clear();
                ffStoreList.clear();
                asoList.clear();

                for (DataSnapshot storeSnapshot : dataSnapshot.getChildren()) {
                    if (storeSnapshot.child("type").getValue().equals("한식"))
                        koStoreList.add(String.valueOf(storeSnapshot.getKey()));
                    else if (storeSnapshot.child("type").getValue().equals("중식"))
                        chStoreList.add(String.valueOf(storeSnapshot.getKey()));
                    else if (storeSnapshot.child("type").getValue().equals("일식"))
                        jpStoreList.add(String.valueOf(storeSnapshot.getKey()));
                    else if (storeSnapshot.child("type").getValue().equals("분식"))
                        buStoreList.add(String.valueOf(storeSnapshot.getKey()));
                    else if (storeSnapshot.child("type").getValue().equals("패스트푸드"))
                        ffStoreList.add(String.valueOf(storeSnapshot.getKey()));
                    else if (storeSnapshot.child("type").getValue().equals("기타"))
                        asoList.add(String.valueOf(storeSnapshot.getKey()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        storeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Store.class);
                String title = (String) parent.getAdapter().getItem(position);
                intent.putExtra("title", title);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_left);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.type_ko:
                radioGroup2.clearCheck();
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, koStoreList);
                storeListView.setAdapter(adapter);
                break;
            case R.id.type_ch:
                radioGroup2.clearCheck();
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, chStoreList);
                storeListView.setAdapter(adapter);
                break;
            case R.id.type_jp:
                radioGroup2.clearCheck();
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, jpStoreList);
                storeListView.setAdapter(adapter);
                break;
            case R.id.type_bu:
                radioGroup1.clearCheck();
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, buStoreList);
                storeListView.setAdapter(adapter);
                break;
            case R.id.type_ff:
                radioGroup1.clearCheck();
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ffStoreList);
                storeListView.setAdapter(adapter);
                break;
            case R.id.aso:
                radioGroup1.clearCheck();
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, asoList);
                storeListView.setAdapter(adapter);
                break;
        }
    }
}
