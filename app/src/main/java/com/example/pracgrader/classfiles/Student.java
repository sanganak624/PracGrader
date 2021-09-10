package com.example.pracgrader.classfiles;

import java.util.LinkedList;
import java.util.List;

public class Student extends User {
    String name;
    int country;
    List<Prac> pracs = new LinkedList<Prac>();
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
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

    public Student(String username, int pin, String name, String email, int country, List<Prac> pracs) {
        super(username, pin);
        this.name = name;
        this.country = country;
        this.pracs = pracs;
        this.email = email;
    }

    public Student(String username, int pin, String name, String email, int country) {
        super(username, pin);
        this.name = name;
        this.country = country;
        this.email = email;
    }

    public Student(String name, String email, int country) {
        super(null, -1);
        this.name = name;
        this.country = country;
        this.email = email;
    }

    public Student()
    {
        super(null,-1);
    }

    public void addPrac(Prac newPrac)
    {
        Prac copyPrac = new Prac(newPrac.title,newPrac.maxMarks,newPrac.mark,newPrac.description);
        pracs.add(copyPrac);
    }

}
