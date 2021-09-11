package com.example.pracgrader.classfiles;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

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
        Prac prac3 = new Prac("Prac3",20,"Prac3");
        pracs.add(prac1);
        pracs.add(prac2);
        pracs.add(prac3);

        Student std1 = new Student("std1",1111,"std1","std@email.com", R.drawable.flag_au);
        std1.addPrac(prac1);
        std1.addPrac(prac2);
        std1.addPrac(prac3);
        Student std2 = new Student("std2",1111,"std2","std@email.com", R.drawable.flag_au);
        std2.addPrac(prac1);
        std2.addPrac(prac2);
        std2.addPrac(prac3);

        Student std3 = new Student("std3",1111,"std3","std@email.com", R.drawable.flag_au);
        std3.addPrac(prac1);
        std3.addPrac(prac2);
        std3.addPrac(prac3);
        Student std4 = new Student("std4",1111,"std4","std@email.com", R.drawable.flag_au);
        std4.addPrac(prac1);
        std4.addPrac(prac2);
        std4.addPrac(prac3);

        students.add(std1);
        students.add(std2);
        students.add(std3);
        students.add(std4);

        Instructor inst1 = new Instructor("inst1",1111,"inst","inst@email.com",R.drawable.flag_af);
        inst1.addStudent(std3);
        inst1.addStudent(std4);

        Admin admin = new Admin("admin",1111,students);

        admins.add(admin);
        instructors.add(inst1);
    }

    //Validation checks
    public boolean uniqueUsername(String newUser)
    {
        //Admin list
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).username.equals(newUser))
            {
                return false;
            }
        }
       //Instructor List
        for (int i = 0; i < instructors.size(); i++) {
            if (instructors.get(i).username.equals(newUser))
            {
                return false;
            }
        }
        //Student List
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).username.equals(newUser))
            {
                return false;
            }
        }
        return true;
    }

    public User findUsername(String newUser)
    {
        //Admin list
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).username.equals(newUser))
            {
                return admins.get(i);
            }
        }
        //Instructor List
        for (int i = 0; i < instructors.size(); i++) {
            if (instructors.get(i).username.equals(newUser))
            {
                return instructors.get(i);
            }
        }
        //Student List
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).username.equals(newUser))
            {
                return  students.get(i);
            }
        }
        return null;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static int stringToPin(List<EditText> pin)
    {
        String num1 = pin.get(0).getText().toString();
        String num2 = pin.get(1).getText().toString();
        String num3 = pin.get(2).getText().toString();
        String num4 = pin.get(3).getText().toString();

        int intPin = Integer.parseInt(num1+num2+num3+num4);

        return intPin;
    }
}
