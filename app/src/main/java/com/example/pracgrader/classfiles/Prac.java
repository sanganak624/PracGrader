package com.example.pracgrader.classfiles;

public class Prac {
    String title;
    double maxMarks;
    double mark;
    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public Prac(String title, double maxMarks, double mark, String description) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.mark = mark;
        this.description = description;
    }

    public Prac(String title, double maxMarks, String description) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.description = description;
        mark = -1;
    }
}
