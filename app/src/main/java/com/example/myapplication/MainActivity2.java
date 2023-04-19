package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    SQLiteDatabase database = null;
    DBHelper helper;
    String DBNAME = "TESTDB";
    Boolean dbflag =false;
    Boolean tableflag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this, DBNAME,null,1);
        TextView textView = findViewById(R.id.textview);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dbflag) {
                    database = helper.getWritableDatabase();
                    if (database == null){
                        textView.append("\n DB가 생성되지 않았습니다.");
                    }else {
                        dbflag = true;
                        textView.append("\n DB가 생성되었습니다.");
                    }
                }else {
                    textView.append("\n 이미 DB가 생성되었습니다.");
                }
            }
        });

        Button btn2 =findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbflag && !tableflag){
                    database = helper.getWritableDatabase();
                    helper.onCreate(database);
                    tableflag = true;
                    textView.append("\n 테이블이 생성되었습니다.");
                }else if (!dbflag){
                    textView.append("\n DB를 먼저 만들어 주세요.");
                }else {
                    textView.append("\n 이미 테이블이 생성되었습니다.");
                }
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableflag) {
                    helper.deleteTable();
                    tableflag = false;
                    textView.append("\n 테이블이 삭제되었습니다.");
                } else {
                    textView.append("\n 테이블이 없습니다.");
                }
            }
        });

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableflag){
                    long result = helper.insertData("홍길동",21,"010-2369-4112");
                    if (result == -1L){
                        textView.append("\n 중복된 데이터 입니다.");
                    }else {
                        textView.append("\n 데이터가 삽입되었습니다.");
                    }
                }else {
                    textView.append("\n 테이블이 준비되지 않았습니다.");
                }
            }
        });

        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableflag){
                    Cursor cursor = helper.searchData();
                    int count = cursor.getCount();
                    if (cursor.getCount() == 0){
                        textView.append("\n 테이블이 비었습니다.");
                    }else {
                        cursor.moveToFirst();
                        for (int i = 0; i < count; i++) {
                            int id = cursor.getInt(0);
                            String name = cursor.getString(1);
                            int age = cursor.getInt(2);
                            String phone = cursor.getString(3);
                            textView.append("\n id = " + id + ", 이름 : " + name + ", 나이 : " + age + ", 전화번호 : " + phone);
                            cursor.moveToNext();
                        }
                    }
                    cursor.close();
                }else {
                    textView.append("\n 테이블이 준비되지 않았습니다.");
                }
            }
        });

        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database =helper.getWritableDatabase();
                String sql = "SELECT * FROM " + helper.TBLNAME + ";";
                Cursor cursor = database.rawQuery(sql,null);
                if (cursor.getCount()==0){
                    textView.append("\n 테이블이 비어있습니다.");
                }else {
                    cursor.moveToFirst();
                    int id = cursor.getInt(0);
                    helper.deleteDATA(id);
                    textView.append("\n 데이터가 삭제되었습니다.");
                }
            }
        });

    }
}