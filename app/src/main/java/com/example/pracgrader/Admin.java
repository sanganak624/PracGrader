package com.example.pracgrader;

import java.util.List;

public class Admin extends User {
    List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Admin(String username, int pin, int role, List<Student> students) {
        super(username, pin, role);
        this.students = students;
    }

    public void addStudent(Student student)
    {
        students.add(student);
    }
}
