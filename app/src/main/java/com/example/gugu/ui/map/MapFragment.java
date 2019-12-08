package com.example.gugu.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.gugu.DataClass.User;
import com.example.gugu.DataClass.Write;
import com.example.gugu.GPSInfo;
import com.example.gugu.MainActivity;
import com.example.gugu.R;
import com.example.gugu.ui.read.ReadHelperActivity;
import com.example.gugu.ui.read.ReadMomActivity;
import com.example.gugu.ui.write.WriteHelperActivity;
import com.example.gugu.ui.write.WriteMomActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.location.LocationManager.GPS_PROVIDER;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    /*맵뷰 변수*/
    private ListView mapListView;
    private MapView map;
    /*GPS 변수*/
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private GPSInfo gps;
    double latitude;
    double longitude;

    private List<Marker> m;
    private Circle circle;


    //뷰들의 변수
    private ImageView write;
    private ToggleButton helper;
    private ToggleButton mom;
    private Spinner term;
    private ArrayList termList;
    private Spinner age;
    private ArrayList ageList;
    private Spinner sex;
    private ArrayList sexList;

    //파베
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;

    private ListItemAdapter listItemAdapter;

    private String detail;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapListView = root.findViewById(R.id.map_list);
        listItemAdapter = new ListItemAdapter();

        //변수 초기화
        write = root.findViewById(R.id.map_write);
        helper = root.findViewById(R.id.map_helper);
        mom = root.findViewById(R.id.map_mom);


        termList = new ArrayList<>();
        termList.add("모두");
        termList.add("장기");
        termList.add("단기");
        ageList = new ArrayList<>();
        ageList.add("모두");
        ageList.add("20대");
        ageList.add("30대");
        ageList.add("40대");
        ageList.add("50대");
        ageList.add("60대");
        sexList = new ArrayList<>();
        sexList.add("모두");
        sexList.add("남");
        sexList.add("여");
        term = root.findViewById(R.id.map_term);
        age = root.findViewById(R.id.map_age);
        sex = root.findViewById(R.id.map_sex);

        ArrayAdapter<String> termAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, termList);
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ageList);
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, sexList);
        term.setAdapter(termAdapter);
        age.setAdapter(ageAdapter );
        sex.setAdapter(sexAdapter );

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();

        //맵 퍼미션
        if (!isPermission) {
            callPermission();
        }

        //맵 초기화
        map = root.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(this);

        //리스너 할당
        write.setOnClickListener(v -> {
            if (helper.isChecked()) {
                Intent intent = new Intent(getActivity(), WriteHelperActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), WriteMomActivity.class);
                startActivity(intent);
            }
        });
        helper.setOnClickListener(v -> {
            if (helper.isChecked()) {
                helper.setChecked(true);
                mom.setChecked(false);
                map.onCreate(savedInstanceState);
                map.onResume();
                map.getMapAsync(this);
                for (Marker marker : m) {
                    marker.remove();
                }
                if (circle != null) {
                    circle.remove();
                }
                listItemAdapter = new ListItemAdapter();
            } else {
                helper.setChecked(true);
                mom.setChecked(false);
            }
        });
        mom.setOnClickListener(v -> {
            if (mom.isChecked()) {
                mom.setChecked(true);
                helper.setChecked(false);
                map.onCreate(savedInstanceState);
                map.onResume();
                map.getMapAsync(this);
                for (Marker marker : m) {
                    marker.remove();
                }
                if (circle != null) {
                    circle.remove();
                }
                listItemAdapter = new ListItemAdapter();
            } else {
                mom.setChecked(true);
                helper.setChecked(false);
            }
        });




        listSetting();


        return root;
    }


    private void listSetting() {

        mapListView.setOnItemClickListener((parent, view, position, id) -> {
            if(helper.isChecked()) {
                Intent intent = new Intent(getContext(), ReadHelperActivity.class);
                intent.putExtra("key",listItemAdapter.getItem(position).getKey());
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(getContext(), ReadMomActivity.class);
                intent.putExtra("key",listItemAdapter.getItem(position).getKey());
                startActivity(intent);
            }
        });
        mapListView.setAdapter(listItemAdapter);
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

    int profile = R.drawable.no_profile;
    String key;
    String title;
    int active;
    int bath;
    int toilet;
    int clean;
    String service;


    private void realAddItem(
            String key,
            String title,
            String uid,
            String service
    ) {


        this.profile = R.drawable.no_profile;
        this.title = "제목 : " + title;
        this.key = key;
        this.service = service;

        if ((Integer.valueOf(service, 2) & 8) == 8) {
            active = R.drawable.ic_active_sup_black;
        } else {
            active = R.drawable.ic_active_sup_grey;
        }
        if ((Integer.valueOf(service, 2) & 4) == 4) {
            bath = R.drawable.ic_bath_sup_black;
        } else {
            bath = R.drawable.ic_bath_sup_grey;
        }
        if ((Integer.valueOf(service, 2) & 2) == 2) {
            toilet = R.drawable.ic_toilet_sup_black;
        } else {
            toilet = R.drawable.ic_toilet_sup_grey;
        }
        if ((Integer.valueOf(service, 2) & 1) == 1) {
            clean = R.drawable.ic_clean_sup_black;
        } else {
            clean = R.drawable.ic_clean_sup_grey;
        }

        rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("users").child(uid);


        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                int iage = Integer.parseInt(sdf.format(date)) / 10000 -
                        Integer.parseInt(u.getUserBirth()) / 10000;
                detail = "이름 : " + u.getUserName() + " / 성별 : " + u.getUserSex() + " / 나이 : " + "만 " + iage + "세";
                listItemAdapter.addItem(key,getResources().getDrawable(profile),
                        title, detail,service);
                listSetting();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(this.getActivity()));

        m = new ArrayList<>();

        if (!isPermission) {
            callPermission();
            return;
        }

        gps = new GPSInfo(getContext());

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
            if (circle != null) {
                circle.remove();
            }
            listItemAdapter = new ListItemAdapter();
        });
        googleMap.setOnCameraIdleListener(() -> {
            listSetting();
            LatLng latLng = googleMap.getCameraPosition().target;
            latitude = latLng.latitude;
            longitude = latLng.longitude;

            Location lo1 = new Location("lo1");
            lo1.setLatitude(latitude);
            lo1.setLongitude(longitude);

            DatabaseReference databaseRef;
            //파이어베이스 접근중
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            if(helper.isChecked()) {
                databaseRef = database.getReference("board").child("helper");
            }
            else{
                databaseRef = database.getReference("board").child("mom");
            }

            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot board : dataSnapshot.getChildren()) {

                        //파이어베이스에서 읽어온값들을 공유변수에 저장
                        board.getKey();
                        Write w = board.getValue(Write.class);
                        Location lo2 = new Location("lo2");
                        lo2.setLatitude(w.getLatitude());
                        lo2.setLongitude(w.getLongitude());
                        if (lo1.distanceTo(lo2) / 1000 < 1) {
                            realAddItem(board.getKey(),w.getTitle(), w.getUid(), w.getService());
                            m.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(w.getLatitude(), w.getLongitude())).title(w.getTitle())));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            //이건 현재 좌표
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            String address;
            try {
                address = geocoder.getFromLocation(latitude, longitude, 1).get(0).getAddressLine(0);

                // 반경 1KM원
                circle = googleMap.addCircle(new CircleOptions().center(new LatLng(latitude, longitude)) //원점
                        .radius(1000)      //반지름 단위 : m
                        .strokeWidth(0f)  //선너비 0f : 선없음
                        .fillColor(Color.parseColor("#7ae1f5fe"))); //배경색);


                //m.add(googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(address)));
            } catch (IOException | IndexOutOfBoundsException ioException) {
                //네트워크 문제
                Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            } catch (IllegalArgumentException illegalArgumentException) {
                Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            }


        });

        // Updates the location and zoom of the MapView
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
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}

