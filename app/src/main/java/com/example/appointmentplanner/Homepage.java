package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Homepage extends AppCompatActivity {
    HandelDB DB;
    Statusbar statusbar;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    Boolean clicked = false;
    FloatingActionButton addButton, addNote,addTodo;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(Homepage.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_homepage);
        DB = new HandelDB();
        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_animation);
        rotateClose =  AnimationUtils.loadAnimation(this,R.anim.rotate_close_animation);
        fromBottom =  AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom =  AnimationUtils.loadAnimation(this,R.anim.to_bottom_animation);

        addButton = findViewById(R.id.floatingActionButton);
        addNote = findViewById(R.id.homePageAddNote);
        addTodo = findViewById(R.id.addTodo);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addNote.getVisibility()==View.GONE && addTodo.getVisibility() == View.GONE)
                {
                    addButton.startAnimation(rotateOpen);
                    setVisible();

                }
                else
                {
                    addButton.startAnimation(rotateClose);
                    setInvisible();
                }
            }
        });
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Homepage.this.openPage(NoteOverview.class);
                setInvisible();
            }
        });
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(TodoOverview.class);
                setInvisible();
            }
        });
        TextView welcomeBack = findViewById(R.id.welcomeText);
        welcomeBack.setText("Welcome Back "+DB.getUsername());

    }
    public void openPage(Class classname){
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    public void setVisible(){
        addNote.setVisibility(View.VISIBLE);
        addTodo.setVisibility(View.VISIBLE);
    }
    public void setInvisible(){
        addNote.setVisibility(View.GONE);
        addTodo.setVisibility(View.GONE);
    }
}