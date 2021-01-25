package com.kosmo.a30xmljsonparser02;

import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    //위젯 처리를 위한 변수
    TextView tv;
    Button btnJson;
    ListView listView;

    int[] profileImg = {
            R.drawable.actor01, R.drawable.actor02, R.drawable.actor03,
            R.drawable.actor04, R.drawable.actor05, R.drawable.actor06,
            R.drawable.actor07, R.drawable.actor08, R.drawable.actor09,
            R.drawable.actor10, R.drawable.actor11, R.drawable.actor12,
    };

    //리스트뷰에 출력할 함목을 위한 변수
    private List<ActorVO> items = new Vector<ActorVO>();

    private ActorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnJson = (Button)findViewById(R.id.btn_json);
        btnJson.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getJsonParser();
            }
        });
    }////onCreate메소드

    //리소스에 저장된 txt파일을 IO스트림을 통해 연결한 후 내용을 읽어옴.
    private String readJsonTxt(){
        //읽어온 JSON데이터를 저장할 변수
        String jsonData = null;
        //res폴더의 json파일을 가져온다.
        InputStream inputStream =
                getResources().openRawResource(R.raw.json);
        //파일의 내용을 읽기 위해 스트립을 생성한다.
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        //Java에서는 IO작업은 항상 예외를 발생시키므로 예외처리는 필수로 해야한다.
        int i;
        try{
            //파일의 내용을 읽어옴
            i = inputStream.read();
            //파일의 끝까지 읽으면 -1을 반환한다.
            while(i != -1){
                //읽어온 내용을 저장.
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            //저장된 내용을 문자열로 변환
            jsonData = byteArrayOutputStream.toString();
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();;
        }

        return jsonData;
    }//readJsonTxt메소드


    private void getJsonParser(){
        String jsonStr = readJsonTxt();
        Log.i("ResourcesRAW", "json.txt내용"+jsonStr);

        try{
            //읽어온 JSON데이터는 객체이므로 JSONObject로 파싱한다.
            JSONObject object = new JSONObject(jsonStr);
            //key값 member는 배열데이터를 가지므로 getJSONArray()로 파싱한다.
            JSONArray array = object.getJSONArray("member");
            //배열의 크기만큼 반복
            for(int i = 0; i<array.length(); i++){
                //배열의 각 요소는 객체
                JSONObject item = array.getJSONObject(i);
                //각 키에 해당하는 값을 가져옴
                String name = item.getString("name");
                String age = item.getString("age");
                //hobby는 배열
                JSONArray hobbysArr = item.getJSONArray("hobbys");
                String hobbys = "";
                for(int j =0; j<hobbysArr.length(); j++){
                    hobbys += hobbysArr.getString(j)+" ";
                }

                String user =
                        item.getJSONObject("login").getString("user");
                String pass =
                        item.getJSONObject("login").getString("pass");
                String loginInfo =
                        String.format("아이디 : %s, 비번 : %s", user, pass);

                String printStr =
                        String.format("이름:%s, 나이 : %s, 취미 : %s, 아이디 : %s, 패스워드 : %s",
                                name, age, hobbys, user, pass);
                Log.i("JsonParsing", "정보>"+printStr);
                /*
                파싱한 정보를 vo객체에 저장한 후 리스트 컬렉션에 추가한다.
                해당 컬렉션에 저장된 값을 어뎁터 객체에서 데이터 사용하게 된다.
                 */
                items.add(new ActorVO(name, age, hobbys, loginInfo, profileImg[i]));

            }///for문
            /*
            커스텀 어뎁터 객체를 생성한다. 인자로 정보를 저장한 리스트컬렉션과
            레이아웃을 전달한다. 어뎁터에서는 레이아웃을 전개하고, getView()에서
            리스트 컬렉션에 저장된 항목을 하나씩 반환하게 된다.
             */
            adapter = new ActorAdapter(this, items, R.layout.actor_layout);
            listView = findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), "선택한 배우 :"+items.get(i).getName(),
                            Toast.LENGTH_LONG).show();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }/////getJsonParser메소드
}