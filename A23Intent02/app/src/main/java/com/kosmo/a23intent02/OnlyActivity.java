package com.kosmo.a23intent02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OnlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only);
        String user = getIntent().getStringExtra("USER");
        String pass = getIntent().getStringExtra("PASS");

        ((TextView)findViewById(R.id.textview_only)).setText(
                String.format("아이디:%s, 비밀번호 : %s", user, pass)
        );
        ((Button)findViewById(R.id.btn_finish)).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }


        );
    }
}