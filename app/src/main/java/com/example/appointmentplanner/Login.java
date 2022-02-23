package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity {
    HandelDB Db = new HandelDB();
    Statusbar statusbar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(Login.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_login);
        LoadingDialog loadingDialog = new LoadingDialog(Login.this);
        Button back =  findViewById(R.id.back);
        Button login = findViewById(R.id.login);
        EditText email_input = findViewById(R.id.emailinput);
        EditText password_input = findViewById(R.id.passwordinput);
        TextView result = findViewById(R.id.result);
        result.setTextColor(Color.parseColor("#FFF44336"));
        TextView register = findViewById(R.id.register);


        login.setOnClickListener(v -> {

            if(email_input.getText().toString().isEmpty() && password_input.getText().toString().isEmpty() || email_input.getText().toString().isEmpty() || password_input.getText().toString().isEmpty())
            {
                result.setText("Email or password is Empty");
            }
            if(!checkInternetConnection())
            {
                Toast.makeText(getApplicationContext(), "please Check your Internet\nConnection", Toast.LENGTH_LONG).show();
            }
            else{
                if(Db.check_Login(email_input.getText().toString().trim(), password_input.getText().toString().trim()) == 1)
                {
                    //loadingDialog.startLoadingDialog();
                    Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_LONG).show();
                    openPage(Homepage.class);
                }
            }

        });
        register.setOnClickListener(v -> openPage(Register.class));
        back.setOnClickListener(v -> openPage(MainActivity.class));
    }
    public void openPage(Class classname){
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return false;
    }
    public Boolean checkInternetConnection()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else
        {
            return false;
        }
    }

}