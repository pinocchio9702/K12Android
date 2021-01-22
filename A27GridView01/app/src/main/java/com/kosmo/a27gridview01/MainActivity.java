package com.kosmo.a27gridview01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KoSMO";

    String[] names = {"엑소", "방탄소년단", "워너원", "뉴이스트", "갓세븐",
                    "비투비", "빅스"};
    String[] ages = {"9", "7", "11", "5", "7", "7", "6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //커스텀 어뎁터 객체 생성
        final MyAdapter adapter = new MyAdapter();
        //그리드뷰 얻어와서 어뎁터와 연결 및 리스너 설정
        GridView gridView1 = findViewById(R.id.gridView1);
        gridView1.setAdapter(adapter);

        gridView1.setOnItemClickListener(new
             AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //콜백되는 index를 통해 행과 열을 계산해서 로그로 출력력
                    int row = position / 2;
                     int column = position % 2;

                     Log.d(TAG, "position : " + position);
                     Log.d(TAG, "Row index : " + row + " Column iindex : " +
                             column);
                     Log.d(TAG, names[row * 2 + column]);
                 }
             });
    }

    //커스텀된 MyAdapter
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Java코드로 레이아웃 생성 및 방향설정정
           LinearLayout layout =
                    new LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView view1 = new TextView(getApplicationContext());
            view1.setText(names[position]);
            view1.setTextSize(40.0f);
            view1.setTextColor(Color.BLUE);

            TextView view2 = new TextView(getApplicationContext());
            view2.setText(ages[position]);
            view2.setTextSize(40.0f);
            view2.setTextColor(Color.RED);

            layout.addView(view1);
            layout.addView(view2);

            return layout;
        }
    }
}