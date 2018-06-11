package com.example.bbbb.teamproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_dropdown_item_1line;

/**
 */



public class AddreviewFragment extends Fragment {

    private EditText reviewText;
    private AutoCompleteTextView searchText;
    private Button addbutton;
    public String msg, name, user_name;
    private Activity activity;

    FirebaseDatabase database;
    DatabaseReference mDatabase;

    private List<String> list;

    private boolean destroy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user_name = bundle.getString("username");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_review, container, false);
        reviewText = layout.findViewById(R.id.review_text);
        searchText = (AutoCompleteTextView) layout.findViewById(R.id.search_text);
        addbutton = layout.findViewById(R.id.button_review);
        destroy=false;

        list = new ArrayList<String>();
        settingList();

        searchText.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,  list ));

        addbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle(R.string.enrollmentreview)
                        .setPositiveButton(R.string.enrollment, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), R.string.sucessreview, Toast.LENGTH_SHORT).show();

                                database = FirebaseDatabase.getInstance();
                                mDatabase = database.getReference();

                                msg = reviewText.getText().toString();
                                name = searchText.getText().toString();

//                                ((OnApplySelectedListener)activity).onCatagoryApplySelected(name);
//                                Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                                mDatabase.child("Review/" + user_name ).child( name ).push().setValue(msg);
//                                mDatabase.child("Review/" + name ).push().setValue(msg);
                                mDatabase.child("AllReview/").push().setValue(msg);


                                reviewText.setText("");
                                searchText.setText("");

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

    private void settingList() {
        FirebaseDatabase.getInstance().getReference().child("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    String sname = childSnapShot.getKey();
                    list.add(sname);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }



    public boolean getdestroy()
    {
        return destroy;
    }

    public interface OnApplySelectedListener {

         void onCatagoryApplySelected(String name);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity ) {
            this.activity = (Activity) context;
        }
    }





}