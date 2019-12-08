package com.example.gugu.ui.read;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.net.Uri;
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
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class ReadHelperActivity extends Activity implements OnMapReadyCallback {

    private String key;

    private TextView title;
    private TextView name;

    private TextView location;
    private TextView term;
    private TextView sex;
    private TextView age;
    private ToggleButton like;

    private ImageView active;
    private ImageView bath;
    private ImageView toilet;
    private ImageView clean;

    private TextView body;
    private String service;
    private MapView map;

    private Button helper_call;
    private String phone;

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
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_helper);

        Intent intent =getIntent();
        key= intent.getStringExtra("key");


        title = findViewById(R.id.helper_title);
        name = findViewById(R.id.helper_name);

        location = findViewById(R.id.helper_location);
        term = findViewById(R.id.helper_term);
        sex = findViewById(R.id.helper_sex);
        age = findViewById(R.id.helper_age);
        like = findViewById(R.id.helper_like);

        SharedPreferences lpref = getSharedPreferences("like",MODE_PRIVATE);
        Set<String> llist;
        llist = lpref.getStringSet("helper", new HashSet<String>());
        for (String s : llist) {
            if (s.equals(key)) {
                like.setChecked(true);
                like.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_heart));
            }
        }



        like.setOnClickListener(v -> {
            if(like.isChecked()){
                like.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_heart));

                SharedPreferences pref = getSharedPreferences("like",MODE_PRIVATE);
                Set<String> list;
                list = pref.getStringSet("helper", new HashSet<String>());
                list.add(key);
                System.out.println(list);
                SharedPreferences.Editor editor = pref.edit();
                editor.putStringSet("helper",list);
                editor.commit();

            }
            else {
                like.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_heart_blank));

                SharedPreferences pref = getSharedPreferences("like",MODE_PRIVATE);
                Set<String> list;
                list = pref.getStringSet("helper", new HashSet<String>());
                list.remove(key);

                SharedPreferences.Editor editor = pref.edit();
                editor.putStringSet("helper",list);
                editor.commit();
            }
        });
        active = findViewById(R.id.active_sup);
        bath = findViewById(R.id.bath_sup);
        toilet = findViewById(R.id.toilet_sup);
        clean = findViewById(R.id.clean_sup);

        body = findViewById(R.id.helper_body);
        map = findViewById(R.id.helper_map);

        helper_call = findViewById(R.id.helper_call);


        //맵 초기화 구문
        if (!isPermission) {
            callPermission();
        }



        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        //리스너 달기


        rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference boardRef = rootRef.child("board").child("helper").child(key);
        boardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Write w = dataSnapshot.getValue(Write.class);


                DatabaseReference usersRef = rootRef.child("users").child(w.getUid());
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User u =dataSnapshot.getValue(User.class);
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        int iage = Integer.parseInt(sdf.format(date)) / 10000 - Integer.parseInt(u.getUserBirth()) / 10000;
                        name.setText(u.getUserName());
                        age.setText("만 " + iage + "세");
                        sex.setText(u.getUserSex());
                        phone = u.getUserPhone();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                service = w.getService();
                if ((Integer.valueOf(service, 2) & 8) == 8) {
                    active.setImageResource(R.drawable.ic_active_sup_black);
                } else {
                    active.setImageResource(R.drawable.ic_active_sup_grey);
                }
                if ((Integer.valueOf(service, 2) & 4) == 4) {
                    bath.setImageResource(R.drawable.ic_bath_sup_black);
                } else {
                    bath.setImageResource(R.drawable.ic_bath_sup_grey);
                }
                if ((Integer.valueOf(service, 2) & 2) == 2) {
                    toilet.setImageResource(R.drawable.ic_toilet_sup_black);
                } else {
                    toilet.setImageResource(R.drawable.ic_toilet_sup_grey);
                }
                if ((Integer.valueOf(service, 2) & 1) == 1) {
                    clean.setImageResource(R.drawable.ic_clean_sup_black);
                } else {
                    clean.setImageResource(R.drawable.ic_clean_sup_grey);
                }

                title.setText(w.getTitle());
                term.setText(w.getTerm());
                body.setText(w.getBody());
                latitude = w.getLatitude();
                longitude = w.getLongitude();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(this);


        helper_call.setOnClickListener(v -> {
            startActivity( new Intent("android.intent.action.DIAL", Uri.parse("tel:"+phone)));

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
        googleMap.setMyLocationEnabled(true);


            //이건 현재 좌표
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            String address = "";
            try {
                address = geocoder.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(0);
                location.setText(address);
                m.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(address)));
            } catch (IOException | NullPointerException | IndexOutOfBoundsException ioException ) {
                //네트워크 문제
                Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            } catch (IllegalArgumentException illegalArgumentException) {
                Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            }


        // Updates the location and zoom of the MapView
        System.out.println(latitude+", "+longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14);

        googleMap.moveCamera(cameraUpdate);


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
