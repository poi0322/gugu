package com.example.gugu.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gugu.R;
import com.example.gugu.ui.home.ListItemAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ListView noticeListView;
    ArrayList<NoticeListItem> notice_List;
    private ListItemAdapter listItemAdapter;

    private ImageView image;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        noticeListView = root.findViewById(R.id.notice_list);
        listItemAdapter = new ListItemAdapter();

        image = root.findViewById((R.id.noticeimage));
        title = root.findViewById((R.id.noticetitle));

        noticeListView.setAdapter(listItemAdapter);

        return root;
    }
}