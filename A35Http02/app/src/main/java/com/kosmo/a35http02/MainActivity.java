package com.kosmo.a35http02;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String TAG = "iKOSMO";

    //전역변수
    EditText user_id, user_pw;
    TextView textResult;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위젯얻오이기
        textResult = (TextView)findViewById(R.id.text_result);
        user_id = (EditText)findViewById(R.id.user_id); //아이디 입력
        user_pw = (EditText)findViewById(R.id.user_pw); //패스워드 입력
        Button btnLogin = (Button)findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                /*
                execute()를 통해 doInBackground()를 호출한다.
                이때 전달되는 파라미터는 총 3개이다.
                첫번째는 요청URL, 두번째와 세번째는 서버로 전송할 파라미터이다.
                각 입력상자에 입력된 내용을 가져와서 전달한다.
                 */
                new AsyncHttpServer().execute(
                        "http://192.168.0.4:8080/k12springapi/android/memberLogin.do",
                        "id="+user_id.getText().toString(),
                        "pass="+user_pw.getText().toString()
                );
            }
        });

        //진행대화상자 준비...
        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);//Back버튼을 누룰때 창이 닫히게 설정
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIcon(android.R.drawable.ic_dialog_email);
        dialog.setTitle("로그인 처리중");
        dialog.setMessage("서버로부터 응답을 기다리고 있습니다.");

    }

    class AsyncHttpServer extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //응답대기 대화창
            if(!dialog.isShowing()) dialog.show();
        }

        /*
        execute()호출시 전달된 3개의 파라미터를 가변인자로 받아서 사용함.
         */

        @Override
        protected String doInBackground(String... strings) {

            StringBuffer receivedData = new StringBuffer();

            try{
                URL url = new URL(strings[0]);//파라미터1 : 요청 URL
                HttpURLConnection conn =
                        (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStream out = conn.getOutputStream();
                //getBytes()?????

                //OutputStream형으로 string을 쓸려면 getBytes()가 필요한듯
                out.write(strings[1].getBytes());//파라미터2 : 사용자아이디
                out.write("&".getBytes());// &를 사용하여 쿼리스트링 형태로 만들어준다.
                out.write(strings[2].getBytes());//파라미터3 : 사용자 패스워드
                out.flush();
                out.close();

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    //스프링서버에 연결성공한 경우 JSON데이터를 읽어서 저장한다.
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "UTF-8")
                    );
                    String data;
                    while((data=reader.readLine())!=null){
                        receivedData.append(data+"\r\n");
                    }
                    reader.close();
                }
                else{
                    Log.i(TAG,"HTTP_OK안됨. 연결 실패");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            //로그출력
            Log.i(TAG, receivedData.toString());

            //서버에서 내려준 JSON정보를 저장후 반환
            return receivedData.toString();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //doInBackground()에서 반환한 값은 해당 메소드로 전달한다.
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            StringBuffer sb = new StringBuffer();
            try{

                /*
                {
                    "isLogin":1,
                    "memberInfo":
                        {"id":"kosmo","pass":"1111","name":"코스모테스트","regidate":"2020-11-27"}
                 }
                 */
                //JSON객체를 파싱
                JSONObject jsonObject = new JSONObject(s);
                int success =
                        Integer.parseInt(jsonObject.getString("isLogin"));
                if(success==1){
                    sb.append("로그인 성공\n");
                    //객체안에 또 하나의 객체가 있으므로 getJSONObject()로 파싱한다.
                    String id = jsonObject.getJSONObject("memberInfo")
                                                .getString("id").toString();
                    String pass = jsonObject.getJSONObject("memberInfo")
                                                .getString("pass").toString();
                    String name = jsonObject.getJSONObject("memberInfo")
                                                .getString("name").toString();

                    sb.append("회원정보\n");
                    sb.append("아이디 : "+id+"\n");
                    sb.append("패스워드 : " + pass + "\n");
                    sb.append("이름 : " + name + "\n");
                }else{
                    sb.append("로그인 실패 ㅜㅜ");
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            //결과 출력
            dialog.dismiss();
            textResult.setText(sb.toString());
            Toast.makeText(getApplicationContext(),
                    sb.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
}