package com.kosmo.a42geocoder;

import android.location.Address;
import android.location.Geocoder;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO";

    Geocoder geocoder;
    TextView tvResult;
    EditText etLatitude;
    EditText etLongitude;
    EditText etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geocoder = new Geocoder(this);
        tvResult = findViewById(R.id.result);
        etLatitude = findViewById(R.id.lattitude);
        etLongitude = findViewById(R.id.longitude);
        etAddress = findViewById(R.id.address);


    }
    //위경도 가져와서 주소로 변환하기
    public void onBtn1Clicked(View v){
        List<Address> list = null;
        String latitude = etLatitude.getText().toString();
        String longitude = etLongitude.getText().toString();

        try{
            //위경도를 매개변수로 주소 얻어오기
            list = geocoder.getFromLocation(Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    10);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            tvResult.setText("에러 : " + e.getMessage());
            e.printStackTrace();
        }

        if(list != null){
            tvResult.setText(list.get(0).toString());
        }
    }
    
    public void onBtn2Clicked(View v){
        List<Address> list = null;
        String latitude = etLatitude.getText().toString();
        String longitude = etLongitude.getText().toString();

        try{
            list = geocoder.getFromLocation(Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    10);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (IOException e){
            tvResult.setText("에러 : " + e.getMessage());
            e.printStackTrace();
        }
        if(list != null){
            tvResult.setText(list.get(0).toString());
        }
    }

}