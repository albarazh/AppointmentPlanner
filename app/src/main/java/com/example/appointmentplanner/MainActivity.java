package com.example.appointmentplanner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;

import java.sql.Connection;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Statusbar statusbar;
    LoadingDialog loadingDialog;
    private static final DatenBank datenBank = new DatenBank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        statusbar = new Statusbar();
        loadingDialog = new LoadingDialog(MainActivity.this);
        statusbar.check_sdk(MainActivity.this, Build.VERSION.SDK_INT);
        Button login = findViewById(R.id.loginButton);
        Button register = findViewById(R.id.regButoon);
        login.setOnClickListener(new View.OnClickListener(){
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

    public void openpage(Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
}