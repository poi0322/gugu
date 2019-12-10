package com.example.gugu.ui.guide;

import android.content.Intent;
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
import com.example.gugu.ui.guide.ListItemAdapter;


public class GuideFragment extends Fragment {
    private ListItemAdapter adapter;

    private ListView GuideListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View root = inflater.inflate(R.layout.fragment_guide, container, false);
        GuideListView = root.findViewById(R.id.guide_list);
        adapter = new ListItemAdapter();


        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.guide01),"[뇌졸증 - 취침 자세]");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.guide02),"[척추손상 - 이동법]");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.guide03),"[뇌성마비 - 근육긴장도조절방법]");


        GuideListView.setAdapter(adapter);


        GuideListView.setOnItemClickListener((parent, view, position, id) -> {
            {
                Intent intent = new Intent(getContext(), guide_test.class);
                startActivity(intent);
            }
        });

        return root;
    }

}