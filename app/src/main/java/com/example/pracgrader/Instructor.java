package com.example.pracgrader;

import java.util.List;

public class Instructor extends User{
    String email;
    String name;
    String country;
    List<Student> students;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
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

    public Instructor(String username, int pin, int role, String email, String name, String country, List<Student> students) {
        super(username, pin, role);
        this.email = email;
        this.name = name;
        this.country = country;
        this.students = students;
    }
}
