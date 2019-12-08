package com.example.gugu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gugu.DataClass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {

    private EditText email;
    private EditText pw;
    private Button submit;
    private Button register;
    private Button findId;
    private Button findPw;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 변수 할당
        register=findViewById(R.id.login_register);
        findId=findViewById(R.id.login_find_id);
        findPw=findViewById(R.id.login_find_pw);
        email=findViewById(R.id.login_email);
        pw=findViewById(R.id.login_pw);
        submit=findViewById(R.id.login_submit);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
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

                //TODO : 로그인 구현
                mAuth.signInWithEmailAndPassword( email.getText().toString(), pw.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uid = user.getUid();
                                    //파이어베이스 접근중
                                    DatabaseReference usersRef = rootRef.child("users").child(uid);
                                    usersRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            SharedPreferences pref = getSharedPreferences("user",MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();

                                            //파이어베이스에서 읽어온값들을 공유변수에 저장
                                            User u = dataSnapshot.getValue(User.class);
                                            editor.putString("userAddress",u.getUserAddress());
                                            editor.putString("userBirth",u.getUserBirth());
                                            editor.putString("userName",u.getUserName());
                                            editor.putString("userNick",u.getUserNick());
                                            editor.putString("userPhone",u.getUserPhone());
                                            editor.putString("userSex",u.getUserSex());
                                            editor.commit();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
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
    public void findid(View view){
        Intent intent = new Intent(getApplicationContext(),FindIdActivity.class);
        startActivity(intent);
    }
    public void findpw(View view){
        Intent intent = new Intent(getApplicationContext(),FindPwActivity.class);
        startActivity(intent);
    }

}
