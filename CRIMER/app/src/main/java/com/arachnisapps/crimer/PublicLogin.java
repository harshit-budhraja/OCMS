package com.arachnisapps.crimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.Toast;

public class PublicLogin extends AppCompatActivity {

    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_login);
        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Button lgnbtn = (Button) findViewById(R.id.loginButton);
        lgnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                //Toast.makeText(getApplicationContext(),"Logged in with " + email + "and " + password,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PublicLogin.this,MainActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
}
