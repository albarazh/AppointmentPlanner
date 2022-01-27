package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Homepage extends AppCompatActivity {
    private HandelDB DB;
    Statusbar statusbar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(Homepage.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_homepage);
        DB = new HandelDB();

        ImageButton noteButton = findViewById(R.id.noteButton);
        TextView welcomeBack = findViewById(R.id.welcomeText);
        welcomeBack.setText("Welcome Back "+DB.getUsername());
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(NoteOverview.class);
            }
        });

    }
    public void openPage(Class classname){
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}