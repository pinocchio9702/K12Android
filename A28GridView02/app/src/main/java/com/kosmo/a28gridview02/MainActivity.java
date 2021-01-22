package com.kosmo.a28gridview02;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    int[] resIds = {
            R.drawable.movie01,R.drawable.movie02,R.drawable.movie03,
            R.drawable.movie04,R.drawable.movie05,R.drawable.movie06,
            R.drawable.movie07,R.drawable.movie08,R.drawable.movie09};

    String[] movies ={
            "리턴","마파도","말아톤",
            "명량","변호인","설국열차",
            "스물","신세계","해적"};

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //커스텀뷰를 생성한 후 그리드뷰와 연결하고 리스너 부착
        CustomAdapter adapter = new CustomAdapter(this, movies, resIds);
        gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //커스텀 대화상자를 띄우기 위해 XML레이아웃을 inflate한다.
                LayoutInflater inflater=(LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                View dialogLayout = inflater.inflate(R.layout.dialog_layout,null);
                //커스텀 대화상자에 이미지를 설정한다.
                ImageView image=(ImageView)dialogLayout.findViewById(R.id.moviedialog);
                image.setImageResource(resIds[i]);
                //커스텀 대화상자 기본설정후 출력
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(movies[i])
                        .setIcon(android.R.drawable.ic_dialog_email)
                        .setView(dialogLayout)
                        .setPositiveButton("확인",null)
                        .show();
            }
        });
    }
}