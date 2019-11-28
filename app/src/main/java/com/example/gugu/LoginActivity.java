package com.example.gugu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {

    private EditText id;
    private EditText pw;
    private Button submit;
    private TextView register;
    private TextView findId;
    private TextView findPw;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 변수 할당
        register=findViewById(R.id.login_register);
        findId=findViewById(R.id.login_find_id);
        findPw=findViewById(R.id.login_find_pw);
        id=findViewById(R.id.login_id);
        pw=findViewById(R.id.login_pw);
        submit=findViewById(R.id.login_submit);

        mAuth = FirebaseAuth.getInstance();

        loginFunction();
        addHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    //로그인 기능
    private void loginFunction(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.getText();
                pw.getText();

                //TODO : 로그인 구현
                //디비 확인 쏘기
            }
        });
    }
    //핸들러 추가
    private void addHandler(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}
