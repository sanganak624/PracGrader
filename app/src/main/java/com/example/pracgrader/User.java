package com.example.pracgrader;

public abstract class User {
    String username;
    int pin;
    int role;

    public String getUsername() {
        return username;
    }

    public int getPin() {
        return pin;
    }

    public int getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public User(String username, int pin, int role) {
        this.username = username;
        this.pin = pin;
        this.role = role;
    }


}
