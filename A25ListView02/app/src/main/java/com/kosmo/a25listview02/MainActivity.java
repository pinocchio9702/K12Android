package com.kosmo.a25listview02;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  static  final String TAG = "lecture";

    //2가지 형태의 데이터로 사용할 배열
    String[] idolGroup = {"엑소", "방탄소년단", "워너원", "뉴이스트", "갓세븐","비투비","빅스"};
    int[] teamCount = {9,7,11,5,7,7,6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView1 = (ListView)findViewById(R.id.listView1);

        //ArrayAdapter<String> adapter1;
        //adapter1 = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, names);
        //listView1.setAdapter(adapter1);

        //2단계
        //어뎁터 객체 생성
        final MyAdapter adapter = new MyAdapter();
        //리스트뷰에 어댑터를 설정한 후 리스너 부착
        listView1.setAdapter(adapter);

        //4단계
        listView1.setOnItemClickListener(new
             AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     Toast.makeText(getApplicationContext(),
                             "선택한항목 : " +idolGroup[position]+"("+teamCount[position]+")",
                             Toast.LENGTH_SHORT).show();
                 }
             });
    }
    /*
    개발자정의 커스텀 어뎁터 객체 선언
        : BaseAdapter를 상속받아 선언한다. 그리고 관련 메소드를
        오버라딩 한다.
     */
    class MyAdapter extends BaseAdapter{
        //어뎁터 객체가 가진 항목의 갯수를 반환
        @Override
        public int getCount() {
            return idolGroup.length;
        }
        //어댑터가 가진 하나의 항목을 반환
        @Override
        public Object getItem(int position) {
            return idolGroup[position];
        }
        //인덱스를 반환
        @Override
        public long getItemId(int position) {
            return position;
        }
        //어댑터가 가진 뷰중에 하나를 반환
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Java코드를 통해 레이아웃 및 텍스트뷰 생성
            //첫번째 텍트스뷰 : 그룹명을 표시
            TextView view1 = new TextView(getApplicationContext());
            view1.setText(idolGroup[position]);
            view1.setTextSize(40.0f);
            view1.setTextColor(Color.BLUE);

            //두번째 텍스트뷰 : 인원수 출력
            TextView view2 = new TextView(getApplicationContext());
            /*
            텍스트뷰에는 가존자료형으로 데이터를 삽입할 수 없으므로 정수를
            Boxing처리해서 삽입한다.
             */
            view2.setText(new Integer(teamCount[position]).toString());
            view2.setTextSize(40.0f);
            view2.setTextColor(Color.RED);

            //리니어레이아웃을 생성한 후 텍스트뷰를 추가한다.
            LinearLayout layout = new
                    LinearLayout(getApplicationContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            layout.addView(view1);
            layout.addView(view2);

            return layout;
        }
    }
}