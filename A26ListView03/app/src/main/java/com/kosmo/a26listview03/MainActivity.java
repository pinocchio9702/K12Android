package com.kosmo.a26listview03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO";

    String[] idolGroup = {"엑소", "방탄소년단", "워너원",
                "뉴이스트", "갓세븐", "비투비", "빅스"};
    int[] teamCount = {9, 7, 11, 5, 7, 7, 6};
    int[] images = {R.drawable.idol1, R.drawable.idol2, R.drawable.idol3,
                R.drawable.idol4, R.drawable.idol5, R.drawable.idol6,
                R.drawable.idol7};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyAdapter adapter = new MyAdapter();

        ListView listView1 = findViewById(R.id.listView1);

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new
                AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getApplicationContext(),
                                "선택한 그룹 : " + idolGroup[position],
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return idolGroup.length;
        }

        @Override
        public Object getItem(int position) {
            return idolGroup[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            IdolView view = new IdolView(getApplicationContext());
            view.setName(idolGroup[position]);
            view.setPerson(new Integer(teamCount[position]).toString());
            view.setImage(images[position]);

            return view;
        }
    }
}