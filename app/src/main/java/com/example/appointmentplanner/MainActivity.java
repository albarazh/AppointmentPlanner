package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Button login = (Button) findViewById(R.id.loginButton);
        Button register = (Button) findViewById(R.id.regButoon);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openpage(Login.class);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openpage(Register.class);
            }
        });
    }
    public void openpage (Class classname){
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
}