package com.kosmo.a18optionmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //레이웃과 이미지뷰를 맴버변수로 선언
    private RelativeLayout layout;
    private ImageView imageView;
    //이미지 회전과 확대/축소를 위한 맴버벼수
    private float rotation;
    private float scaleXY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (RelativeLayout)findViewById(R.id.layout);
        imageView = (ImageView)findViewById(R.id.imageview);

    }

    //오버라이딩1 : 옵션메뉴를 뷰에 추가하기 위한 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //리소스에 정의한 option.menu.xml을 가져와서 액티비티에 전개한다.
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //오버라이딩2 : 옵션메뉴를 클릭했을때의 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.optmenu_1:
                Toast.makeText(this, "옵션메뉴1선택",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.optmenu_2:
                Toast.makeText(this, "옵션메뉴2선택",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.optmenu_3:
                Toast.makeText(this, "옵션메뉴3선택",
                        Toast.LENGTH_SHORT).show();
                break;
                /*
                RGB 메뉴를 클릭하는 경우 해당 레이아웃의 배경색을 변경한다.
                레이아웃은 XML에서 뿐만아니라 JAVA에서도 추가/변경 가능한다.
                 */
            case R.id.bg_red:
                layout.setBackgroundColor(Color.RED);
                break;
            case R.id.bg_green:
                layout.setBackgroundColor(Color.GREEN);
                break;
            case R.id.bg_blue:
                layout.setBackgroundColor(Color.BLUE);
                break;
                //이미지 크기, 회전에 대한 처리
            case R.id.image_rotation:
                if(rotation==360) rotation=0;
                rotation += 90;//90도씩 증가하면서 회전
                imageView.setRotation(rotation);
                break;
            case R.id.image_scale_inc:
                if(scaleXY!=5) scaleXY +=2;//2배씩 확대
                imageView.setScaleX(scaleXY);
                imageView.setScaleY(scaleXY);
                break;
            case R.id.image_scale_desc:
                if(scaleXY > 1) scaleXY -=2; //2배씩 축소
                imageView.setScaleX(scaleXY);
                imageView.setScaleY(scaleXY);
                break;

        }


        return super.onOptionsItemSelected(item);
    }
}