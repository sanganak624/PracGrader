package com.example.pracgrader;

import java.util.List;

public class Student extends User{
    String name;
    String country;
    List<Prac> pracs;
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Prac> getPracs() {
        return pracs;
    }

    public void setPracs(List<Prac> pracs) {
        this.pracs = pracs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student(String username, int pin, int role, String name, String country, List<Prac> pracs, String email) {
        super(username, pin, role);
        this.name = name;
        this.country = country;
        this.pracs = pracs;
        this.email = email;
    }

}
