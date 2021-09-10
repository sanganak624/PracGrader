package com.example.pracgrader.classfiles;

import java.util.LinkedList;
import java.util.List;

import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.classfiles.User;

public class Instructor extends User {
    String email;
    String name;
    int country;
    List<Student> students = new LinkedList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student)
    {
        students.add(student);
    }

    public Instructor(String username, int pin,String name, String email, int country, List<Student> students) {
        super(username, pin);
        this.email = email;
        this.name = name;
        this.country = country;
        this.students = students;
    }

    public Instructor(String name, String email, int country) {
        super(null,-1);
        this.email = email;
        this.name = name;
        this.country = country;
    }

    public Instructor()
    {
        super(null,-1);
    }
}
