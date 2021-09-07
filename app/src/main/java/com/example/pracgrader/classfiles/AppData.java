package com.example.pracgrader.classfiles;

import com.example.pracgrader.R;

import java.util.LinkedList;
import java.util.List;

public class AppData {
    private static AppData instance = null;

    List<Admin> admins = new LinkedList<Admin>();
    List<Student> students = new LinkedList<Student>();
    List<Instructor> instructors = new LinkedList<Instructor>();
    List<Prac> pracs = new LinkedList<Prac>();

    FlagData flagData = new FlagData();

    User currentUser = null;

    public static AppData getInstance()
    {
        if(instance==null)
        {
            instance = new AppData();
        }
        return instance;
    }

    public List<String> getFlagName()
    {
        return flagData.getNames();
    }

    public List<Integer> getFlags()
    {
        return flagData.getFlags();
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<Prac> getPracs() {
        return pracs;
    }

    public void setPracs(List<Prac> pracs) {
        this.pracs = pracs;
    }

    public Student getStudent(int i)
    {
        return students.get(i);
    }

    public Admin getAdmin(int i)
    {
        return admins.get(i);
    }

    public Instructor getInstructor(int i)
    {
        return instructors.get(i);
    }

    public Prac getPrac(int i)
    {
        return pracs.get(i);
    }

    public void addStudent(Student newStudent)
    {
        students.add(newStudent);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void addAdmin(Admin newAdmin)
    {
        admins.add(newAdmin);
    }

    public void addInstructor(Instructor newInstructor)
    {
        instructors.add(newInstructor);
    }

    public void addPrac(Prac newPrac)
    {
        pracs.add(newPrac);
    }

    public void fillTestData()
    {
        Prac prac1 = new Prac("Prac1",10,"Prac1");
        Prac prac2 = new Prac("Prac2",20,"Prac2");
        pracs.add(prac1);
        pracs.add(prac2);

        Student std1 = new Student("std1",1234,"std","std@email.com", R.drawable.flag_au);
        std1.addPrac(prac1);
        std1.addPrac(prac2);
        Student std2 = new Student("std2",1234,"std","std@email.com", R.drawable.flag_au);
        std2.addPrac(prac1);
        std2.addPrac(prac2);
        students.add(std1);
        students.add(std2);

        LinkedList<Student> instStudent = new LinkedList<>();
        instStudent.add(std2);

        Instructor inst1 = new Instructor("inst1",1234,"inst","inst@email.com",R.drawable.flag_af,instStudent);
        Admin admin = new Admin("admin",1234,students);

        admins.add(admin);
        instructors.add(inst1);

        currentUser = inst1;
    }
}
