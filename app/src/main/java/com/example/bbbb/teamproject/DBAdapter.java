package com.example.bbbb.teamproject;

/**
 * Created by SUNSOOMIN on 2018-05-22.
 */


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by SUNSOOMIN on 2018-05-22.
 */

public class DBAdapter {

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    // 경로명
    private static String DB_PATH = "/data/data/com.example.bbbb.teamproject/databases/";
    // DB명
    private static String DB_NAME = "Food_Data.db";


    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(this.context);
        try {
            // 1. 디비 생성
            DBHelper.createDatabase();
            // 2. 디비 열기
            DBHelper.openDataBase();
        } catch (IOException e) {
            Log.d("DBAdapter", e.toString());
        } catch (SQLException e) {
            Log.d("DBAdapter", e.toString());
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private SQLiteDatabase sqlite;
        private final Context crContext;

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, 3);
            this.crContext = context;
        }

        public void createDatabase() throws IOException {
            if (!checkDatabase()) { // 디비가 있는지 체크
                this.getReadableDatabase();
                try {
                    copyDatabase(); // 디비복사
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            }
        }

        public boolean checkDatabase() {
            SQLiteDatabase checkDB = null;
            try {
                String myPath = DB_PATH + DB_NAME;
                // 디비 열어서 그 결과를 checkDB에 넣는다.
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);

            } catch (SQLiteException e) {
                Log.d("checkDatabase", e.toString());
            }

            if (checkDB != null) {
                checkDB.close();
            }
            // checkDB에 값이 있으면 true. 즉 이미 디비가 있으면 복사하지 않는다. false면 무조건 복사
            return checkDB != null ? true : false;
            //return false;
        }

        // 디비 복사
        private void copyDatabase() throws IOException {

            // assets 폴더에 있는 디비을 열어 InputStream 에 넣는다.
            InputStream myInput = crContext.getAssets().open(DB_NAME);

            String outFileName = DB_PATH + DB_NAME;
            // 복사하기 위해 OutputStream 선언
            OutputStream myOutput = new FileOutputStream(outFileName);

            // 1메가 단위까지만 읽어 드린다. 이보다 큰 단위는 mp4로 변환하여 복사한다.
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // myOutput, myInput close
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }

        public void openDataBase() throws SQLException {
            String myPath = DB_PATH + DB_NAME;
            sqlite = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        }

        @Override
        public synchronized void close() {
            if (sqlite != null)
                sqlite.close();
            super.close();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    // 읽기 전용모드로 데이터베이스 오픈
    public SQLiteDatabase getReadableDatabase() {
        // TODO Auto-generated method stub
        db = DBHelper.getReadableDatabase();
        return null;
    }

    // 읽고 쓰기 모드로 데이터베이스 오픈
    public SQLiteDatabase getWritableDatabase() {
        // TODO Auto-generated method stub
        db = DBHelper.getWritableDatabase();
        return null;
    }

}