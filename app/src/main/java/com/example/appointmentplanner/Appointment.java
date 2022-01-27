package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

public class Appointment extends AppCompatActivity {
    Statusbar statusbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(Appointment.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_appointment);
    }
}