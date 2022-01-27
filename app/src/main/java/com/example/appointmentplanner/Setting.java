package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

public class Setting extends AppCompatActivity {
    Statusbar statusbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusbar = new Statusbar();
        statusbar.check_sdk(Setting.this, Build.VERSION.SDK_INT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
    }
}