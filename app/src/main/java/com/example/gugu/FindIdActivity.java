package com.example.gugu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
public class FindIdActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_find);
    }

    //아이디 찾는코드
    public void nextid(View view){
        Intent intent = new Intent(getApplicationContext(),ViewIdActivity.class);
        startActivity(intent);
    }

}
