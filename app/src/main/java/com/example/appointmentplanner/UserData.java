package com.example.appointmentplanner;

public class UserData {
    private String username;
    private String email;
    private String password;


    public UserData (String username,String email,String password) {
        username = this.username;
        email = this.email;
        password = this.password;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

}
