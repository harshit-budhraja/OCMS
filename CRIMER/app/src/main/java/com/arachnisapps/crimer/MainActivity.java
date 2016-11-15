package com.arachnisapps.crimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        try {
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Logged in with Email: " + email + " and Password: " + password,Toast.LENGTH_SHORT).show();
    }
}
