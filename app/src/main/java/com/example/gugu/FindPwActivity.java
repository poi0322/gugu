package com.example.gugu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class FindPwActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_find);
    }
//비밀번호 찾는코드

    public void nextpw(View view){
        Intent intent = new Intent(getApplicationContext(),ViewPwActivity.class);
        startActivity(intent);
    }
}
