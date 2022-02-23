package com.example.appointmentplanner;

import net.sourceforge.jtds.jdbc.DateTime;

import java.io.Serializable;

public class Todo implements Serializable {
    private int todo_id;
    private String todoText;
    private String todoStatus;
    private String todoDate;
    private String todoColor;

    protected int getTodo_id() {
        return todo_id;
    }

    protected void setTodo_id(int todo_id) {
        this.todo_id = todo_id;
    }

    protected String getTodoText() {
        return todoText;
    }

    protected void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    protected String getTodoStatus() {
        return todoStatus;
    }

    protected void setTodoStatus(String todoStatus) {
        this.todoStatus = todoStatus;
    }

    protected String getTodoDate() {
        return todoDate;
    }

    protected void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

    public String getTodoColor() {
        return todoColor;
    }

    public void setTodoColor(String todoColor) {
        this.todoColor = todoColor;
    }
}
