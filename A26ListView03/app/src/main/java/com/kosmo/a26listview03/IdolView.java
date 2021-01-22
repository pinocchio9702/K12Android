package com.kosmo.a26listview03;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IdolView extends LinearLayout {

    TextView textView1;//그룹명
    TextView textView2;//인원수
    ImageView imageView1;//프로필 이미지

    public IdolView(Context context){
        super(context);
        //먼저 xml파일을 전개하고 필요한 값을 가져온다.
        //먼저 xml파일을 전개해야한다는 것이 중요하다.
        //레이아웃의 전개를 위해 Inflater객체를 생성함
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE
                );
        //inflate()를 통해 레이아웃을 전개한다.
        inflater.inflate(R.layout.idol_view, this, true);

        //데이터를 출력할 위젯을 얻어온다.
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        imageView1 = findViewById(R.id.imageView1);


    }

    //각 항목을 설정할 setter() 정의의
   public void setName(String name){
        textView1.setText(name);
    }
    public void setPerson(String pCount){
        textView2.setText(pCount);
    }
    public void setImage(int imgNum){
        imageView1.setImageResource(imgNum);
    }

}