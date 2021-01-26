package com.kosmo.a33asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO";

    ProgressBar mProgress1;
    int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress1 = findViewById(R.id.progressBar1);
    }

    public void onBtnClicked(View v){
        new CounterTask().execute(0);
    }
    /*
    AsyncTask
        :작업 쓰레드와 관련된 복잡한 작업을 쉽게 처리해주는 클래스이다.
        UI에 대한 비동기 작업을 자동으로 실행해주며 개발자가 직접 핸들러
        클래스를 만들 필요가 없다. 실행은 execute()메소드를 호출하면 된다.

       고 생 형식] AsyncTask<Param, Progress, Result>
            Param : 실행시 작업에 전달되는 값(파라미터)의 타입
            Progress : 잡업의 진행정도를 나타내는 값의 타입
            Result : 작업의 결과값을 나타내는 타입

        만약 사용할 필요가 없는 타입이 있다면 Void라고 표기한다.
        또한 모든 매개변수는 가변인자를 사용하여 여러개의 파라미터를
        처리할 수 있도록 정의되어 있다.

        두번째 Progress 매개변수는 onProgressUpdate() 로넘겨주는 값의 타입이다.
        새번째 Result 매개변수는 그냥 doInBackground()의 반환타입이라각하면 된다.

     */
    class CounterTask extends AsyncTask<Integer, Integer, Void> {
        //doInBackground()실행정에 호출되는 메소드
        protected  void onPreExecute(){
            Log.i(TAG, "onPreExecute() 실행");
        }

        /*
        execute()가 호출되면 자동으로 호출되어 동작한다.
        해당 클래스에서 실제 작업을 담당하는 메소드이다.
         */
        protected Void doInBackground(Integer... value) {
            while(mProgressStatus<100){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){

                }
                mProgressStatus++;

                //onProgressUpdate()를 호출하는 함수.
                publishProgress(mProgressStatus);
            }

            return null;
        }
        /*
        doInBackground() 메소드 실행중 언제든지 호출할 수 있는 메소드로
        해당 메소드를 호출할때는 publicProgress()를 사용한다.
         */
        protected void onProgressUpdate(Integer...value){
            mProgress1.setProgress(mProgressStatus);
        }

        protected  void onPostExecute(Integer result){
            mProgress1.setProgress(mProgressStatus);
        }
    }

}