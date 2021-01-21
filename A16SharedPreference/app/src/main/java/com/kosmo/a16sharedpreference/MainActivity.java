package com.kosmo.a16sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /*
    SharedPreferences
        : 해당 어플리케이션에서 저장하고 싶은 데이터가 있을때 단말기에
        파일형태로 보관해주는 기능을 제공한다. 해당 파일은 앱이 삭제되기
        전까지 유지되고, 삭제될때 파일도 같이 삭제된다.
        자동로그인, 게임스코어 유지, 앱의 마지막상태 저장 등에 사용된다.

     */
    SharedPreferences.Editor editor;
    EditText tvID;
    EditText tvPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        SharedPreferences 객체에 저장된 내용이 있는지 확인한다.
        MODE_PRIVATE : 본인의 앱에서만 사용되도록 설정하는 기본값
         */
        SharedPreferences pref = getSharedPreferences("login", Activity.MODE_PRIVATE);
        editor = pref.edit();

        //위젯을 얻어온다.
        tvID = findViewById(R.id.etID);
        tvPwd = findViewById(R.id.etPwd);

        /*
        getString()으로 저장된 값을 가져와서 입력상자에 설정한다.
        만약 저장된 값이 없을경우에는 디폴트 값으로 두번째 인자에
        지정된 값을 사용한다.
        */
        String id = pref.getString("id", "저장된 아이디 없음");
        String pwd = pref.getString("pwd", "");
        //얻어온 값을 입력상제 설정한다.
        tvID.setText(id);
        tvPwd.setText(pwd);
    }
    //로그인 버튼을 눌렀을때
    public void btnLoginClicked(View v){
        //입력상자에 입력된 내용을 가져와서 문자열로 저장
        String sid = tvID.getText().toString();
        String spwd = tvPwd.getText().toString();
        //SharedPreferences 객체에 데이터를 저장한다.
        editor.putString("id", sid);
        editor.putString("pwd", spwd);
        //저장후 반그시 commit()을 호출해야 한다.
        editor.commit();

        Toast.makeText(getApplicationContext(), "아이디/비번 저장됨",
                Toast.LENGTH_SHORT).show();
    }





}