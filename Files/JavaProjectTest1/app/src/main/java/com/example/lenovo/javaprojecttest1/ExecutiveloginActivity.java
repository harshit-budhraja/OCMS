package com.example.lenovo.javaprojecttest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExecutiveloginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executivelogin);
        Button btn=(Button)findViewById(R.id.QRbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                startActivityForResult(intent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //      System.out.println("fiosfjsifjsoifjsofjsiojfoijfsofsifsoifsufsufiosfosubfsiofbsofbsiofsiofsiofbsiofb");
        if (requestCode == 0) {
//            System.out.println("fiosfjsifjsoifjsofjsiojf");
            if (resultCode == RESULT_OK) {
                // contents contains whatever was encoded
                String contents = data.getStringExtra("SCAN_RESULT");
                // Format contains the type of code i.e. UPC, EAN, QRCode etc...
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
            System.out.println(contents);
                Toast.makeText(this,contents,Toast.LENGTH_LONG);
                //          System.out.println("Hello");
                TextView tv=(TextView) findViewById(R.id.textView2);
                tv.setText(contents);
            }
        }
    }
}
