package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Button back = (Button) findViewById(R.id.back);
        Button cancel = (Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openpage(MainActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openpage(MainActivity.class);
            }
        });
    }
    public void openpage(Class classname){
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
}