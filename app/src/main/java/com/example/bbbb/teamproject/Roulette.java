package com.example.bbbb.teamproject;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by bbbb_ on 2018-05-20.
 */

public class Roulette {
    Context context;
    ImageView imageView;
    ImageButton button;
    Context context2;



    public Roulette(Context context, ImageView imageView,ImageButton button, Context context2) {
        this.context = context;
        this.imageView = imageView;
        this.button=button;
        this.context2 = context2;

    }

    public void setComponents(final ImageButton button) {
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

                                                Animation animation = AnimationUtils.loadAnimation(context2, R.anim.rotate);
                                                imageView.startAnimation(animation);
                                                // 가격대 선택했을 때
                                            }
                                                }
                                        )
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Animation animation = AnimationUtils.loadAnimation(context2, R.anim.rotate);
                                                Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                                                    @Override
                                                    public void onAnimationStart(Animation animation) {
                                                        button.setVisibility(View.INVISIBLE);
                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animation animation) {
                                                        button.setVisibility(View.VISIBLE);
                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animation animation) {

                                                    }
                                                };

                                                animation.setAnimationListener(animationListener);
                                                imageView.startAnimation(animation);
                                                Toast.makeText(context2, "탐색중 ...", Toast.LENGTH_SHORT).show();



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

    public Bitmap rotateImage(Bitmap src, int i) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(i,src.getWidth()/2,src.getHeight()/2);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }
}