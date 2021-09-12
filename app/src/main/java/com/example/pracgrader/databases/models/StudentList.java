package com.example.pracgrader.databases.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Prac;
import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.classfiles.User;
import com.example.pracgrader.databases.DatabaseHelper;
import com.example.pracgrader.databases.cursor.InstructorCursor;
import com.example.pracgrader.databases.cursor.StudentCursor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class StudentList {

    private AppData appData = AppData.getInstance();
    private List<Student> students = appData.getStudents();
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
                List<Object> returnList = studentCursor.getStudent();
                Student newStudent = (Student) returnList.get(0);
                String creator = (String) returnList.get(1);
                if(!appData.getAdmins().get(0).getUsername().equals(creator))
                {
                    List<Instructor> instructors = appData.getInstructors();
                    for(int i=0; i<instructors.size();i++)
                    {
                        Instructor instructor = instructors.get(i);
                        if(instructor.getUsername().equals(creator))
                        {
                            instructor.addStudent(newStudent);
                            i = instructors.size();
                        }
                    }
                }
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

    public int addStudent(Student newStudent, User creator)
    {
        List<Prac> pracs = appData.getPracs();

        for(int i=0;i<pracs.size();i++)
        {
            newStudent.addPrac(pracs.get(i));
        }
        if(creator.getRole()==2)
        {
            Instructor instructor = (Instructor) creator;
            instructor.addStudent(newStudent);
        }
        students.add(newStudent);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.StudentTable.Cols.USERNAME,newStudent.getUsername());
        cv.put(DatabaseSchema.StudentTable.Cols.PIN,newStudent.getPin());
        cv.put(DatabaseSchema.StudentTable.Cols.NAME,newStudent.getName());
        cv.put(DatabaseSchema.StudentTable.Cols.EMAIL,newStudent.getEmail());
        cv.put(DatabaseSchema.StudentTable.Cols.COUNTRY,newStudent.getCountry());
        cv.put(DatabaseSchema.StudentTable.Cols.MARK,newStudent.getAvgMark());
        cv.put(DatabaseSchema.StudentTable.Cols.CREATOR,creator.getUsername());

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
        cv.put(DatabaseSchema.StudentTable.Cols.MARK,newStudent.getAvgMark());

        String[] whereValue = { String.valueOf(newStudent.getUsername()) };
        pracGraderDb.update(DatabaseSchema.StudentTable.NAME, cv,
                DatabaseSchema.StudentTable.Cols.USERNAME + " = ?", whereValue);
    }

    public void removeStudent(Student student)
    {
        students.remove(student);
        String[] whereValue = {String.valueOf(student.getUsername())};
        pracGraderDb.delete(DatabaseSchema.StudentTable.NAME, DatabaseSchema.StudentTable.Cols.USERNAME+" = ?", whereValue);
    }
}
