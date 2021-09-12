package com.example.pracgrader.databases.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.classfiles.Prac;
import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.classfiles.User;
import com.example.pracgrader.databases.DatabaseHelper;
import com.example.pracgrader.databases.cursor.PracCursor;
import com.example.pracgrader.databases.cursor.StudentCursor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.List;

public class PracList {

    private AppData appData = AppData.getInstance();
    private List<Prac> pracs = appData.getPracs();
    private SQLiteDatabase pracGraderDb;

    public PracList()
    {

    }

    public void loadPracs(Context context)
    {
        if(pracGraderDb == null)
        {
            pracGraderDb = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        }

        PracCursor pracCursor = new PracCursor(pracGraderDb.query(DatabaseSchema.PracTable.NAME,
                null,null,null,null,null,null));

        try{
            pracCursor.moveToFirst();
            while(!pracCursor.isAfterLast())
            {
                Prac newPrac = pracCursor.getPrac();
                pracs.add(newPrac);
                List<Student> students = appData.getStudents();
                for(int i=0; i<students.size(); i++)
                {
                    Student student = students.get(i);
                    student.addPrac(newPrac);
                }
                pracCursor.moveToNext();
            }
        }
        finally {
            pracCursor.close();
        }

    }

    public int pracSize()
    {
        return pracs.size();
    }

    public Prac getStudent(int i)
    {
        return pracs.get(i);
    }

    public int addPrac(Prac newPrac)
    {
        pracs.add(newPrac);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.PracTable.Cols.TITLE,newPrac.getTitle());
        cv.put(DatabaseSchema.PracTable.Cols.MAXMARK,newPrac.getMaxMarks());
        cv.put(DatabaseSchema.PracTable.Cols.DIS,newPrac.getDescription());
        pracGraderDb.insert(DatabaseSchema.PracTable.NAME,null,cv);

        return pracs.size() -1;
    }

    public void editPrac(Prac newPrac)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.PracTable.Cols.TITLE,newPrac.getTitle());
        cv.put(DatabaseSchema.PracTable.Cols.MAXMARK,newPrac.getMaxMarks());
        cv.put(DatabaseSchema.PracTable.Cols.DIS,newPrac.getDescription());

        String[] whereValue = { String.valueOf(newPrac.getTitle()) };
        pracGraderDb.update(DatabaseSchema.PracTable.NAME, cv,
                DatabaseSchema.PracTable.Cols.TITLE + " = ?", whereValue);
    }

    public void removePrac(Prac newPrac)
    {
        pracs.remove(newPrac);
        String[] whereValue = {String.valueOf(newPrac.getTitle())};
        pracGraderDb.delete(DatabaseSchema.PracTable.NAME, DatabaseSchema.PracTable.Cols.TITLE+" = ?", whereValue);
    }

}
