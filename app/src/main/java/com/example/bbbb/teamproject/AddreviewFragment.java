package com.example.bbbb.teamproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 */



public class AddreviewFragment extends Fragment {

    private EditText reviewText;
    private AutoCompleteTextView searchText ;
    private Button addbutton;
    public String msg, name;

    FirebaseDatabase database;
    DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View layout = inflater.inflate(R.layout.fragment_review,container,false);
        reviewText= layout.findViewById(R.id.review_text);
        searchText= layout.findViewById(R.id.search_text);
        addbutton= layout.findViewById(R.id.button_review);


        addbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle(R.string.enrollmentreview)
                        .setPositiveButton(R.string.enrollment, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),R.string.sucessreview, Toast.LENGTH_SHORT).show();

                                database = FirebaseDatabase.getInstance();
                                mDatabase = database.getReference();

                                msg=reviewText.getText().toString();
                                name=searchText.getText().toString();

                                Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                                mDatabase.child("Review/"+name+"review").push().setValue(msg);



                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), R.string.canlebutton, Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });

        return layout;
    }

}