package com.example.gugu.ui.home;


import android.content.Intent;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.gugu.R;
import com.example.gugu.ui.home.ListItemAdapter;
import com.example.gugu.ui.read.ReadHelperActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ListItemAdapter adapter;

    private ListView HomeListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_home, container, false);
        HomeListView = root.findViewById(R.id.notice_list);
        adapter = new ListItemAdapter();


        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.notice1),"[어플 사용 안내]");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.notice2),"[신청 시 주의사항]");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.notice3),"[센터에서의 글 작성금지]");


        HomeListView.setAdapter(adapter);


        HomeListView.setOnItemClickListener((parent, view, position, id) -> {
            {
                Intent intent = new Intent(getContext(), notice_test.class);
                startActivity(intent);
            }
        });

        return root;
    }

}
