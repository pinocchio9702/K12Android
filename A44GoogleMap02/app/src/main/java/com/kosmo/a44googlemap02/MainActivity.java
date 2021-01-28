package com.kosmo.a44googlemap02;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "iKosmo";

    SupportMapFragment mapFragment;
    GoogleMap map;
    MarkerOptions myLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //구글맵을 사용하기 위한 객체 생성
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        getSupportFragmentManager().findFragmentById(R.id.map);
        //비동기 방식으로 구글로 맵 요청
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            //콜백메소드를 통해 구글맵 반환
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "GoogleMAP is ready.");
                map = googleMap;
                requestMyLocation();//내위치 요청
            }
        });

        //권한 체크(위치관리자)
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        }
        try{
            MapsInitializer.initialize(this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //내 위치 요청
    private void requestMyLocation(){
        LocationManager manager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try{
            long minTime = 10000;
            float minDistance = 0;
            //GPS정보제공자를 통해 내 위치를 가져온다.
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );
            //GPS를 통해 확인된 마지막 내 위치값을 가져온다.(캐시값)
            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation != null){
                showCurrentLocation(lastLocation);
            }
            //네트워크를 통한 내 위치 확인. Wi-Fi 혹은 무선인터넷
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            showCurrentLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    }
            );

        }catch (SecurityException e){
            e.printStackTrace();
        }
    }
    private  void showCurrentLocation(Location location){
        LatLng curPoint = new LatLng(location.getLatitude(),
                location.getLongitude());
        //에니메이션 효과 있는 카메라
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        //에니메이션 효과 없는 카메라
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        showMyLocationMaker(location);
    }

    //내 위치에 아이콘(마커 혹은 플레그)표시
    private  void showMyLocationMaker(Location location){
        if(myLocationMarker == null){
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(new LatLng(location.getLatitude(),
                    location.getLongitude()));
            myLocationMarker.title("*** 내 위치 ***\n");
            myLocationMarker.snippet("GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myLocationMarker);
        }else{
            myLocationMarker.position(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }
    //수명주기 함수를 통해 추가적인 본인위치 확인(반드시 필요한것은 아님)
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()실행");

        if(map!=null){
            Log.i(TAG, "권한 체크 후 onPause()실행");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()실행");

        if(map != null){
            Log.i(TAG, "권한 체크 후 onResume()실행");
            requestMyLocation();
        }
    }

    //버튼을 누르면 내 위치로 이동함
    public void onBtnClicked(View view){
        requestMyLocation();
    }
}