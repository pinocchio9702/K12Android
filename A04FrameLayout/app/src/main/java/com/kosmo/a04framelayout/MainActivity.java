package com.kosmo.a04framelayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 객체를 얻어와서 리스너를 부착함. 클랙했을때 두번째 엑티비티를 실행함.
        Button btn_second = (Button)findViewById(R.id.btn_second);
        btn_second.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        FrameActivity.class);
                startActivity(intent);
            }
        });

    }
}