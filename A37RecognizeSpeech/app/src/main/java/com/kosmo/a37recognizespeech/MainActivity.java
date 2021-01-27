package com.kosmo.a37recognizespeech;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "iKosmo";
    TextView textView1;
    SpeechRecognizer mRecognizer;//음성인식 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);

        //오디오 권한에 대한 체크여부 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
        //음성인식 객체생성 및 리스너 부착
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(recognitionListener);
    }

    public void onBtn1Clicked(View v) {
        try {
            //첫번째 버튼을 눌렀을때 음성인식 기능을 실행함.
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
            //음성인식 결과에 대한 부가데이터
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 검색");
            //음성을 듣고 인식하기위해 시작
            mRecognizer.startListening(intent);
        }
        catch (ActivityNotFoundException e) {
            Log.d(TAG, "ActivityNotFoundException");
        }
    }

    public void onBtn2Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);
    }

    //음성인식 리스너 정의
    private RecognitionListener recognitionListener = new RecognitionListener() {

        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        //통신이 불가능하거나 음성인식이 되지 않을때 오류발생됨
        @Override
        public void onError(int i) {
            textView1.setText("너무 늦게 말하면 오류가 발생합니다.");
        }

        //음성인식에 성공했을때의 콜백메소드
        @Override
        public void onResults(Bundle bundle) {

            //Bundle객체를 통해 음성인식 결과를 전달받는다.
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = bundle.getStringArrayList(key);

            Log.i(TAG, String.valueOf(mResult.size()));
            for(String s : mResult){
                Log.i(TAG, s);
            }

            String[] rs = new String[mResult.size()];
            //List컬렉션을 배열로 반환
            mResult.toArray(rs);
            //결과값을 텍스트뷰에 표시함
            textView1.setText(rs[0]);

            /////////////////////////////////////////////////////////
            //인식한 문장내에 '피자'라는 단어가 있는경우 토스트로 알림처리
            String[] searchTxtArr = rs[0].split(" ");
            for(String s : searchTxtArr){
                if(s.contains("피자")){
                    Toast.makeText(getApplicationContext(),
                            "피자가 인식되었습니다.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
}
