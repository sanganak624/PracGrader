package com.example.pracgrader.databases.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.databases.DatabaseHelper;
import com.example.pracgrader.databases.cursor.InstructorCursor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class InstructorList {

    private List<Instructor> instructors = new ArrayList<>();
    private SQLiteDatabase pracGraderDb;

    public InstructorList(){

    }

    public void loadInstructors(Context context)
    {
        if(pracGraderDb == null)
        {
            pracGraderDb = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        }
        InstructorCursor instructorCursor = new InstructorCursor(pracGraderDb.query(DatabaseSchema.AdminTable.NAME,
                null,null,null,null,null,null));
        try{
            instructorCursor.moveToFirst();
            while(!instructorCursor.isAfterLast())
            {
                Instructor newInstructor = instructorCursor.getInstructor();
                instructors.add(newInstructor);
                instructorCursor.moveToNext();
            }
        }
        finally {
            instructorCursor.close();
        }
    }

    public int instructorSize()
    {
        return instructors.size();
    }

    public Instructor getInstructor(int i)
    {
        return instructors.get(i);
    }

    public int addInstructor(Instructor newInstructor)
    {
        instructors.add(newInstructor);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.InstructorTable.Cols.USERNAME,newInstructor.getUsername());
        cv.put(DatabaseSchema.InstructorTable.Cols.PIN,newInstructor.getPin());
        cv.put(DatabaseSchema.InstructorTable.Cols.NAME,newInstructor.getName());
        cv.put(DatabaseSchema.InstructorTable.Cols.EMAIL,newInstructor.getEmail());
        cv.put(DatabaseSchema.InstructorTable.Cols.COUNTRY,newInstructor.getCountry());

        pracGraderDb.insert(DatabaseSchema.InstructorTable.NAME,null,cv);

        return instructors.size() -1;
    }

    public void editInstructor(Instructor newInstructor)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.InstructorTable.Cols.USERNAME,newInstructor.getUsername());
        cv.put(DatabaseSchema.InstructorTable.Cols.PIN,newInstructor.getPin());
        cv.put(DatabaseSchema.InstructorTable.Cols.NAME,newInstructor.getName());
        cv.put(DatabaseSchema.InstructorTable.Cols.EMAIL,newInstructor.getEmail());
        cv.put(DatabaseSchema.InstructorTable.Cols.COUNTRY,newInstructor.getCountry());

        String[] whereValue = { String.valueOf(newInstructor.getUsername()) };
        pracGraderDb.update(DatabaseSchema.InstructorTable.NAME, cv,
                DatabaseSchema.InstructorTable.Cols.USERNAME + " = ?", whereValue);

    }

}
