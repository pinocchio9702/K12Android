package com.kosmo.a39sqlite02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ikosmo";

    //SQLite와 어뎁터를 사용하기 위한 맴버변수
    SQLiteDatabase database;
    SingerAdapter adapter;
    TextView textView2;

    String dbName;
    String tname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //테이블명
        tname = "customer";
        //어뎁터 객체 생성 및 리스트뷰 위젯에 설정
        adapter = new SingerAdapter(this);
        ListView listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);
        //리스트뷰에 리스너 부착
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                특정 아이템을 클릭했을때 전달되는 인덱스 i를 통해
                어뎁터 항목을 가져온다. getName()으로 이름을 토스트로 출력한다.
                 */
                SingerDTO item = (SingerDTO)adapter.getItem(i);
                Toast.makeText(getApplicationContext(),
                        "선택항목" + item.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        textView2 = findViewById(R.id.textView2);

        createMyDatabase();
        createMyTable();
        selectAllData();

    }

    //추가하기 버튼
    public void onBtn1Clicked(View v){
        String sql1 = "insert into customer  " +
                " (name, age, mobile) values ('방탄소년단', 7, '010-1234-5678') ";
        String sql2 = "insert into customer  " +
                " (name, age, mobile) values ('레드벨벳', 5, '010-9876-5432') ";

        try{
            database.execSQL(sql1);
            printInfo("데이터 추가 : 1");
            database.execSQL(sql2);
            printInfo("데이터 추가 : 2");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //데이터 조회 및 어뎁터에 추가하기
    public void onBtn2Clicked(View v){
        selectAllData();
    }


    public void printInfo(String msg){
        textView2.append(msg + "\n");
    }

    private void createMyDatabase(){
        try{
            //데이터베이스가 있다면 Open하고 없다면 Create한다.
            database = openOrCreateDatabase("customer.sqlite", Activity.MODE_PRIVATE, null);

            printInfo("데이터 베이스 만듬 : customer.sqlite");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //테이블 생성
    private void createMyTable(){
        //테이블이 없을때만 새롭게 생성한다.
        String sql = "create table if not exists customer (name text, age integer, mobile text) ";
        try{
            database.execSQL(sql);

            printInfo("테이블 만듬 : customer");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //테이블의 모든 레코드를 조회한다.
    private void selectAllData(){
        String sql = "select name, age, mobile from customer";
        try{
            //select쿼리 실행 후 결과를 얻어온다.
            Cursor cursor = database.rawQuery(sql, null);

            //getcount()를 통해 데이터 갯수 조회
            int count = cursor.getCount();
            printInfo("데이터 갯수 : " + count );

            //select된 갯수만큼 반복
            int i = 0;
            while(i < count){
                //결과셋의 다음 항목으로 이동하여 인덱스를 통해 데이터를 가져온다.
                cursor.moveToNext();

                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);

                Log.d(TAG, "#" + name + " : " + age + " : " +  mobile);
                printInfo( "#" + name + " : " + age + " : " +  mobile);

                //이부분이 없으면 리스트뷰가 나오지 않음
                //하나의 레코드를 DTO에 저장한후 리스트뷰에 출력함
                SingerDTO item = new SingerDTO(name, age, mobile);
                adapter.addItem(item);

                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}