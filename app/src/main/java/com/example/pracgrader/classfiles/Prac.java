package com.example.pracgrader.classfiles;

public class Prac {
    String title;
    int maxMarks;
    int mark;
    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Prac(String title, int maxMarks, int mark, String description) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.mark = mark;
        this.description = description;
    }

    public Prac(String title, int maxMarks, String description) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.description = description;
    }
}
