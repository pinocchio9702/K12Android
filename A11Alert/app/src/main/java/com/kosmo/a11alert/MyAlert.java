package com.kosmo.a11alert;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class MyAlert {
    //2번경고창 : 내용만 있음 메소드 오버라이딩 됨
    public static void AlertShow(Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage("\n"+msg+"\n" + "Back버튼으로 닫힘");
        builder.setPositiveButton("확인", null);

        AlertDialog alert = builder.create();
        alert.show();

        //AlertDialog의 가운데 정렬을 위한...setting
        //Must call show() prior to fetching text view
        TextView messageView =
                (TextView)alert.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }
    //1번 경고창 : 타이틀, 내용, 확인버튼 있음
    public static void AlertShow(Context context, String msg, String title){
        //경고창을 띄우기 위한 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //아이콘 설정(기본 이미지 사용)
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //제목 설정
        builder.setTitle(title);
        //안드로이드의 물리적버튼(Back버튼)을 눌렀을때 동작방식
        builder.setCancelable(false);/*
            AlertDialog를 띄운상태에서 BACK버튼을 눌렀을때 대화창이
            닫히지 않게 해준다. TRUE로 지정한 경우에는 닫히게 된다.
            또한 여백을 눌러도 닫히게 된다.

        */
        //메세지 창의 내용
        builder.setMessage("\n"+msg+"\n");
        //확인 버튼
        builder.setPositiveButton("확인", null);
        //위에서 설정한 내용으로 대화창을 생성하고, 화면에 띄운다.
        AlertDialog alert = builder.create();
        alert.show();

        //아래부분을 주석처리 하면 메세지는 좌측정렬된다.
        //AlertDialog의 가운데 정렬위한..Setting
        //Must call show() prior to fetching text view
        //TextView messageView =
        //        (TextView)alert.findViewById(android.R.id.message);
        //messageView.setGravity(Gravity.CENTER);
    }

}
