package com.example.gugu.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gugu.R;

import java.util.List;

public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;
    private ListView mapListView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        //final TextView textView = root.findViewById(R.id.text_map);

        /*
        mapViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
*/
        mapListView = root.findViewById(R.id.map_list);

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
}