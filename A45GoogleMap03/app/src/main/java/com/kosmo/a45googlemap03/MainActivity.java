package com.kosmo.a45googlemap03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO";
    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "GoogleMap is ready..");
                map = googleMap;

                //대표위치(서울시청)
                LatLng SEOUL = new LatLng(37.56, 126.97);
                //가산디지털단지열
                LatLng GASAN = new LatLng(37.480379, 126.882744);

                //지역명
                String[] titles = {"지역1", "지역2", "지역3", "지역4", "지역5"};

                //위경도
                ArrayList<LatLng> loc = new ArrayList<LatLng>();
                loc.add(new LatLng(37.480280, 127.882356));
                loc.add(new LatLng(37.660751, 127.073440));
                loc.add(new LatLng(37.660037, 127.079740));
                loc.add(new LatLng(37.656325, 127.063341));
                loc.add(new LatLng(37.655356, 127.062929));

                for (int idx = 0; idx < titles.length; idx++) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(loc.get(idx));
                    markerOptions.title(titles[idx]);
                    markerOptions.snippet("여기는" + titles[idx]);

                    map.addMarker(markerOptions);
                }
                //카메라이동 및 최조레벨 지정
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(GASAN, 16));
                //지도유형
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                //마커클리에 대한 리스너
                map.setOnMarkerClickListener(new
                         GoogleMap.OnMarkerClickListener() {
                             @Override
                             public boolean onMarkerClick(Marker marker) {
                                 Toast.makeText(getApplicationContext(),
                                         "Marker클릭 : " + marker.getTitle() + "\n" + marker.getPosition(),
                                         Toast.LENGTH_SHORT).show();

                                 //true로 하면 InfoWindow가 안나온다.
                                 return false;
                             }
                         });

                //정보창 클릭에 대한 리스너
                map.setOnInfoWindowClickListener(new
                     GoogleMap.OnInfoWindowClickListener() {
                         @Override
                         public void onInfoWindowClick(Marker marker) {
                             Toast.makeText(getApplicationContext(),
                                     "InfoWind클릭 : " + marker.getTitle() + "\n" + marker.getPosition(),
                                     Toast.LENGTH_SHORT).show();
                         }
                     });


            }
        });
        try{
            MapsInitializer.initialize(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}