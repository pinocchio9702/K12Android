package com.kosmo.a22intent01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //투표수를 저장할 정수형 배열
    int[] votes = new int[9];

    //ImageView 객체를 저장할 배열
    ImageView[] imageViews = new ImageView[9];

    //영화제목을 저장할 배열
    String[] titles = {"블랙펜선", "스파이더맨", "아이언맨",
            "인피니티워", "앤드맨&와스프", "인크레더블헐크",
            "시빌워", "원터솔져", "토르나그나로크"};

    /*
    ImageView의 리소스 아이디를 저장하기 위한 배열로
    안드로이드에서는 리소스 아이디를 내부적으로 처리할때 int형으로 사용한다.
     */
    //ImageView의 리소스 아이디를 저장할 배열
    int[] resourceIds = {R.id.iv1, R.id.iv2, R.id.iv3,
            R.id.iv4,R.id.iv5,R.id.iv6,
            R.id.iv7,R.id.iv8,R.id.iv9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("당신이 좋아하는 영화는?");
        setContentView(R.layout.activity_main);

        //이미지뷰 위젯얻어와서 리스너 부착
        for(int i =0; i<votes.length; i++){
            //영화갯수만큼 반복하면서 이미지를 얻어와서 리스너 부착
            final int index = i;
            imageViews[i] = (ImageView)findViewById(resourceIds[i]);
            imageViews[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(votes[index]<5){
                        votes[index]++;
                        Toast.makeText(MainActivity.this,
                                String.format("%s:%d표", titles[index], votes[index]),
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this
                                , "5점이 최고점수입니다."
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //결과 보기 버튼
            ((Button)findViewById(R.id.btn_result)).setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            //새로운 액티비티를 실행하기 위해 Intent객체 생성
                            Intent intent = new Intent(v.getContext(),
                                    ResultActivity.class);
                            //부가데이터를 넘기기 위한 준비. Map컬렉션같이 key와 value로 설정
                            intent.putExtra("votes", votes);
                            intent.putExtra("titles", titles);
                            //액티비티 실행
                            startActivity(intent);
                        }
                    }
            );

        }

    }
}