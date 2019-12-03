package com.example.gugu.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.gugu.GPSInfo;
import com.example.gugu.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapListView = root.findViewById(R.id.map_list);

        //변수 초기화
        if(!isPermission){
            callPermission();
        }


        map = root.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(this);

        listSetting();


        return root;
    }



    private void listSetting(){
        ListItemAdapter listItemAdapter = new ListItemAdapter();

        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
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


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(this.getActivity()));

        if(!isPermission){
            callPermission();
            return;
        }

        gps= new GPSInfo(getContext());

        if(gps.isGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }
        else {
            gps.showSettingsAlert();
        }

        googleMap.setMyLocationEnabled(true);


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
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}

