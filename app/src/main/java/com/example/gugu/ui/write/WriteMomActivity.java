package com.example.gugu.ui.write;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.gugu.GPSInfo;
import com.example.gugu.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WriteMomActivity extends Activity implements OnMapReadyCallback {
    private EditText title;
    private TextView name;

    private TextView location;
    private Spinner term;
    private TextView sex;
    private TextView age;

    private ImageView active;
    private ImageView bath;
    private ImageView toilet;
    private ImageView clean;

    private String service;
    private EditText body;
    private MapView map;

    private Button mom_submit;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private GPSInfo gps;
    double latitude;
    double longitude;
    private List<Marker> m;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_mom);


        title = findViewById(R.id.mom_title);
        name = findViewById(R.id.mom_name);

        location = findViewById(R.id.mom_location);
        term = findViewById(R.id.spinner_term);
        sex = findViewById(R.id.mom_sex);
        age = findViewById(R.id.mom_age);

        active = findViewById(R.id.active_sup);
        bath = findViewById(R.id.bath_sup);
        toilet = findViewById(R.id.toilet_sup);
        clean = findViewById(R.id.clean_sup);

        body = findViewById(R.id.mom_body);
        map = findViewById(R.id.mom_writemap);

        mom_submit = findViewById(R.id.mom_submit);

        //맵 초기화 구문
        if(!isPermission){
            callPermission();
        }

        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(this);


        //리스너 달기
        //TODO : 맵 중앙좌표 가져오기
        //TODO : 글 써서 파이어베이스에 집어넣기

        imageOnClickListener();
        submitOnClickListener();
    }

    private void imageOnClickListener() {
        service = Integer.toBinaryString(0000);

        active.setOnClickListener((v) -> {
            Toast.makeText(this,"sadf",Toast.LENGTH_LONG);
            if((Integer.valueOf(service,2) & 8) == 8){
                active.setImageResource(R.drawable.ic_active_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service,2) & ~8);
            }
            else{
                active.setImageResource(R.drawable.ic_active_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service,2) | 8);
            }
        });
        bath.setOnClickListener((v) -> {
            if((Integer.valueOf(service,2) & 4) == 4){
                bath.setImageResource(R.drawable.ic_bath_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service,2) & ~4);
            }
            else{
                bath.setImageResource(R.drawable.ic_bath_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service,2) | 4);
            }
        });
        toilet.setOnClickListener((v) -> {
            if((Integer.valueOf(service,2) & 2) == 2){
                toilet.setImageResource(R.drawable.ic_toilet_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service,2) & ~2);
            }
            else{
                toilet.setImageResource(R.drawable.ic_toilet_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service,2) | 2);
            }
        });
        clean.setOnClickListener((v) -> {
            if((Integer.valueOf(service,2) & 1) == 1){
                clean.setImageResource(R.drawable.ic_clean_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service,2) & ~1);
            }
            else{
                clean.setImageResource(R.drawable.ic_clean_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service,2) | 1);
            }
        });

    }

    private void submitOnClickListener(){
        map.setOnClickListener((v -> {


        }));
    }

    @Override
    public void onResume() {
        map.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(this.getApplication()));

        if(!isPermission){
            callPermission();
            return;
        }

        gps= new GPSInfo(this);

        if(gps.isGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }
        else {
            gps.showSettingsAlert();
        }

        googleMap.setMyLocationEnabled(true);


        // 이거 지금 위치 표시하는거
        googleMap.setOnCameraMoveListener(() -> {
            for (Marker marker : m) {
                marker.remove();
            }

        });
        googleMap.setOnCameraIdleListener(() -> {
            LatLng latLng = googleMap.getCameraPosition().target;
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            //이건 현재 좌표
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            String address;
            try {
                address = geocoder.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(0);
                m.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(address)));
            } catch (IOException ioException) {
                //네트워크 문제
                Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            } catch (IllegalArgumentException illegalArgumentException) {
                Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            }

        });
        //여기까지 지금위치 표시



        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14);

        googleMap.animateCamera(cameraUpdate);

        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("루프리코리아"));

    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}
