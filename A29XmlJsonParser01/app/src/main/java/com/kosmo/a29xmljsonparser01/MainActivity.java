package com.kosmo.a29xmljsonparser01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnJson1, btnJson2, btnXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 위젯 가져오기
        btnJson1 = (Button)findViewById(R.id.btn_json1);
        btnJson2 = (Button)findViewById(R.id.btn_json2);
        btnXml= (Button)findViewById(R.id.btn_xml);

        //Json파싱1
        btnJson1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getJsonData1();
            }
        });

        //Json파싱2
        btnJson2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getJsonData2();
            }
        });

        //xml파싱1
        btnXml.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getXmlData();
            }
        });


    }//onCreate()

    //JSON파싱1
    private void getJsonData1(){

        //안드로이드에서 JSON 파싱에는 반드시 예외처리를 해야한다.
        //파싱할 데이터는 전체는 객체, value는 배열로 구선된 Json
        String jsonStr = "{'number' : [1,2,3,4,5]}";
        try{
            //Json객체를 파싱할때 사용하는 클래스
            JSONObject jsonObject = new JSONObject(jsonStr);
            /*
            json 배열을 얻어올 것이므로 getJSONArray()를 사용한다. key값은 number로
            사용함.
             */
            JSONArray jsonArray = jsonObject.getJSONArray("number");
            //배열의 크기만큼 반복하여 로그를 출력
            for(int i = 0; i < jsonArray.length(); i++){
                int tempNum = jsonArray.getInt(i);
                Log.i("KOSMO", "JSON1 파싱 데이터"+ tempNum);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }//getJsonData1


    private void getJsonData2(){
        /*
        전체는 객체로 구성되었고, value도 객체로 구선된 JSON데이터터
        */
        String jsonStr = "{'color' : {'top' : 'red', 'right' : 'blue', 'bottom' : 'green', 'left' : 'black'}}";

        try{
            //JSON전체를 파싱
            JSONObject jsonObject = new JSONObject(jsonStr);
            //color키값에 해당하는 데이터를 두번째로 파싱
            JSONObject color = jsonObject.getJSONObject("color");

            //getString(키값) : 키에 해당하는 벨류를 가져옴.
            String top = color.getString("top");
            String right = color.getString("right");
            String bottom = color.getString("bottom");
            String left = color.getString("left");

            String jsonPrint =
                    String.format("top:%s, right:%s, bottom:%s, left:%s", top, right, bottom, left);

            Log.i("KOSMO", "JSON2 파싱 데이터 : " + jsonPrint);

            /*
            has(키값) : Json객체 안에 해당 키값이 있는지 확인

             */
            if(color.has("left")){
                Log.i("has메소드1", "key:left있음");
            }
            else{
                Log.i("has메소드1", "key:left없음");
            }
            if(color.has("css")){
                Log.i("has메소드2", "key:css있음");
            }
            else{
                Log.i("has메소드2", "key:css없음");
            }



        }catch (Exception e){

        }
    }
    /*
    XmlPullParser 클래스를 이용한 xml파싱
    형식
        next() : XML탐색을 위한 메소드로 XML요소를 식별하면 탐색을
            중지하고 식별된 요소를 리턴한다.
    상수
        START_DOCUMENT : XML문서의 시작으로 파싱의 시작을 알림
        START_TAG : XML지삭태그를 의미함
        TEXT : XML의 시작태그와 종료태그 사이의 텍스트를 의미함
            실제 파싱할 데이터를 가르킴
        END_TAG : 종료태그를 의미함.
        END_DOCUMENT : XML문서의 끝을 알림
        태그는 xxx.getName으로 파싱하고
     */

    private void getXmlData(){
        try{
            //파싱한 값을저장하기 위한 ArrayList컬렉션 생성
            ArrayList<String> xNumber = new ArrayList<String>();
            ArrayList<String> xActor = new ArrayList<String>();
            ArrayList<String> xWord = new ArrayList<String>();

            //XML처리에 사용할 변수 선언
            int event = 0;
            String currentTag = null;

            /*
            Arrays.asList()
                : 인자로 주어진 배열을 List컬랙션으로 변환해 주는 메소드
                단, 이렇게 변화된 컬렉션에는 원소를 새롭게 추가할 수는 없다.
                하지만 컬렉션에서 제공하는 모든 메소드를 사용할 수 있는 장점이 있다.
             */
            List<String> tagList =
                    Arrays.asList(new
                            String[]{"number", "actor", "word"});

            //xml파서 선언 : 리소드 폴더에 word.xml을 가져온다.
            XmlPullParser parser = getResources().getXml(R.xml.word);

            //XML문서의 노드를 하나씩 읽으면서 문서의 끝까지 반복한다.
            while((event=parser.next())!=XmlPullParser.END_DOCUMENT){
                switch (event){
                    //탐색된 요소가 시작태그이면...
                    case XmlPullParser.START_TAG:
                        //시작태그를 변수에 저장한다.
                        currentTag = parser.getName();
                        break;

                    //해당 요소가 텍스트(데이터)면
                    case XmlPullParser.TEXT:
                        if(currentTag!=null && tagList.contains(currentTag)){
                            String value = parser.getText();
                            Log.i("XML>TEXT", "value="+value);
                            //각 태그명에 해당하는 값을 컬렉션에 저장한다.
                            if(currentTag.equals("number")){
                                xNumber.add(value);
                            }
                            else if(currentTag.equals("actor")){//배우이름
                                xActor.add(value);
                            }
                            else if(currentTag.equals("word")){//명대사
                                xWord.add(value);
                            }
                        }
                        break;
                    //해당 요소가 종료태그라면...
                    case XmlPullParser.END_TAG:
                        currentTag = null;
                        break;
                    default:
                        break;
                }//switch문

            }//while문
            //컬렉션에 저장된 모든 내용을 로그로 출력한다.
            for(int i =0; i<xNumber.size(); i++){
                Log.i("XML>Data", "number="+xNumber.get(i));
                Log.i("XML>Data", "actor="+xActor.get(i));
                Log.i("XML>Data", "word="+xWord.get(i));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }//getXmlData


}