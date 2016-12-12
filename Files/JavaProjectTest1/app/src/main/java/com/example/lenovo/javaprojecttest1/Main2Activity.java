package com.example.lenovo.javaprojecttest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList al=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
    public void buttonOnClick() {
        Button bt = (Button) findViewById(R.id.submit);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main2Activity.this,displaycomplaint.class);
                startActivity(i);
                EditText un=(EditText) findViewById(R.id.username);
                al.add(un.getText().toString());
                EditText pno=(EditText) findViewById(R.id.phno);
                al.add(pno.getText().toString());
                EditText cpl=(EditText) findViewById(R.id.complain);
                al.add(cpl.getText().toString());
                EditText lcn=(EditText) findViewById(R.id.location);
                al.add(un.getText().toString());
                EditText pin=(EditText) findViewById(R.id.pincode);
                al.add(pin.getText().toString());
                System.out.println(al);
                TextView vals=(TextView) findViewById(R.id.textarea);
                vals.append(al.get(0).toString());
                vals.append(al.get(1).toString());
                vals.append(al.get(2).toString());
                vals.append(al.get(3).toString());
                vals.append(al.get(4).toString());



            }
        });
    }

}
