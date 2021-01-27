package com.kosmo.a40fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //프레그먼트 관리자 및 자바코드로 교체할 프레그먼트 객체 선언
    FragmentManager fragmentManager;
    MyFragment1 myFragment1;
    MyFragment2 myFragment2;
    MyFragment3 myFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼객체 생성 및 리스너 부착
        //상단영역의 프레그먼트에 정의된 버튼을 가져와서 리스너 부착
        Button btn1Frag = (Button)findViewById(R.id.btnFirstFragment);
        Button btn2Frag = (Button)findViewById(R.id.btnSecondFragment);
        Button btn3Frag = (Button)findViewById(R.id.btnThirdFragment);

        btn1Frag.setOnClickListener(listener);
        btn2Frag.setOnClickListener(listener);
        btn3Frag.setOnClickListener(listener);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        //각 부분화면을 전담할 프레그먼트 객체 생성
        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();
        myFragment3 = new MyFragment3();
        //첫번째 프레드먼트 Bundle객체를 통해 데이터를 전달한다.
        Bundle bundle = new Bundle();//번들객체 생성
        bundle.putString("KOSMO", "한국소프트웨어인재개발원");//데이터 저장
        myFragment1.setArguments(bundle);//데이터 전달
        fragmentTransaction.replace(R.id.bottomLayout, myFragment1).commit();//프레그먼트 교체 및 적용
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.btnFirstFragment){
                fragmentManager.beginTransaction().replace(R.id.bottomLayout, myFragment1).commit();
            }
            else if(view.getId() == R.id.btnSecondFragment){
                fragmentManager.beginTransaction().replace(R.id.bottomLayout, myFragment2).commit();
            }
            else if(view.getId() == R.id.btnThirdFragment){
                fragmentManager.beginTransaction().replace(R.id.bottomLayout, myFragment3).commit();
            }
        }
    };

    //프레그먼트 1, 2는 내부클래스로 정의한다.
    public static  class MyFragment1 extends  Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            /*
            자바코드로 프레그먼트를 붙힐경우 세번째 인자는 false로 기술한다.
            그렇지 않으면 ANR(앱응답없음)이 발생한다.
             */
            View view = inflater.inflate(R.layout.my_fragment1, container, false);

            //데이터 받기 - 버튼을 눌렀을때 받은 데이터 출력
            Button button1 = view.findViewById(R.id.button1);
            button1.setOnClickListener(new View.OnClickListener(){
                /*
                프레그먼트 교체시 번들객체를 통해 전달되었던 데이터를 아래와 같이
                받을 수 있다.
                 */
                @Override
                public void onClick(View view) {
                    Bundle bundle = getArguments();//번들객체를 받아와서
                    String value = bundle.getString("KOSMO");//key값으로 value을 가져옴
                    Toast.makeText(view.getContext(), value, Toast.LENGTH_LONG).show();
                }
            });

            return view;
        }
    }

    public static class MyFragment2 extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.my_fragment2, container, false);

            ((Button)view.findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),
                            "두번째 프레임먼트 입니다.",
                            Toast.LENGTH_LONG).show();
                }
            });
            return view;

        }
    }



}