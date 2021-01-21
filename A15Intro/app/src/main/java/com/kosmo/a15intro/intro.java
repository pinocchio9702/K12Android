package com.kosmo.a15intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class intro extends AppCompatActivity {

    /*
    Manifest.xml 파일을 수정하여 Intro액티비티가 가장먼저
    실행되도록 한다.
     */
    //일정시간 이후에 실행하기 위해 Handler객체를 생성한다.
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //인트로 화면 이후에 메인액티비티를 실행하기 위해 인텐트 객체 생성
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            //액티비티 실행
            startActivity(intent);


            /*
            액티비티가 실행되거나 종료될때 에니메이션 효과를 부여한다.
            인자 1 : 새롭게 실행되는 액티비티의 애니메이션
            인자 2 : 종료되는 액티비티에 적용
             */
            overridePendingTransition(R.anim.slide_in_up,
                    R.anim.hold);

            //인트로 액티비티를 종료한다.
            finish();

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

    }

    //액티비티 실행시 3번째로 실행되는 수명주기 함수
    @Override
    protected void onResume() {
        //Intro화면에 진입한 후 2초후에 runnable 객체를 실행한다.
        super.onResume();

        handler.postDelayed(runnable, 2000);
    }

    //액티비티 종료시 첫번째로 실행되는 수명주기 함수
    @Override
    protected void onPause() {
        //Intro가 종료될때 handler에 예약해놓은 작업을 취소한다.
        super.onPause();

        handler.removeCallbacks(runnable);
    }
}