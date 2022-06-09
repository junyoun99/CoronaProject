package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditId;
    private EditText mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditId = (EditText) findViewById(R.id.Id);
        mEditPassword = (EditText) findViewById(R.id.Password);

        if(savedInstanceState == null){

            SharedPreferences prefs = getSharedPreferences("person_info", 0);

            String id = prefs.getString("id", "");
            String password = prefs.getString("password", "");

            mEditId.setText(id);
            mEditPassword.setText(password);
        }


    }

    public void loginClick(View v){
        SharedPreferences prefs = getSharedPreferences("person_info", 0);
        SharedPreferences.Editor editor = prefs.edit();

        String id = mEditId.getText().toString();
        String password = mEditPassword.getText().toString();
        if(!id.equals("") && !password.equals("")){ //아이디와 비밀번호 입력해야 로그인.
            editor.putString("id", id);
            editor.putString("password", password);
            Toast loginToast = Toast.makeText(this.getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT);
            loginToast.show();
            editor.commit();
            finish();
        }else{  //아이디와 비밀번호 입력 안할 시 로그인이 안되고 페이지가 넘어가지 않음.
            Toast loginToast = Toast.makeText(this.getApplicationContext(),"아이디와 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT);
            loginToast.show();
            editor.commit();
        }
         //로그인 성공 -> 메인액티비티로 이동
    }

    public void logoutClick(View v){
        SharedPreferences prefs = getSharedPreferences("person_info", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.commit();

        Toast loginToast = Toast.makeText(this.getApplicationContext(),"로그아웃 성공",Toast.LENGTH_SHORT);
        loginToast.show();

        finish(); //로그아웃 성공 -> 메인액티비티로 이동
    }
}