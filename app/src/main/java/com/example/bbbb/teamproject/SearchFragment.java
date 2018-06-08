package com.example.bbbb.teamproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SearchFragment extends android.app.Fragment {

    String store[] = {"아롤도그", "천애부", "롯데리아", "우만동", "아맛나", "보영만두", "국수나무", "시골집", "에스팟", "피자스쿨", "솔져치킨", "맥도날드", "김밥천국"};
    ListView listView;
    ArrayAdapter<String> adapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, store);
        listView = view.findViewById(R.id.search_list);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        return view;
    }

}
