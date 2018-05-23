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
         setListAdapter(adapter) ;

         // 첫 번째 아이템 추가.
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "아맛나", "맛나용") ;

         // 두 번째 아이템 추가.
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "구뜰", "맛있어여") ;

         // 세 번째 아이템 추가.
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;
         adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.home), "미솊", "마아아싯어서엉") ;

         return super.onCreateView(inflater, container, savedInstanceState);
         }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {

        ListViewItem item = (ListViewItem) l.getItemAtPosition(position);
        String store = item.getTitle();
        String review = item.getReview();


    }


}
