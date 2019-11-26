package com.example.gugu.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mapViewModel;
    private ListView mapListView;
    private MapView map;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapListView = root.findViewById(R.id.map_list);

        map = root.findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
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
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(Objects.requireNonNull(this.getActivity()));

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.141233, 126.925594), 14);

        googleMap.animateCamera(cameraUpdate);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(35.141233, 126.925594))
                .title("루프리코리아"));

    }
}