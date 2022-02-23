package com.example.appointmentplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoOverview extends AppCompatActivity implements TodoListener{
    Statusbar statusbar;
    private AlertDialog dialogAddTodo;
    ImageButton addTodoButton;
    HandelDB database;
    ArrayList<Todo> todoList;
    TodoAdapter todoAdapter;
    RecyclerView todoRecyclerView;
    public static final int REQUEST_CODE_ADD_NOTE= 1;
    public static final int REQUEST_CODE_UPDATE_NOTE= 2;
    public static final int REQUEST_CODE_SHOW_NOTES= 3;
    int noteClickedPosition = -1;
    EditText search_bar_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusbar = new Statusbar();
        statusbar.check_sdk(TodoOverview.this, Build.VERSION.SDK_INT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_to_do);
        database = new HandelDB();
        addTodoButton = findViewById(R.id.addTodoButton);
        addTodoButton.setOnClickListener(v -> openPage(TodoAdd.class));
        TextView item = findViewById(R.id.itemCountTodo);
        item.setText(MessageFormat.format("{0} /todo", database.getTableRows("todo_table")));
        search_bar_input =findViewById(R.id.search_bar_input_todo);
        search_bar_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                todoAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(todoList.size()!=0)
                {
                    todoAdapter.searchTodo(s.toString());
                }
            }
        });
        todoRecyclerView = findViewById(R.id.todo_list);
        todoRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        getTodos(REQUEST_CODE_SHOW_NOTES);
    }

    public void openPage(Class classname) {
        Intent intent = new Intent(this, classname);
        startActivity(intent);
    }
    @SuppressLint("NotifyDataSetChanged")
    private void getTodos (final int requestCode)
    {
        todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoList,this);
        todoRecyclerView.setAdapter(todoAdapter);
        if(requestCode == REQUEST_CODE_SHOW_NOTES)
        {
            todoList.addAll(getAllTodosFromDb());
            todoAdapter.notifyDataSetChanged();
        }
        else if (requestCode == REQUEST_CODE_ADD_NOTE)
        {
            todoList.add(0,getAllTodosFromDb().get(0));
            todoAdapter.notifyItemInserted(0);
            todoRecyclerView.smoothScrollToPosition(0);
        }
        else if (requestCode == REQUEST_CODE_UPDATE_NOTE)
        {
            todoList.remove(noteClickedPosition);
            todoList.add(noteClickedPosition, getAllTodosFromDb().get(noteClickedPosition));
            todoAdapter.notifyItemChanged(noteClickedPosition);
        }

    }
    @Override
    public void onTodoClicked(Todo todo, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(),TodoAdd.class);
        intent.putExtra("isViewOrUpdate",true);
        intent.putExtra("todo", todo);
        startActivityForResult(intent,REQUEST_CODE_UPDATE_NOTE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK)
        {
            getTodos(REQUEST_CODE_ADD_NOTE);
        } else if(requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                getTodos(REQUEST_CODE_UPDATE_NOTE);
            }
        }


    }
    private List<Todo> getAllTodosFromDb ()
    {
        List<Todo> todo = null;
        try{
            todo = database.getAllTodos();
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Failed to get the Todos from DB", Toast.LENGTH_SHORT).show();
        }
        return todo;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        finish();
        return false;
    }
}