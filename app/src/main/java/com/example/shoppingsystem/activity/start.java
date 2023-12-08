package com.example.shoppingsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shoppingsystem.R;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button login=(Button)findViewById(R.id.loginonchoise);
        Button signup=(Button)findViewById(R.id.signupinchoise);
        Button loginadmin=(Button)findViewById(R.id.loginasadmin);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(this, Login.class);
//                startActivity(intent);
//            }
//        });
//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(this,SignUp.class);
//                startActivity(intent);
//            }
//        });
//        loginadmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(this,AdminLogin.class);
//                startActivity(intent);
//            }
//        });

    }
}