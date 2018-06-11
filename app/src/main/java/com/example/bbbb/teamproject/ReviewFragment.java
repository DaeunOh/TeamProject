package com.example.bbbb.teamproject;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ReviewFragment extends ListFragment {
    ListViewAdapter adapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Adapter 생성 및 Adapter 지정.
         adapter = new ListViewAdapter() ;
         setListAdapter(adapter);


         return super.onCreateView(inflater, container, savedInstanceState);


         }

}
