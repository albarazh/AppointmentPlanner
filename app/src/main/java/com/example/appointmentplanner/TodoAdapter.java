package com.example.appointmentplanner;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>{

    private Todo todoObject;
    private List<Todo> todo;
    private TodoListener todosListener;
    private Timer timer;
    private List<Todo> todoSource;


    public TodoAdapter(List<Todo> todo, TodoListener todosListener)
    {
        this.todo = todo;
        this.todosListener = todosListener;
        todoSource = todo;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_contener_todo,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

        todoObject = todo.get(Objects.requireNonNull(holder).getAdapterPosition());
        holder.setTodo(todoObject);
        holder.layoutTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todosListener.onTodoClicked(todo.get(Objects.requireNonNull(holder).getAdapterPosition()),Objects.requireNonNull(holder).getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder
    {
        TextView todoText, todoDate;
        LinearLayout layoutTodo;
        TodoViewHolder (@NonNull View itemView)
        {
            super(itemView);
            todoText = itemView.findViewById(R.id.todoTitle);
            todoDate  = itemView.findViewById(R.id.todoDateContiner);
            layoutTodo= itemView.findViewById(R.id.layoutTodo);
        }
        void setTodo (Todo todo)
        {
            todoText.setText(todo.getTodoText());
            todoDate.setText(todo.getTodoDate());
            //GradientDrawable gradientDrawable = (GradientDrawable) layoutTodo.getBackground();
            //gradientDrawable.setColor(Color.parseColor(todo.getTodoColor()));

        }
    }
    public void searchTodo(final String searchKeyWord)
    {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKeyWord.trim().isEmpty())
                {
                    todo = todoSource;
                }
                else
                {
                    ArrayList<Todo> temp = new ArrayList<>();
                    for(Todo todo : todoSource)
                    {
                        if(todo.getTodoText().toLowerCase().contains(searchKeyWord.toLowerCase()))
                        {
                            temp.add(todo);
                        }
                    }
                    todo = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },5000);

    }
    public void cancelTimer()
    {
        if(timer!= null)
        {
            timer.cancel();
        }
    }
}
