package com.kosmo.a14lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SubActivity extends AppCompatActivity {

    //로그캣을 확인을 위한 상수 선언언
   private static final String TAG = "KOSMO123";

   /*
   Activity의 Lifecycle(수명주기) 메소드
        : 액티비티가 실행되면 아래 순서대로 메소드가 실행된다.
        onCreate() -> onStart() -> onResume()
        액티비티가 종료되면
        onPause() -> onStop() -> onDestory()

        안드로이드에서 수명주기 메소드는 앱이 비정장석으로 종료되는
        시점의 상태를 저장하거나, 앱이 실행될때 자동으로 실행하고자
        하는 명령이 있는경우 사용한다.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Log.d(TAG, "서브액티비티 시작");
        Log.d(TAG, "onCreate()호출");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart()호출");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()호출");
        super.onResume();
    }







    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()호출");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()호출");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()호출");
        super.onDestroy();
    }


    public void SubBtnClicked(View view){
        Log.d(TAG, "서브액티비티 종료버튼 클릭");
        finish();
    }
}