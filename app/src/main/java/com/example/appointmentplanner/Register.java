package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class Register extends AppCompatActivity {

    private boolean username_true=false, email_ture=false, password_true=false,
                    passwords_identical=false,
                    number= false, capital_letters = false, special_characters= false,
                    passwordLength= false;
    //TextInputLayout declaration
    private TextInputLayout username_layout, email_layout, password_layout, repeat_password_layout;
    //EditText declaration
    private EditText username_input,email_input,password_input, repeat_password_input;
    //Button declaration
    private Button back,register;
    //Textview
    private TextView login,numberTextview,specialCharactersTextview,capitalLetterTextview;
    Context context = Register.this;
    Statusbar statusbar;
    HandelDB Db = new HandelDB();
    boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //Hide Actionbar
        Objects.requireNonNull(getSupportActionBar()).hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(Register.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_register);

        //Button initialize
         back =  findViewById(R.id.back);
         register = findViewById(R.id.regbt);
         login = findViewById(R.id.login);

        //TextInputLayout initialize
        username_layout = findViewById(R.id.usernameinputlayout);
        email_layout = findViewById(R.id.emaillayout);
        password_layout = findViewById(R.id.passwordlayout);
        repeat_password_layout = findViewById(R.id.repeatpasswordlayout);

        //EditText initialize
        username_input = findViewById(R.id.usernameinput);
        email_input = findViewById(R.id.emailinput);
        password_input = findViewById(R.id.passwordinput);
        repeat_password_input = findViewById(R.id.repeatpasswordinput);
        //Textview initialize
        numberTextview = findViewById(R.id.numberTextview);
        specialCharactersTextview= findViewById(R.id.specialCharactersTextview);
        capitalLetterTextview = findViewById(R.id.capitalLetterTextview);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        username_input.addTextChangedListener(new TextWatcher()  {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String username = s.toString();

                if(username.length() > 2)
                {
                    if(!check_special_characters(username))
                    {
                        username_layout.setHelperText("✓");
                        username_layout.setBoxStrokeColor(Color.parseColor("#FF8BC34A"));
                        username_true = true;
                        username_layout.setErrorEnabled(false);
                    }
                    else
                    {
                        username_layout.setError("X to short or include special characters");
                        username_true = false;
                    }

                }
                else
                {
                    username_layout.setBoxStrokeColor(Color.parseColor("#FFFFFFFF"));
                    username_layout.setHelperText("");
                    username_true= false;
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                if(email.length() > 2 &&  email.contains("@") &&email.contains("."))
                {
                    email_layout.setBoxStrokeColor(Color.parseColor("#FF8BC34A"));
                    email_layout.setHelperText(" ✓ ");
                    email_ture= true;
                }
                else
                {
                    email_layout.setBoxStrokeColor(Color.parseColor("#FFFFFFFF"));
                    email_layout.setHelperText("");
                    email_ture= false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        password_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 String password1 = s.toString();
                 if(password1.length()>2) {
                     passwordLength = true;
                 }
                 else {
                     passwordLength = false;
                     password_true = false;
                 }
                if(check_number(password1)) {
                    number= true;
                }
                else {
                    number = false;
                    password_true=false;
                }
                 if(check_capital_letters(password1)) {
                     capital_letters = true;
                 }
                 else {
                     capital_letters = false;
                     password_true=false;
                 }

                 if(check_special_characters(password1)) {
                     special_characters = true;
                 }
                 else {
                     special_characters = false;
                 }

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if(number)
                {
                    numberTextview.setText("⦿ Number ✓ ");
                    numberTextview.setTextColor(Color.parseColor("#FF8BC34A"));
                }
                else
                {
                    numberTextview.setText("⦿ Number X");
                    numberTextview.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                if(capital_letters)
                {
                    capitalLetterTextview.setText("⦿ Capital letters ✓");
                    capitalLetterTextview.setTextColor(Color.parseColor("#FF8BC34A"));

                }
                else
                {
                    capitalLetterTextview.setText("⦿ Capital letters X");
                    capitalLetterTextview.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                if (special_characters) {
                    specialCharactersTextview.setText("⦿ specialCharacters ✓");
                    specialCharactersTextview.setTextColor(Color.parseColor("#FF8BC34A"));
                    password_layout.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#FFFF9800")));
                }
                else
                {
                    specialCharactersTextview.setText("⦿ specialCharacters X");
                    specialCharactersTextview.setTextColor(Color.parseColor("#FFFFFFFF"));
                }

                if(number&&capital_letters&&passwordLength) {

                    password_true = true;
                }
                if(number&&capital_letters&&special_characters&&passwordLength) {
                    password_layout.setBoxStrokeColor(Color.parseColor("#FF8BC34A"));
                    password_layout.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#FF8BC34A")));
                    password_true = true;
                }
                else
                {
                    password_layout.setBoxStrokeColor(Color.parseColor("#FFFFFFFF"));
                    password_layout.setHelperTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
                }

            }
        });
        repeat_password_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(password_input.getText().toString()))
                {
                    repeat_password_layout.setBoxStrokeColor(Color.parseColor("#FF8BC34A"));
                    repeat_password_layout.setHelperText("✓");
                    passwords_identical = true;
                    repeat_password_layout.setErrorEnabled(false);
                }
                else
                {
                    repeat_password_layout.setError("!");
                    passwords_identical = false;
                }
            }
        });
        //Buttons on click
        register.setOnClickListener(v -> {
            if(!username_true)
            {
                username_layout.setError("username is required");
            }
            else if(Db.check_username(username_input.getText().toString().trim()) == 1)
            {
                username_true=false;
                username_layout.setError("Username already exist");
            }
            if(!email_ture)
            {
                email_layout.setError( "Please press a valid E-Mail");
            }
            if(Db.check_email(email_input.getText().toString().trim()) == 1)
            {
                email_ture=false;
                email_layout.setError("Email already exist");
            }
            if(!password_true)
            {
                password_layout.setError("password is required");
            }
            if(!passwords_identical)
            {
                repeat_password_layout.setError("password is required");
            }
            if(!checkInternetConnection())
            {
                Toast.makeText(getApplicationContext(), "please Check your Internet\nConnection", Toast.LENGTH_LONG).show();
            }
            else
            {
                if(username_true && email_ture && password_true && passwords_identical)
                {

                    if(Db.insert_user_data(username_input.getText().toString().trim()
                            , email_input.getText().toString().trim()
                            , password_input.getText().toString().trim()))
                    {
                        Message.message(context,"Successful registration");
                        openMain(Homepage.class);
                    }
                    else
                    {
                        Message.message(context, "Registration failed!");
                    }
                }
            }
        });
        login.setOnClickListener(v -> openMain(Login.class));
        back.setOnClickListener(v -> openMain(MainActivity.class));
    }
    public void openMain(Class classname){
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
    public boolean check_number (String string)
    {
        String numbers = "1234567890";
        int counter = 0;
        for(int i = 0 ; i < string.length();i++)
        {
            for (int j = 0; j < numbers.length(); j++)
            {
                if(string.charAt(i) == numbers.charAt(j))
                {
                    counter++;
                }
            }
        }
        if(counter >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean check_special_characters (String string){
        String special_characters = "!\"§$%&@/()=?`´°^ '_:;,.-#++*~€|{[]}\\<>";
        int counter = 0;
        for(int i = 0 ; i < string.length();i++)
        {
            for (int j = 0; j < special_characters.length(); j++)
            {
                if(string.charAt(i) == special_characters.charAt(j))
                {
                    counter++;
                }
            }
        }
        if(counter >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean check_capital_letters (String string)
    {
        int counter= 0;
        for (int i=0 ; i<string.length();i++)
        {
            if(Character.isUpperCase(string.charAt(i)))
            {
                counter++;
            }
            else
            {

            }
        }
        if (counter>=1)
        {
            return true;
        }
        else
        {
            return false;
        }

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