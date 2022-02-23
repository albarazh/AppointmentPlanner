package com.example.appointmentplanner;

import android.util.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HandelDB {
    private static final String LOG = "DEBUGHandel";
    private static final String users_table= "users_table";
    private static final String note_table="note_table";
    private static final String todo_table="todo_table";
    private static int  user_id ;
    private static String  username ;

    private static final DatenBank datenBank = new DatenBank();

    protected int getTableRows (String tableName)
    {
        int rows = 0;
        try {
            Connection connection = datenBank.connection();
            String statement ="SELECT count(*) FROM "+tableName+" where user_id="+user_id+";";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(statement);
            if (rs.next())
            {
                rows = rs.getInt(1);
            }
        } catch (Exception e)
        {
            Log.d(LOG, e.getMessage());
        }
        return rows;
    }
    protected int determine_user_id(String email , String password)
    {
        int user_id = 0;
        try {
            Connection connection = datenBank.connection();
            String statement ="Select user_id from " + users_table +
                    " Where email= '" + email + "' and password='" + password + "';";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(statement);
            if (rs.next())
            { 
                user_id = rs.getInt(1);
            }
        } catch (Exception e)
        {
            Log.d(LOG, e.getMessage());
        }
        return user_id;
    }
    protected String determine_username(String email,String password)
    {
        String username = "";
        try {
            Connection connection = datenBank.connection();
            String statement ="Select username from " + users_table +
                    " Where email= '" + email + "' and password='" + password + "';";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(statement);
            if (rs.next())
            {
                username = rs.getString(1);
            }
            connection.close();
        } catch (Exception e)
        {
            Log.d(LOG, e.getMessage());
        }

        return username;
    }
    protected Boolean insert_user_data(String username, String email, String password)
    {
        try {
            Connection connection = datenBank.connection();
            String queryStmt = "INSERT INTO "+users_table+
                    " (username, email, password) values "
                    + "('"+username+"', '"+email+"', '"+password+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.d(LOG, "Added successfully");
            return true;
        }
        catch (SQLException e)
        {
            Log.d(LOG, "SQLException "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception. Please check your code and database.");
            return false;
        }
    }
    protected Boolean insert_note(String title, String text,String color,String imagePath,String url)
    {
        try {
            Connection connection = datenBank.connection();
            String queryStmt = "INSERT INTO "+note_table+
                    " (user_id, noteTitle, noteText, noteDate,noteColor,imagePath,url) values "
                    + "("+getUser_id()+",'"+title+"','"+text+"', getutcdate(), '"+color+"', '"+imagePath+"', '"+url+"')";
            PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.d(LOG, "Added successfully");
            connection.close();
            return true;
        }
        catch (SQLException e)
        {
            Log.d(LOG, "SQLException "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception. Please check your code and database.");
            return false;
        }
    }
    protected Boolean update_note(String title, String text,String color,String imagePath,String url,int note_id)
    {
        try {
            Connection connection = datenBank.connection();
            String queryStmt = "UPDATE "+note_table+" SET noteTitle = '"+title+"', noteText = '"+text+"'" +
                    ", noteColor = '"+color+"', imagePath = '"+imagePath+"', url = '"+url+"' WHERE note_id ="+note_id+";";
            PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.d(LOG, "Updated successfully");
            connection.close();
            return true;
        }
        catch (SQLException e)
        {
            Log.d(LOG, "SQLException: "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception: "+e.getMessage());
            return false;
        }
    }
    protected Boolean delete_note(int note_id)
    {
        try {
            Connection connection = datenBank.connection();
            String queryStmt = "DELETE FROM "+note_table+" WHERE note_id ="+note_id+";";
            PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.d(LOG, "Updated successfully");
            connection.close();
            return true;
        }
        catch (SQLException e)
        {
            Log.d(LOG, "SQLException: "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception: "+e.getMessage());
            return false;
        }
    }
    protected Boolean insert_todo(String text,String status,String color)
    {
        try {
            Connection connection = datenBank.connection();
            String queryStmt = "INSERT INTO "+todo_table+
                    " (user_id, todoText, todoStatus, todoColor, todoDate) values "
                    + "("+getUser_id()+",'"+text+"', '"+status+"', '"+color+"', getutcdate())";
            PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.d(LOG, "Added successfully");
            connection.close();
            return true;
        }
        catch (SQLException e)
        {
            Log.d(LOG, "SQLException "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception. Please check your code and database.");
            return false;
        }
    }
    protected Boolean update_todo(String text,String status,int todo_id)
    {
        try {
            Connection connection = datenBank.connection();
            String queryStmt = "UPDATE "+todo_table+" SET todoText = '"+text+"', todoStatus = '"+status+"' WHERE todo_id ="+todo_id+";";
            PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.d(LOG, "Updated successfully");
            connection.close();
            return true;
        }
        catch (SQLException e)
        {
            Log.d(LOG, "SQLException: "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception: "+e.getMessage());
            return false;
        }
    }
    protected Boolean delete_todo(int todo_id)
    {
        try {
            Connection connection = datenBank.connection();
            String queryStmt = "DELETE FROM "+todo_table+" WHERE todo_id ="+todo_id+";";
            PreparedStatement preparedStatement = connection.prepareStatement(queryStmt);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            Log.d(LOG, "Updated successfully");
            connection.close();
            return true;
        }
        catch (SQLException e)
        {
            Log.d(LOG, "SQLException: "+e.getMessage());
            return false;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception: "+e.getMessage());
            return false;
        }
    }
    protected int check_username (String username)
    {
        try {
            Connection connection = datenBank.connection();
            String username_rs = "Select * from "+users_table+" Where username='"+username+"';";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(username_rs);
            if(rs.next())
            {
                connection.close();
                return 1;
            }
            else
            {
                connection.close();
                return 0;
            }
        }
        catch (SQLException e)
        {
            Log.d(LOG, e.getMessage());
            e.printStackTrace();
            return 0;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception. Please check your code and database.");
            return 0;
        }
    }
    protected int check_email (String email)
    {
        try {
            Connection connection = datenBank.connection();
            String email_rs = "Select * from "+users_table+" Where email= '"+email+"';";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(email_rs);
            if(rs.next())
            {
                connection.close();
                return 1;
            }
            else
            {
                connection.close();
                return 0;
            }

        }
        catch (SQLException e)
        {
            Log.d(LOG, e.getMessage());
            return 0;
        }
        catch (Exception e)
        {
            Log.d(LOG, "Exception. Please check your code and database.");
            return 0;
        }
    }
    protected int check_Login (String email, String password)
    {
        try
        {
            Connection connection = datenBank.connection();
            String login ="Select email, password from " + users_table +
                    " Where email= '" + email + "' and password='" + password + "';";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(login);
                if (rs.next())
                {
                    user_id = determine_user_id(email,password);
                    username = determine_username(email,password);
                    Log.d(LOG, "user_id= "+user_id);
                    connection.close();
                    return 1;
                }
                else
                {
                    connection.close();
                    return 0;
                }
        } catch (Exception e)
        {
            Log.d(LOG, e.getMessage());
            return 0;
        }
    }
    protected ArrayList<Note> getAllNote()
    {
        ArrayList<Note> noteList = new ArrayList<>();
        try {
            Connection connection = datenBank.connection();
            String getAllNotes ="Select * from "+note_table;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getAllNotes);
            int rows = 0;
            while(rs.next())
            {
                Note note = new Note();
                note.setId(rs.getInt("note_id"));
                note.setTitle(rs.getString("noteTitle"));
                note.setNoteText(rs.getString("noteText"));
                note.setCurrent_Date(rs.getString("noteDate"));
                note.setColor(rs.getString("noteColor"));
                note.setImagePath(rs.getString("ImagePath"));
                if(rs.getString("url")!= null || !rs.getString("url").equals("null"))
                {
                    note.setLink(rs.getString("url"));
                }
                else
                {
                    note.setLink("null");
                }
                noteList.add(note);
                rows++;
            }
            connection.close();
        }
        catch (SQLException e)
        {
            Log.d("NOTE_HandelDB", e.getMessage());
        }
        catch (Exception e)
        {
            Log.d("NOTE_HandelDB", e.getMessage());
        }
        return noteList;
    }
    protected ArrayList<Todo> getAllTodos()
    {
        ArrayList<Todo> todoList = new ArrayList<>();
        try {
            Connection connection = datenBank.connection();
            String getAllNotes ="Select * from "+todo_table;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getAllNotes);
            while(rs.next())
            {
                Todo todo = new Todo();
                todo.setTodo_id(rs.getInt("todo_id"));
                todo.setTodoText(rs.getString("todoText"));
                todo.setTodoStatus(rs.getString("todoStatus"));
                todo.setTodoDate(rs.getString("todoDate"));
                todoList.add(todo);
            }
            connection.close();
        }
        catch (SQLException e)
        {
            Log.d("TODO_HandelDB", e.getMessage());
        }
        catch (Exception e)
        {
            Log.d("TODO_HandelDB", e.getMessage());
        }
        return todoList;
    }
    protected int getUser_id() {
        return user_id;
    }
    protected String getUsername() {
        return username;
    }
}
