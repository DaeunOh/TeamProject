package com.example.bbbb.teamproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by LAPTOP on 2018-06-11.
 */

class DataListAdapter extends ArrayAdapter<String> {
    private ArrayList<String> oriDataList, dataList;
    private Filter dataFilter;

    public DataListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.dataList = objects;
        this.oriDataList = objects;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public String getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList.get(position).hashCode();
    }

//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View curView = convertView;
//        if(curView == null){
//            curView = inflateWithCustomFont(layoutResource);
//        }
//        Data curData = dataList.get(position);
//        String name = curData.getName();
//
//        TextView tvName = (TextView) curView.findViewById(R.id.tvDataListListItemName);
//        TextView tvLevel = (TextView) curView.findViewById(R.id.tvDataListListItemLevel);
//
//        tvName.setText(name);
//        tvLevel.setText(level);
//
//        return curView;
//    }
//
//    public void resetData(){
//        dataList = oriDataList;
//    }
//
//    @Override
//    public Filter getFilter() {
//        if(dataFilter == null)
//            dataFilter = new DataFilter();
//        return dataFilter;
//    }
//
//    // 검색 필터
//    private class DataFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults results = new FilterResults();
//            if(constraint == null || constraint.length() == 0){
//                results.values = oriDataList;
//                results.count = oriDataList.size();
//            }
//            else {
//                ArrayList<Data> filterDataList = new ArrayList<Data>();
//                filterDataList.clear();
//                for(Data data : dataList){
//                    if(data.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
//                        filterDataList.add(data);
//                }
//                results.values = filterDataList;
//                results.count = filterDataList.size();
//            }
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            if(results.count == 0)
//                notifyDataSetInvalidated();
//            else {
//                dataList = (ArrayList<Data>) results.values;
//                notifyDataSetChanged();
//                for(int i=0; i<dataList.size(); i++){
//                    Log.d(TAG, "PublishResults [" + i + "] : " + dataList.get(i).getName());
//                }
//            }
//        }
//
//    }
}
