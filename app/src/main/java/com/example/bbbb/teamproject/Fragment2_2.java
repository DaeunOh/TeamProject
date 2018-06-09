package com.example.bbbb.teamproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUNSOOMIN on 2018-05-19.
 */

public class Fragment2_2 extends Fragment {
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    DatabaseReference mTitleRef;
    ListView storeListView;
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View layout = inflater.inflate(R.layout.fragmnet2_2,container,false);

        storeListView = layout.findViewById(R.id.store_listview);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        mTitleRef = mDatabase.child("Restaurant").child("title");

        return layout;
    }
}
