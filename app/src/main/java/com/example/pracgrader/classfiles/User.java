package com.example.pracgrader.classfiles;

public class User {
    String username;
    int pin;
    //int role; //1=Admin , 2=Instructor, 3=Student

    public String getUsername() {
        return username;
    }

    public int getPin() {
        return pin;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }


    public User(String username, int pin) {
        this.username = username;
        this.pin = pin;
        //this.role = role;
    }


}
