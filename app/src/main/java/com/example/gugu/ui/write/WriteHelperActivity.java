package com.example.gugu.ui.write;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.gugu.DataClass.User;
import com.example.gugu.DataClass.Write;
import com.example.gugu.GPSInfo;
import com.example.gugu.MainActivity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WriteHelperActivity extends Activity implements OnMapReadyCallback {
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

    private EditText body;
    private String service;
    private MapView map;

    private Button helper_submit;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private GPSInfo gps;
    double latitude;
    double longitude;
    private List<Marker> m;

    private ArrayList termList;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_helper);


        title = findViewById(R.id.helper_title);
        name = findViewById(R.id.helper_name);

        location = findViewById(R.id.helper_location);
        term = findViewById(R.id.spinner_term);
        sex = findViewById(R.id.helper_sex);
        age = findViewById(R.id.helper_age);

        active = findViewById(R.id.active_sup);
        bath = findViewById(R.id.bath_sup);
        toilet = findViewById(R.id.toilet_sup);
        clean = findViewById(R.id.clean_sup);

        body = findViewById(R.id.helper_body);
        map = findViewById(R.id.helper_writemap);

        helper_submit = findViewById(R.id.helper_submit);
        termList = new ArrayList<>();
        termList.add("장기");
        termList.add("단기");

        ArrayAdapter<String> termAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, termList);
        term.setAdapter(termAdapter);


        //맵 초기화 구문
        if (!isPermission) {
            callPermission();
        }

        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(this);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        System.out.println("현재날짜 : " + sdf.format(date));


        //쉐어드 프리퍼런스 가져와서 정보넣기
        SharedPreferences pref = this.getSharedPreferences("user", MODE_PRIVATE);
        name.setText(pref.getString("userName", ""));
        sex.setText(pref.getString("userSex", ""));
        location.setText(pref.getString("userAddress", ""));
        sex.setText(pref.getString("userSex", ""));
        int iage = Integer.parseInt(sdf.format(date)) / 10000 -
                Integer.parseInt(pref.getString("userBirth", "")) / 10000;
        age.setText("만 " + iage + "세");


        //리스너 달기
        imageOnClickListener();


        // 글 써서 파이어베이스에 집어넣기
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        submitOnClickListener();
    }

    private void imageOnClickListener() {
        service = Integer.toBinaryString(0000);

        active.setOnClickListener((v) -> {
            if ((Integer.valueOf(service, 2) & 8) == 8) {
                active.setImageResource(R.drawable.ic_active_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) & ~8);
            } else {
                active.setImageResource(R.drawable.ic_active_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) | 8);
            }
        });
        bath.setOnClickListener((v) -> {
            if ((Integer.valueOf(service, 2) & 4) == 4) {
                bath.setImageResource(R.drawable.ic_bath_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) & ~4);
            } else {
                bath.setImageResource(R.drawable.ic_bath_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) | 4);
            }
        });
        toilet.setOnClickListener((v) -> {
            if ((Integer.valueOf(service, 2) & 2) == 2) {
                toilet.setImageResource(R.drawable.ic_toilet_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) & ~2);
            } else {
                toilet.setImageResource(R.drawable.ic_toilet_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) | 2);
            }
        });
        clean.setOnClickListener((v) -> {
            if ((Integer.valueOf(service, 2) & 1) == 1) {
                clean.setImageResource(R.drawable.ic_clean_sup_grey);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) & ~1);
            } else {
                clean.setImageResource(R.drawable.ic_clean_sup_black);
                service = Integer.toBinaryString(Integer.valueOf(service, 2) | 1);
            }
        });

    }

    private void submitOnClickListener() {
        helper_submit.setOnClickListener((v) -> {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");


            SharedPreferences pref = this.getSharedPreferences("user", MODE_PRIVATE);
            name.setText(pref.getString("userName", ""));
            FirebaseUser user = mAuth.getCurrentUser();
            String uid = user.getUid();
            DatabaseReference usersRef = rootRef.child("board");
            usersRef.push().setValue(new Write(
                    "활동보조인", latitude, longitude,
                    term.getSelectedItem().toString(),
                    title.getText().toString(),
                    uid, sdf.format(date), service,
                    body.getText().toString()
            ));

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
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

        m = new ArrayList<>();

        if (!isPermission) {
            callPermission();
            return;
        }

        gps = new GPSInfo(this);

        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }

        googleMap.setMyLocationEnabled(true);
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
            } catch (IOException | NullPointerException ioException) {
                //네트워크 문제
                Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            } catch (IllegalArgumentException illegalArgumentException) {
                Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            }
        });


        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14);

        googleMap.animateCamera(cameraUpdate);

        //googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("루프리코리아"));

    }

    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isAccessFineLocation = true;
        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}
