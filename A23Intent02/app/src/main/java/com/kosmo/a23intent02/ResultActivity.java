package com.kosmo.a23intent02;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    public static final int RESULT_CODE_FAIL_ID = 1;
    public static final int RESULT_CODE_FAIL_PWD = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //메인에서 전달한 부가데이터를 받아서 텍스트뷰에 설정한다.
        final Intent intent = getIntent();
        final String user = intent.getStringExtra("USER");
        final String pass = intent.getStringExtra("PASS");

        ((TextView)findViewById(R.id.textview_for_result)).setText(
                String.format("아이디 : %s, 패스워드:%s", user, pass)
        );

        //결과값 전송 버튼에 리스너 부착
        //버튼에 리스너 부착
        ((Button)findViewById(R.id.btn_finish_for_result)).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        /*
                        아아디/패스워드 일치여부를 판단한후 setResult()를
                        통해 결과값을 메인액티비티
                         */
                        if(!"kosmo".equals(user)){
                            intent.putExtra("FAIL_ID", "아이디 오류");
                            setResult(RESULT_CODE_FAIL_ID, intent);
                        }
                        else if(!"1234".equals(pass)){
                            intent.putExtra("FAIL_PWD", "비밀번호 오류");
                            setResult(RESULT_CODE_FAIL_PWD, intent);
                        }
                        else{
                            intent.putExtra("SUCCESS", user+"님 안녕하세요");
                            setResult(Activity.RESULT_OK, intent);

                        }
                        finish();
                    }
                }

        );
    }
}