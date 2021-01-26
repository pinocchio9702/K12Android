package com.kosmo.a34http01;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    String TAG = "KOSMO123";
    TextView textResult;
    //서버와 통신중을 띄어줄 대화창
    ProgressDialog dialog;
    int buttonResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (TextView)findViewById(R.id.text_result);
        Button btnJson = (Button)findViewById(R.id.btn_json);
        btnJson.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buttonResId = v.getId();
                /*
                회원리스트 가져오기 버튼을 누를경우 아래 객체를 생성하고
                execute()를 호출한다. 이때 파라미터는 접속할URL 하나만 전달한다.
                해당 메소드 호출로 doInBackground()가 호출된다.
                 */
                new AsyncHttpRequest().execute(
                        "http://192.168.0.4:8080/k12springapi/android/memberList.do"
                );
            }
        });
        //서버와 통신시 진행대화창을 띄우기 위한 객체 생성
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//스타일 설정
        dialog.setIcon(android.R.drawable.ic_dialog_alert);//아이콘 설정
        dialog.setTitle("회원정보 리스트 가져오기");//제목
        dialog.setMessage("서버로부터 응답을 기다리고 있습니다.");//출력할 내용
        dialog.setCancelable(false);//back버튼으로 닫히지 않도록 설정

    }//onCreate

    class AsyncHttpRequest extends AsyncTask<String, Void, String>{

        //doInBackground() 실행되기 전에 호출됨
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //서버와 통신 직전에 프로그래스다이어로그를 띄어준다.
            if(!dialog.isShowing())
                dialog.show();//대화창이 없다면 show()를 통해 창을 띄운다.
        }

        //첫번째 AsyncTask의 매개변수를 String...strings(가변인자)로 받음
        /*
        execute() 호출시 전달된 파라미터를 가변인자가 받게된다.
        가변인자는 여러개의 파라미터를 받을 수 있는 매개변수로 배열로
        사용하게 된다.
         */
        @Override
        protected String doInBackground(String... strings) {

            //스프링 서버에서 반환하는 JSON데이터를 저장할 변수수
            StringBuffer sBuffer = new StringBuffer();
            try{
                //0번째 인자를 통해 접속할 URL로 객체를 생성한다.
                URL url = new URL(strings[0]);
                //URL을 연결할 객체 생성
                HttpURLConnection connection =
                        (HttpURLConnection)url.openConnection();
                //서버와 통신할때의 방식(GET or POST)
                connection.setRequestMethod("POST");
                //쓰기모드 지정
                connection.setDoOutput(true);
                /*
                요청 파라미터를 설정한다. 해당 예제에서는 서버와 통신시
                전달할 파라미터가 없으므로 아래 부분에 별다른 설정이 없다.
                 */
                OutputStream out = connection.getOutputStream();
                /*
                out.write(string[1].getBytes());
                파라미터가 2개 이상이라면 &로 문자열을 연결
                out.write("&".getBytes());
                out.write(strings[2].getBytes());
                 */
                out.flush();
                out.close();

                //서버요청이 전달되고 성공이라면 HTTP_OK로 확인가능
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    Log.i(TAG, "HTTP OK 성공");
                    //서버로 부터 받은 응답데이터(JSON)를 스트림을 통해 읽어 저장한다.
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(),
                                    "UTF-8")
                    );
                    String responseData;

                    while((responseData=reader.readLine()) != null){
                        //내용을 한줄씩 읽어서 StringBuffer객체에 저장한다.
                        sBuffer.append(responseData + "\n\r");
                    }
                    reader.close();
                }
                //서버접속에 실패한경우...
                else{
                    Log.i(TAG, "HTTP OK연결 안됨");
                }

                //버튼이 "회원리스트 가져오기"라면 JSON파싱
                if(buttonResId == R.id.btn_json){
                    //읽어온 JSON데이터를 로그로 출력
                    Log.i(TAG, sBuffer.toString());
                    //먼저 JSON배열로 파싱
                    JSONArray jsonArray = new JSONArray(sBuffer.toString());
                    //StringBuffter 객체를 비움
                    sBuffer.setLength(0);
                    //배열의 크기만큼 반복
                    for(int i = 0; i<jsonArray.length(); i++){
                        //배열의 요소는 객체이므로 JSON객체로 파싱
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //각 Key에 해당하는 값을 가져와서 StinrgBuffer객체에 다시 저장
                        sBuffer.append("아이디 : " +
                                jsonObject.getString("id")+"\n\r");
                        sBuffer.append("패스워드 : " +
                                jsonObject.getString("pass")+"\n\r");
                        sBuffer.append("이름 : " +
                                jsonObject.getString("name")+"\n\r");
                        sBuffer.append("가입날짜 : " +
                                jsonObject.getString("regidate")+"\n\r");
                        sBuffer.append("------------------\n\r");

                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            return sBuffer.toString();
        }
        //doInBackground()가 종료되면 해당 함수가 호출된다.
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();;
            textResult.setText(s);
        }
    }
}