package com.example.pracgrader.classfiles;

import java.util.LinkedList;
import java.util.List;

public class Student extends User {
    String name;
    int country;
    double avgMark;
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

    public double getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(double avgMark) {
        this.avgMark = avgMark;
    }

    public Student(String username, int pin, String name, String email, int country, List<Prac> pracs) {
        super(username, pin,3);
        this.name = name;
        this.country = country;
        this.pracs = pracs;
        this.email = email;
        avgMark =-1;
    }

    public Student(String username, int pin, String name, String email, int country) {
        super(username, pin, 3);
        this.name = name;
        this.country = country;
        this.email = email;
        avgMark =-1;
    }

    public Student(String username, int pin, String name, String email, int country,double avgMark) {
        super(username, pin, 3);
        this.name = name;
        this.country = country;
        this.email = email;
        this.avgMark = avgMark;
    }

    public Student(String name, String email, int country) {
        this.name = name;
        this.country = country;
        this.email = email;
        avgMark=-1;
    }

    public Student()
    {
        super.setRole(3);
    }

    public void addPrac(Prac newPrac)
    {
        Prac copyPrac = new Prac(newPrac.title,newPrac.maxMarks,newPrac.mark,newPrac.description);
        pracs.add(copyPrac);
    }

    public void updateMark()
    {
        double totalScore=0;
        int count = 0;
        for(int i=0; i<pracs.size();i++)
        {
            double score = pracs.get(i).getMark();
            if(score!=-1)
            {
                totalScore = totalScore+score/pracs.get(i).getMaxMarks()*100;
                count++;
            }
        }
        avgMark = totalScore/count;
    }

}
