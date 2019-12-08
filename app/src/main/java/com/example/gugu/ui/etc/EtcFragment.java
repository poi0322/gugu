package com.example.gugu.ui.etc;

import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class EtcFragment extends Fragment {

    private EtcViewModel etcViewModel;

    private TextView name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        etcViewModel =
                ViewModelProviders.of(this).get(EtcViewModel.class);
        View root = inflater.inflate(R.layout.fragment_etc, container, false);

        name = root.findViewById(R.id.etc_name);
        SharedPreferences pref = this.getActivity().getSharedPreferences("user",MODE_PRIVATE);
        name.setText(pref.getString("userName",""));

        return root;
    }
}