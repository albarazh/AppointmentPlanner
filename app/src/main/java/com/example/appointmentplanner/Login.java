package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    private boolean email_ture = false;
    private boolean password_true = false;
    private String email, password;
    HandelDB Db = new HandelDB();
    Context context = Login.this;
    Statusbar statusbar;
    LoadingDialog loadingDialog;
    int db_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(Login.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_login);
        LoadingDialog loadingDialog = new LoadingDialog(Login.this);
        Button back = (Button) findViewById(R.id.back);
        Button login = (Button) findViewById(R.id.login);
        TextInputLayout email_layout = findViewById(R.id.emaillayout);
        EditText email_input = findViewById(R.id.emailinput);
        TextInputLayout password_layout = findViewById(R.id.passwordlayout);
        EditText password_input = findViewById(R.id.passwordinput);
        TextView result = findViewById(R.id.result);
        result.setTextColor(Color.parseColor("#FFF44336"));
        TextView register = findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email_input.getText().toString().isEmpty() && password_input.getText().toString().isEmpty() || email_input.getText().toString().isEmpty() || password_input.getText().toString().isEmpty())
                {
                    result.setText("Email or password is Empty");
                }
                if(Db.check_Login(email_input.getText().toString().trim(), password_input.getText().toString().trim()) == 1)
                 {
                     loadingDialog.startLoadingDialog();
                     Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_LONG).show();
                     openpage(Homepage.class);
                 }
                 else
                 {
                     result.setText("Email or password is wrong");
                 }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpage(Register.class);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return false;
    }

}