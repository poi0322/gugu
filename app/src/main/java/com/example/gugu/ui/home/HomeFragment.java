package com.example.gugu.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gugu.R;
import com.example.gugu.ui.map.ListItemAdapter;

public class HomeFragment extends Fragment {
    private ListView homeListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        homeListView = root.findViewById(R.id.map_list);

        listSetting();

        return root;

    }
    private void listSetting() {
        ListItemAdapter listItemAdapter = new ListItemAdapter();

        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));
        listItemAdapter.addItem(getResources().getDrawable(R.drawable.ic_alarm),"ddd","sadf",getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm),getResources().getDrawable(R.drawable.ic_alarm));

        homeListView.setAdapter(listItemAdapter);
    }

}