package com.kosmo.a23intent02;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //부가데이터를 보낼때 응답을 받기 위한 코드
    public static final int REQUEST_CODE = 0;

    //위젯선언
    private Button btnOnlyData, btnDataReqResp;
    private EditText editUser, editPass;
    private TextView textViewMian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위젝얻기
        btnOnlyData = (Button)findViewById(R.id.btn_only_data);
        btnDataReqResp = (Button)findViewById(R.id.btn_data_req_resp);
        editUser = (EditText)findViewById(R.id.edit_user);
        editPass = (EditText)findViewById(R.id.edit_pass);
        textViewMian = (TextView)findViewById(R.id.textView_main);

        //버튼에 리스너 부착
        btnOnlyData.setOnClickListener(listener);
        btnDataReqResp.setOnClickListener(listener);
    }

    //리스너 부착을 위한 핸들러
    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //인텐트 생성을 통해 실행할 액티비티를 설정한다.
            Intent intent = new Intent(v.getContext(), OnlyActivity.class);
            //부가데이터를 인텐트에 추가한다.
            intent.putExtra("USER", editUser.getText().toString());
            intent.putExtra("PASS", editPass.getText().toString());

            if(v==btnOnlyData){
                //인텐트를 통해 부가데이터를 전달만 한다.
                startActivity(intent);
            }
            else if(v==btnDataReqResp){
                /*
                부가데이터를 전달한 후 결과를 돌려받은.
                startActivityForResult(인텐트, 요청코드)
                    :요청코드는 인텐트를 전달하거나 전달받을 액티비티에서
                    식별자로 사용된다.
                 */
                intent.setClass(MainActivity.this,
                        ResultActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    };

    /*
    부가데이터를 전달했던 액티비티로부터 결과값을 받기 위해 오버라이딩한다.
    해당 메소드는 setReault()가 실행되면 자동으로 콜백된다.즉 임의로
    호출하지 않는다.
    매개변수
        requestCode : 내가 보낸 인텐트 확인용(요청코드)
        resultCode : 인텐트를 받은 액티비티에서 보낸코드(응답코드)
        data : 인텐트를 받았던 액티비티에서 보낸 인텐트(부가데이터 확인)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //내가 보낸 요청코드를 받은 액티비티인지 확인후 텍스트뷰에 출력
        if(requestCode == REQUEST_CODE){
            if(resultCode==ResultActivity.RESULT_CODE_FAIL_ID){
                textViewMian.setText(data.getStringExtra("FAIL_ID"));
            }
            else if(resultCode==ResultActivity.RESULT_CODE_FAIL_PWD){
                textViewMian.setText(data.getStringExtra("FAIL_PWD"));
            }
            else if(resultCode== Activity.RESULT_OK){
                textViewMian.setText(data.getStringExtra("SUCCESS"));
            }
        }

    }
}