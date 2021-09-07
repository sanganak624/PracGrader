package com.example.pracgrader.databases.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.databases.DatabaseHelper;
import com.example.pracgrader.databases.cursor.InstructorCursor;
import com.example.pracgrader.databases.cursor.StudentCursor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class StudentList {

    private List<Student> students = new ArrayList<>();
    private SQLiteDatabase pracGraderDb;

    public StudentList()
    {

    }

    public void loadStudents(Context context)
    {
        if(pracGraderDb == null)
        {
            pracGraderDb = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        }

        StudentCursor studentCursor = new StudentCursor(pracGraderDb.query(DatabaseSchema.StudentTable.NAME,
                null,null,null,null,null,null));

        try{
            studentCursor.moveToFirst();
            while(!studentCursor.isAfterLast())
            {
                Student newStudent = studentCursor.getStudent();
                students.add(newStudent);
                studentCursor.moveToNext();
            }
        }
        finally {
            studentCursor.close();
        }

    }

    public int studentSize()
    {
        return students.size();
    }

    public Student getStudent(int i)
    {
        return students.get(i);
    }

    public int addStudent(Student newStudent)
    {
        students.add(newStudent);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.StudentTable.Cols.USERNAME,newStudent.getUsername());
        cv.put(DatabaseSchema.StudentTable.Cols.PIN,newStudent.getPin());
        cv.put(DatabaseSchema.StudentTable.Cols.NAME,newStudent.getName());
        cv.put(DatabaseSchema.StudentTable.Cols.EMAIL,newStudent.getEmail());
        cv.put(DatabaseSchema.StudentTable.Cols.COUNTRY,newStudent.getCountry());

        pracGraderDb.insert(DatabaseSchema.StudentTable.NAME,null,cv);

        return students.size() -1;
    }

    public void editStudent(Student newStudent)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.StudentTable.Cols.USERNAME,newStudent.getUsername());
        cv.put(DatabaseSchema.StudentTable.Cols.PIN,newStudent.getPin());
        cv.put(DatabaseSchema.StudentTable.Cols.NAME,newStudent.getName());
        cv.put(DatabaseSchema.StudentTable.Cols.EMAIL,newStudent.getEmail());
        cv.put(DatabaseSchema.StudentTable.Cols.COUNTRY,newStudent.getCountry());

        String[] whereValue = { String.valueOf(newStudent.getUsername()) };
        pracGraderDb.update(DatabaseSchema.StudentTable.NAME, cv,
                DatabaseSchema.StudentTable.Cols.USERNAME + " = ?", whereValue);
    }
}
