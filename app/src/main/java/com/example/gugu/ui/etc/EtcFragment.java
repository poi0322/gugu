package com.example.gugu.ui.etc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gugu.R;

public class EtcFragment extends Fragment {

    private EtcViewModel etcViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        etcViewModel =
                ViewModelProviders.of(this).get(EtcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_etc, container, false);
        final TextView textView = root.findViewById(R.id.text_etc);
        etcViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}