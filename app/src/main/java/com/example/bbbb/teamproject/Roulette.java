package com.example.bbbb.teamproject;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by bbbb_ on 2018-05-20.
 */

public class Roulette {
    Context context;

    public Roulette(Context context) {
        this.context = context;
    }

    public void setComponents(ImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = new String[]{"한식", "중식", "일식", "분식", "패스트푸드"};
                final String[] price = new String[]{"5000원 이하", "10000원 이하", "15000원 이하", "상관 없음"};

                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                dialog1.setTitle("원하는 종류를 선택해 주세요.")
                        .setMultiChoiceItems(
                                items,
                                new boolean[]{false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) { // 종류 선택했을 때

                                        }
                                    }
                                }
                        )
                        .setPositiveButton("다음", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder dialog2 = new AlertDialog.Builder(context);
                                dialog2.setTitle("원하는 가격대를 선택해 주세요.")
                                        .setMultiChoiceItems(
                                                price,
                                                new boolean[]{false, false, false, false},
                                                new DialogInterface.OnMultiChoiceClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                        // 가격대 선택했을 때
                                                    }
                                                }
                                        )
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(context, "탐색중 ...", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(context, "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                dialog2.create();
                                dialog2.show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "취소 버튼을 눌렀습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog1.create();
                dialog1.show();
            }
        });
    }
}