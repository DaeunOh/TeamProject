package com.example.bbbb.teamproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 */
public class AddreviewFragment extends Fragment {

    private EditText reviewText;
    private AutoCompleteTextView searchText ;
    private Button addbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View layout = inflater.inflate(R.layout.fragment_review,container,false);
        reviewText= layout.findViewById(R.id.review_text);
        searchText= layout.findViewById(R.id.search_text);
        addbutton= layout.findViewById(R.id.button_review);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("리뷰를 등록하시겠습니까?")
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "리뷰가 성공적으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                searchText.setText("");
                                reviewText.setText("");
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });

        return layout;
    }
}
