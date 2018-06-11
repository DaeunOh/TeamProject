package com.example.bbbb.teamproject;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bbbb_ on 2018-05-20.
 */

public class Roulette {
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private DatabaseReference mTitleRef;

    private Context context;
    private ImageView imageView;
    private ImageButton button;
    private Button nextButton;
    private Button typeOkButton;
    private Button priceOkButton;

    private int checkMethod = -1;
    private boolean checkType[] = new boolean[6];
    private int checkPrice = -1;

    private List<String> koStoreList = new ArrayList<>();
    private List<String> chStoreList = new ArrayList<>();
    private List<String> jpStoreList = new ArrayList<>();
    private List<String> buStoreList = new ArrayList<>();
    private List<String> ffStoreList = new ArrayList<>();
    private List<String> asoList = new ArrayList<>();

    private List<String> storeList5000 = new ArrayList<>();
    private List<String> storeList10000 = new ArrayList<>();
    private List<String> storeList15000 = new ArrayList<>();
    private List<String> storeListAll = new ArrayList<>();

    private List<String> combinedList = new ArrayList<>();


    public Roulette(Context context, ImageView imageView, ImageButton button) {
        this.context = context;
        this.imageView = imageView;
        this.button = button;
    }

    public void setComponents(final ImageButton button) {
        getStoreList();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] methods = new String[]{"종류별 탐색", "가격대별 탐색"};
                final String[] items = new String[]{"한식", "중식", "일식", "분식", "패스트푸드", "기타"};
                final String[] price = new String[]{"5000원 이하", "10000원 이하", "15000원 이하", "상관 없음"};

                AlertDialog.Builder builder0 = new AlertDialog.Builder(context);
                builder0.setTitle("원하는 방법을 선택해주세요.")
                        .setSingleChoiceItems(
                                methods,
                                -1,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkMethod = which;
                                        nextButton.setEnabled(true);
                                    }
                                }
                        ).setPositiveButton("다음", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 종류별 탐색
                        if (checkMethod == 0) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("원하는 종류를 선택해주세요.")
                                    .setMultiChoiceItems(
                                            items,
                                            new boolean[]{false, false, false, false, false, false},
                                            new DialogInterface.OnMultiChoiceClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                    checkType[which] = isChecked;
                                                    typeOkButton.setEnabled(false);
                                                    if (checkType[0] || checkType[1] || checkType[2] || checkType[3] || checkType[4] || checkType[5]) {
                                                        typeOkButton.setEnabled(true);
                                                    }
                                                }
                                            }
                                    )
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            rouletteAnimation(checkMethod);
                                            //selectRandomStore(checkMethod);
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(context, "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            final AlertDialog dialog1 = builder1.create();
                            // ok 버튼 비활성화
                            dialog1.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialog) {
                                    typeOkButton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                                    typeOkButton.setEnabled(false);
                                }
                            });
                            dialog1.show();
                        }
                        // 가격대별 탐색
                        else if (checkMethod == 1) {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                            builder2.setTitle("원하는 가격대를 선택해주세요.")
                                    .setSingleChoiceItems(
                                            price,
                                            -1,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    checkPrice = which;
                                                    priceOkButton.setEnabled(true);
                                                }
                                            }
                                    )
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            rouletteAnimation(checkMethod);
                                            //selectRandomStore(checkMethod);
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(context, "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            final AlertDialog dialog2 = builder2.create();
                            // ok 버튼 비활성화
                            dialog2.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialog) {
                                    priceOkButton = dialog2.getButton(DialogInterface.BUTTON_POSITIVE);
                                    priceOkButton.setEnabled(false);
                                }
                            });
                            dialog2.show();
                        }
                    }
                });

                final AlertDialog dialog0 = builder0.create();
                // next 버튼 비활성화
                dialog0.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        combinedList.clear();
                        checkType = new boolean[6];
                        nextButton = dialog0.getButton(DialogInterface.BUTTON_POSITIVE);
                        nextButton.setEnabled(false);
                    }
                });
                dialog0.show();
            }
        });
    }

    private void getStoreList() {
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

                storeList5000.clear();
                storeList10000.clear();
                storeList15000.clear();
                storeListAll.clear();

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

                    if (storeSnapshot.child("price").getValue().equals("5000"))
                        storeList5000.add(String.valueOf(storeSnapshot.getKey()));
                    else if (storeSnapshot.child("price").getValue().equals("10000"))
                        storeList10000.add(String.valueOf(storeSnapshot.getKey()));
                    else if (storeSnapshot.child("price").getValue().equals("15000"))
                        storeList15000.add(String.valueOf(storeSnapshot.getKey()));

                    storeListAll.add(String.valueOf(storeSnapshot.getKey()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void rouletteAnimation(final int method) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                button.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setVisibility(View.VISIBLE);
                selectRandomStore(method);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        animation.setAnimationListener(animationListener);
        imageView.startAnimation(animation);
        Toast.makeText(context, "탐색중 ...", Toast.LENGTH_SHORT).show();
    }

    public void selectRandomStore(int method) {
        if (method == 0) {
            if(checkType[0]){
                combinedList.addAll(koStoreList);
            } if(checkType[1]){
                combinedList.addAll(chStoreList);
            } if(checkType[2]){
                combinedList.addAll(jpStoreList);
            } if(checkType[3]){
                combinedList.addAll(buStoreList);
            } if(checkType[4]){
                combinedList.addAll(ffStoreList);
            } if(checkType[5]){
                combinedList.addAll(asoList);
            }
        }
        else if (method == 1) {
            if(checkPrice == 0){
                combinedList.addAll(storeList5000);
            } else if(checkPrice == 1){
                combinedList.addAll(storeList5000);
                combinedList.addAll(storeList10000);
            } else if(checkPrice == 2){
                combinedList.addAll(storeList5000);
                combinedList.addAll(storeList10000);
                combinedList.addAll(storeList15000);
            } else {
                combinedList.addAll(storeListAll);
            }
        }

        int num = (int) (Math.random()*combinedList.size());
        combinedList.get(num);

    }
}