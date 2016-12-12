package com.arachnisapps.crimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class ExecutiveloginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
        @Override
        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            try {
                sharedPreferences = getSharedPreferences("mypref", 0);
                editor = sharedPreferences.edit();
                setContentView(R.layout.activity_executivelogin);
                Button btn = (Button) findViewById(R.id.QRbutton);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                        //intent.setType("text/plain");

                        PackageManager packageManager = getApplicationContext().getPackageManager();
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivityForResult(intent, 0);
                        } else {
                            Log.d("APP", "No Intent available to handle action");
                            Toast.makeText(getApplicationContext(), (CharSequence) "No Application Found", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            } catch (Exception e) {

                e.printStackTrace();
            }
        }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // contents contains whatever was encoded
                String contents = data.getStringExtra("SCAN_RESULT");
                // Format contains the type of code i.e. UPC, EAN, QRCode etc...
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                //Toast.makeText(getApplicationContext(),contents,Toast.LENGTH_LONG).show();
                EditText useredittext = (EditText) findViewById(R.id.username);
                EditText passwordedittext = (EditText) findViewById(R.id.password);
                String user = useredittext.getText().toString();
                String pass = passwordedittext.getText().toString();
                if(user.equals("GOD") && pass.equals("GodZilla") && contents.equals("10th December 2016")){
                    Intent intent = new Intent(ExecutiveloginActivity.this,MainActivity.class);
                    editor.putBoolean("isgod",true);
                    editor.apply();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),(CharSequence)"INVALID CREDENTIALS",Toast.LENGTH_SHORT).show();
                    editor.putBoolean("isgod",false);
                }
            }
        }
    }
}
