package com.kosmo.a24listview01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO";
    //리스트뷰의 데이터로 사용할 String 배열
    String[] idolGroup ={"엑소", "방타소년단", "워너원","뉴이스트", "갓세븐",
                        "비투비", "빅스","엑소2", "방타소년단2", "워너원2","뉴이스트2", "갓세븐2",
                        "비투비2", "빅스2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        1.리스트뷰의 데이터로 사용할 어댑터 객체생성 및 배열설정
        형식] new ArrayAdapter<>(컨택스트, 리스트뷰의모양, 데이터(배열 혹은 JSON))

         */

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, idolGroup);

        //2. 리스트뷰 위젯을 객체화
        ListView listView1 = findViewById(R.id.listView1);

        //3.리스트뷰에 어댑터 설정
        listView1.setAdapter(adapter);
        //4.리스트뷰에 리스너 부착
        listView1.setOnItemClickListener(new
             AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                     Log.i(TAG, "선택한 idnex : "+i);
                     Toast.makeText(getApplicationContext(),
                             idolGroup[i] + "선택됨",
                             Toast.LENGTH_SHORT).show();
                 }
             });
    }
}