package com.example.gugu.ui.like;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gugu.DataClass.User;
import com.example.gugu.DataClass.Write;
import com.example.gugu.R;
import com.example.gugu.ui.map.ListItemAdapter;
import com.example.gugu.ui.read.ReadHelperActivity;
import com.example.gugu.ui.read.ReadMomActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class LikeFragment extends Fragment {

    private ListView likeListView;

    private DatabaseReference rootRef;
    private ListItemAdapter listItemAdapter;

    private int profile = R.drawable.no_profile;
    private String key;
    private String title;
    private int active;
    private int bath;
    private int toilet;
    private int clean;
    private String service;

    private String detail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_like, container, false);

        likeListView = root.findViewById(R.id.like_list);

        //init();

        return root;
    }
    private void init(){

        listItemAdapter = new ListItemAdapter();
        likeListView.setAdapter(listItemAdapter);
        DatabaseReference databaseRef;
        //파이어베이스 접근중


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("board").child("helper");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot board : dataSnapshot.getChildren()) {
                    board.getKey();
                    Write w = board.getValue(Write.class);
                    //파이어베이스에서 읽어온값들을 공유변수에 저장
                    realAddItem(board.getKey(), w.getTitle(), w.getUid(), w.getService(),"helper");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseRef = database.getReference("board").child("mom");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot board : dataSnapshot.getChildren()) {
                    board.getKey();
                    Write w = board.getValue(Write.class);
                    //파이어베이스에서 읽어온값들을 공유변수에 저장
                    realAddItem(board.getKey(), w.getTitle(), w.getUid(), w.getService(),"mom");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //System.out.println("page Created");
        likeListView.setAdapter(listItemAdapter);
    }

    @Override
    public void onResume() {
        System.out.println("page Resume");
        init();
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();
        super.onResume();
    }

    private void realAddItem(
            String keyI,
            String titleI,
            String uid,
            String serviceI,
            String type
    ) {


        this.profile = R.drawable.no_profile;
        this.title = "제목 : " + titleI;
        this.key = keyI;
        this.service = serviceI;

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

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                int iage = Integer.parseInt(sdf.format(date)) / 10000 -
                        Integer.parseInt(u.getUserBirth()) / 10000;
                detail = "이름 : " + u.getUserName() + " / 성별 : " + u.getUserSex() + " / 나이 : " + "만 " + iage + "세";


                SharedPreferences pref = getActivity().getSharedPreferences("like",MODE_PRIVATE);
                Set<String> helper;
                helper = pref.getStringSet("helper",new HashSet<String>());
                Set<String> mom;
                mom = pref.getStringSet("mom",new HashSet<String>());


                if(helper.isEmpty() && mom.isEmpty()){
                    likeListView.setAdapter(listItemAdapter);
                    return;
                }
                for (String h:helper) {
                    if(!h.equals(key)) {
                        return;
                    }
                }

                for (String m:mom) {
                    if(!m.equals(key)) {
                        return;
                    }
                }

                System.out.println(key+"added");
                listItemAdapter.addItem(key, getResources().getDrawable(profile),
                        title, detail, service);
                if(type.equals("helper")) {
                    likeListView.setOnItemClickListener((parent, view, position, id) -> {
                        Intent intent = new Intent(getContext(), ReadHelperActivity.class);
                        intent.putExtra("key", listItemAdapter.getItem(position).getKey());
                        startActivity(intent);
                    });
                }
                else{
                    likeListView.setOnItemClickListener((parent, view, position, id) -> {
                        Intent intent = new Intent(getContext(), ReadMomActivity.class);
                        intent.putExtra("key", listItemAdapter.getItem(position).getKey());
                        startActivity(intent);
                    });
                }
                likeListView.setAdapter(listItemAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}