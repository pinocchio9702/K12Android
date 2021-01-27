package com.kosmo.a38sqlite01;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ikosmo";
    //SQLite를 사용하기 위한 객체
    SQLiteDatabase database;
    TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2 = findViewById(R.id.textView2);
    }

    //메세지 출력용 메소드. 텍스트뷰에 출력 내용을 추가한다.
    private  void printInfo(String msg){
        textView2.append(msg + "\n");
    }

    //DB생성
    public void onBtn1Clicked(View v){
        String dbName = "customer.sqlite";

        try{
            database = openOrCreateDatabase(dbName, Activity.MODE_PRIVATE, null);

            printInfo("데이터베이스 생성 : " + dbName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //테이블 생성
    public void onBtn2Clicked(View v){
        String sql = "create table if not exists customer (name text, age integer, mobile text)";
        try{
            //쿼리문 실행
            database.execSQL(sql);

            printInfo("테이블 생성 : customer");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //데이터 삽입
    public void onBtn3Clicked(View v){
        String sql1 = "insert into customer " +
                " (name, age, mobile) values ('홍길동', 20, '010-1111-2222') ";
        String sql2 = "insert into customer  " +
                "  (name, age, mobile) values ('감강찬', 25, '010-7777-2222') ";

        try{
            database.execSQL(sql1);
            printInfo("레코드 추가 : 1");

            database.execSQL(sql2);
            printInfo("레코드 추가 2");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void onBtn4Clicked(View v){
        String sql = "select name, age, mobile from customer";
        try{
            //select쿼리를 실행 후 커서 객체에 저장
            Cursor cursor = database.rawQuery(sql, null);

            int count = cursor.getCount();
            printInfo("데이터 갯수" + count);

            int i = 0;
            while(i < count){
                //커서를 다음 행으로 이동시킨다.
                cursor.moveToNext();

                //레코드의 각 데이터를 인덱스를 통해 읽어온다.
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                Log.d(TAG, "# " + name + " : " + age + " : " + mobile);
                printInfo("# " + name + " : " + age + " : " + mobile);

                i++;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}