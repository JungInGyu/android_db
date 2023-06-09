package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database = null;
    String DATABASE = "TestDB";
    String TBLNAME = "member";
    Boolean dbflag = false;
    Boolean tableflag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview = findViewById(R.id.textview);
        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dbflag) {

                    database = openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
                    if (database == null) {
                        textview.append("\n 데이터베이스가 생성되지 않았습니다.");
                    } else {
                        textview.append("\n 데이터베이스가 생성되었습니다.");
                        dbflag = true;
                    }
                }else {
                    textview.append("\n 데이터베이스가 이미 생성되었습니다.");
                }
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbflag && !tableflag){
                    String sql = "CREATE TABLE IF NOT EXISTS " + TBLNAME +
                            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT, age INTEGER, phone TEXT NOT NULL UNIQUE);";
                    database.execSQL(sql);
                    tableflag = true;
                    textview.append("\n  테이블이 생성되었습니다");
                } else if (!dbflag) {
                    textview.append("\n 먼저 DB부터 생성하세요");
                } else {
                    textview.append("\n 이미 테이블이 생성되어 있습니다");
                }
            }
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableflag) {
                    String sql = "DROP TABLE IF EXISTS " + TBLNAME + ";";
                    database.execSQL(sql);
                    tableflag = false;
                    textview.append("\n 테이블이 삭제되었습니다.");
                }else {
                    textview.append("\n 삭제할 테이블이 없습니다.");
                }
            }
        });

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableflag) {
                    String sql = "INSERT INTO " + TBLNAME +
                            "(name, age, phone) VALUES" +
                            "('홍길동', 21 ,'010-2369-4112');";
                    try {
                        database.execSQL(sql);
                        textview.append("\n 하나의 레코드가 삽입되었습니다.");
                    } catch (Exception e) {
                        textview.append("\n 중복된 데이터 입니다.");
                    }
                } else {
                    textview.append("\n 테이블이 준비되지 않았습니다.");
                }
            }
        });
        Button btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableflag) {
                    String sql = "SELECT * FROM " + TBLNAME + ";";
                    Cursor cursor = database.rawQuery(sql, null);
                    int count = cursor.getCount();
                    if (count == 0) {
                        textview.append("\n 테이블이 비어있습니다.");
                    }else {
                        cursor.moveToFirst();
                        for (int i=0; i <count; i++){
                            int id = cursor.getInt(0);
                            String name = cursor.getString(1);
                            int age = cursor.getInt(2);
                            String phone = cursor.getString(3);
                            textview.append("\n id = " + id + ", 이름 : " + name + ", 나이 : " + age + ", 전화번호 : " + phone);
                            cursor.moveToNext();
                        }
                    }
                    cursor.close();
                }else {
                    textview.append("\n 테이블이 준비되지 않았습니다.");
                }
            }
        });
        Button btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tableflag){
                    String sql = "SELECT * FROM " + TBLNAME + ";";
                    Cursor cursor = database.rawQuery(sql, null);
                    if (cursor.getCount() == 0){
                        textview.append("\n 테이블에 데이터가 없습니다.");
                    }else {
                        cursor.moveToFirst();
                        int id = cursor.getInt(0);
                        sql = "DELETE FROM " + TBLNAME + " WHERE _id = '"+ id +"';";
                        database.execSQL(sql);
                        textview.append("\n 하나의 레코드를 삭제하였습니다.");
                    }
                    cursor.close();
                }else {
                    textview.append("\n 테이블이 준비되지 않았습니다.");
                }
            }
        });
    }
}