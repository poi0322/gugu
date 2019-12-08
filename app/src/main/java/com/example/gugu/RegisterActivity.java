package com.example.gugu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gugu.DataClass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {
    private EditText regEmail;
    private EditText regPw;
    private EditText regPwChk;
    private EditText regName;
    private EditText regNick;
    private EditText regPhone;
    private EditText regBirth;
    private Button   regBirthPicker;
    private Button   regSubmit;
    private EditText regAddress;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //변수 할당
        regPw = findViewById(R.id.reg_pw);
        regPwChk = findViewById(R.id.reg_pw_chk);
        regName = findViewById(R.id.reg_name);
        regNick = findViewById(R.id.reg_nick);
        regEmail = findViewById(R.id.reg_email);
        regPhone = findViewById(R.id.reg_phone);
        regBirth = findViewById(R.id.reg_birth);
        regAddress = findViewById(R.id.reg_address);
        regBirthPicker = findViewById(R.id.reg_birth_picker);
        regSubmit = findViewById(R.id.reg_submit);
        //필터 설정
        regPhone.setFilters(new InputFilter[] { new InputFilter.LengthFilter(13) });

        rootRef = FirebaseDatabase.getInstance().getReference();

        //리스너 할당
        regBirthPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        regBirth.setText(String.format("%d%02d%02d",year,month,dayOfMonth));
                    }
                }, 1997, 3, 22);
                dialog.show();
            }
        });

        regSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = regEmail.getText().toString();
                String pw = regPw.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            DatabaseReference usersRef = rootRef.child("users");
                            usersRef.child(uid).setValue(new User(
                                                        regAddress.getText().toString(),
                                                        regBirth.getText().toString(),
                                                        regName.getText().toString(),
                                                        regNick.getText().toString(),
                                                        regPhone.getText().toString()
                                    ));

                            Toast.makeText(RegisterActivity.this,"회원가입이 성공",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"회원가입이 실패했습니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
        regPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    public static boolean checkEmail(String email){

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();

    }

}
