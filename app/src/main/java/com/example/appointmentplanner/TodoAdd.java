package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TodoAdd extends AppCompatActivity {
    private HandelDB dataBase;
    Statusbar statusbar;
    private View view;
    private Todo alreadyAvailableTodo;
    ImageView imageColor1,imageColor2,imageColor3,imageColor4,imageColor5,imageColor6,imageColor7;
    Button save ,back, update;
    TextView todoDate;
    FrameLayout color1,color2,color3,color4,color5,color6,color7;
    EditText todoTextInput;
    LinearLayout layoutTaskdone;
    String currentDate;
    private String selectTodoColor;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Objects.requireNonNull(getSupportActionBar()).hide();
        statusbar = new Statusbar();
        statusbar.check_sdk(TodoAdd.this, Build.VERSION.SDK_INT);
        setContentView(R.layout.activity_todo_add);
        dataBase = new HandelDB();
        back = findViewById(R.id.todoBack);
        todoDate = findViewById(R.id.todoDate);
        view = findViewById(R.id.todoColorView);
        layoutTaskdone = findViewById(R.id.layoutTaskdone);
        save = findViewById(R.id.todoSave);
        back = findViewById(R.id.todoBack);
        update = findViewById(R.id.todoUpdate);
        todoTextInput = findViewById(R.id.todoTextInput);
        selectTodoColor = "#121617";
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTodo();
            }
        });
        todoDate.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(new Date()));
        imageColor1 = findViewById(R.id.imageColor1);
        imageColor2 = findViewById(R.id.imageColor2);
        imageColor3 = findViewById(R.id.imageColor3);
        imageColor4 = findViewById(R.id.imageColor4);
        imageColor5 = findViewById(R.id.imageColor5);
        imageColor6 = findViewById(R.id.imageColor6);
        imageColor7 = findViewById(R.id.imageColor7);

        findViewById(R.id.viewColor1).setOnClickListener(v -> {
            selectTodoColor = "#121617";
            imageColor1.setImageResource(R.drawable.ic_check);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectTodoColor);

        });
       findViewById(R.id.viewColor1).performClick();
       findViewById(R.id.viewColor2).setOnClickListener(v -> {
           selectTodoColor = "#E91E63";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_check);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectTodoColor);

        });
        findViewById(R.id.viewColor3).setOnClickListener(v -> {
            selectTodoColor = "#00BCD4";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_check);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectTodoColor);

        });
        findViewById(R.id.viewColor4).setOnClickListener(v -> {
            selectTodoColor = "#673AB7";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_check);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectTodoColor);

        });
        findViewById(R.id.viewColor5).setOnClickListener(v -> {
            selectTodoColor = "#FF9800";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(R.drawable.ic_check);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(0);
            setViewColor(selectTodoColor);

        });
        findViewById(R.id.viewColor6).setOnClickListener(v -> {
            selectTodoColor = "#F44336";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(R.drawable.ic_check);
            imageColor7.setImageResource(0);
            setViewColor(selectTodoColor);

        });
        findViewById(R.id.viewColor7).setOnClickListener(v -> {
            selectTodoColor = "#8BC34A";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
            imageColor6.setImageResource(0);
            imageColor7.setImageResource(R.drawable.ic_check);
            setViewColor(selectTodoColor);
        });
        if(getIntent().getBooleanExtra("isViewOrUpdate",false))
        {
            alreadyAvailableTodo = (Todo) getIntent().getSerializableExtra("todo");
            setViewUpdateTodo();
        }
    }
        public void setViewColor(String color)
        {
            GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
            gradientDrawable.setColor(Color.parseColor(color));
        }
    private void setViewUpdateTodo()
    {

        save.setVisibility(View.GONE);
        update.setVisibility(View.VISIBLE);
        layoutTaskdone.setVisibility(View.VISIBLE);
        todoTextInput.setText(alreadyAvailableTodo.getTodoText());
        todoDate.setText(alreadyAvailableTodo.getTodoDate());
//        if (!alreadyAvailableTodo.getTodoColor().trim().isEmpty() && alreadyAvailableTodo.getTodoColor() != null)
//        {
//            switch (alreadyAvailableTodo.getTodoColor())
//            {
//                case "#121617":
//                    selectTodoColor = "#121617";
//                    imageColor1.setImageResource(R.drawable.ic_check);
//                    imageColor2.setImageResource(0);
//                    imageColor3.setImageResource(0);
//                    imageColor4.setImageResource(0);
//                    imageColor5.setImageResource(0);
//                    imageColor6.setImageResource(0);
//                    imageColor7.setImageResource(0);
//                    setViewColor(selectTodoColor);
//                    break;
//                case "#E91E63":
//                    selectTodoColor = "#E91E63";
//                    imageColor1.setImageResource(0);
//                    imageColor2.setImageResource(R.drawable.ic_check);
//                    imageColor3.setImageResource(0);
//                    imageColor4.setImageResource(0);
//                    imageColor5.setImageResource(0);
//                    imageColor6.setImageResource(0);
//                    imageColor7.setImageResource(0);
//                    setViewColor(selectTodoColor);
//                    break;
//                case "#00BCD4":
//                    selectTodoColor = "#00BCD4";
//                    imageColor1.setImageResource(0);
//                    imageColor2.setImageResource(0);
//                    imageColor3.setImageResource(R.drawable.ic_check);
//                    imageColor4.setImageResource(0);
//                    imageColor5.setImageResource(0);
//                    imageColor6.setImageResource(0);
//                    imageColor7.setImageResource(0);
//                    setViewColor(selectTodoColor);
//                    break;
//                case "#673AB7":
//                    selectTodoColor = "#673AB7";
//                    imageColor1.setImageResource(0);
//                    imageColor2.setImageResource(0);
//                    imageColor3.setImageResource(0);
//                    imageColor4.setImageResource(R.drawable.ic_check);
//                    imageColor5.setImageResource(0);
//                    imageColor6.setImageResource(0);
//                    imageColor7.setImageResource(0);
//                    setViewColor(selectTodoColor);
//                    break;
//                case "#FF9800":
//                    selectTodoColor = "#FF9800";
//                    imageColor1.setImageResource(0);
//                    imageColor2.setImageResource(0);
//                    imageColor3.setImageResource(0);
//                    imageColor4.setImageResource(0);
//                    imageColor5.setImageResource(R.drawable.ic_check);
//                    imageColor6.setImageResource(0);
//                    imageColor7.setImageResource(0);
//                    setViewColor(selectTodoColor);
//                    break;
//                case "#F44336":
//                    selectTodoColor = "#F44336";
//                    imageColor1.setImageResource(0);
//                    imageColor2.setImageResource(0);
//                    imageColor3.setImageResource(0);
//                    imageColor4.setImageResource(0);
//                    imageColor5.setImageResource(0);
//                    imageColor6.setImageResource(R.drawable.ic_check);
//                    imageColor7.setImageResource(0);
//                    setViewColor(selectTodoColor);
//                    break;
//                case "#8BC34A":
//                    selectTodoColor = "#8BC34A";
//                    imageColor1.setImageResource(0);
//                    imageColor2.setImageResource(0);
//                    imageColor3.setImageResource(0);
//                    imageColor4.setImageResource(0);
//                    imageColor5.setImageResource(0);
//                    imageColor6.setImageResource(0);
//                    imageColor7.setImageResource(R.drawable.ic_check);
//                    setViewColor(selectTodoColor);
//                    break;
//            }
   //     }
    }
    private void saveTodo ()
    {
        if(todoTextInput.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Text is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String text ;
            text = todoTextInput.getText().toString().trim();
            currentDate = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault()).format(new Date());
            final Todo todo = new Todo();
            todo.setTodoText(text);
            todo.setTodoDate(currentDate);
            todo.setTodoStatus("false");
            todo.setTodoColor(selectTodoColor);

            if(alreadyAvailableTodo != null)
            {
                todo.setTodo_id(alreadyAvailableTodo.getTodo_id());
            }
            if(dataBase.insert_todo(todo.getTodoText(),todo.getTodoStatus(), todo.getTodoColor()))
            {
                Toast.makeText(getApplicationContext(), "Successful added", Toast.LENGTH_LONG).show();
                Log.d("AddTodo","Successful");
                openPage(TodoOverview.class);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to add the Note", Toast.LENGTH_LONG).show();
                Log.d("AddNote","Failed");
            }
        }
    }
    public void openPage(Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }

}