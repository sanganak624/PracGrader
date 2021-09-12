package com.example.pracgrader.classfiles;

import java.util.LinkedList;
import java.util.List;

public class Admin extends User {
    //List<Student> students = new LinkedList<>();
    /*
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

     */

    public Admin(String username, int pin, List<Student> students) {
        super(username, pin,1);
        //this.students = students;
    }

    public Admin(String username, int pin) {
        super(username, pin,1);
    }

    public Admin() {
        super(null, -1,1);
    }

    /*public void addStudent(Student student)
    {
        students.add(student);
    }*/
}
