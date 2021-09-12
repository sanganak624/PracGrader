package com.example.pracgrader.databases.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.classfiles.Prac;
import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.databases.DatabaseHelper;
import com.example.pracgrader.databases.cursor.MarksCursor;
import com.example.pracgrader.databases.cursor.PracCursor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.List;

public class MarksList {

    private AppData appData = AppData.getInstance();
    private List<Prac> pracs = appData.getPracs();
    private SQLiteDatabase pracGraderDb;


    public MarksList()
    {

    }

    public void loadPracs(Context context)
    {
        if(pracGraderDb == null)
        {
            pracGraderDb = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        }

        MarksCursor marksCursor = new MarksCursor(pracGraderDb.query(DatabaseSchema.MarksTable.NAME,
                null,null,null,null,null,null));

        try{
            marksCursor.moveToFirst();
            while(!marksCursor.isAfterLast())
            {
                List<Object> returnRes = marksCursor.getMarked();

                Prac newPrac = (Prac) returnRes.get(0);
                String userName = (String) returnRes.get(1);

                List<Student> students = appData.getStudents();

                for(int i=0; i<students.size(); i++)
                {
                    Student student = students.get(i);
                    if(student.getUsername().equals(userName))
                    {
                        List<Prac> pracs = student.getPracs();
                        for(int j=0; j<pracs.size();j++)
                        {
                            Prac prac = pracs.get(j);
                            if(prac.getTitle().equals(newPrac.getTitle()))
                            {
                                prac.setMark(newPrac.getMark());
                                j=pracs.size();
                                i=students.size();
                            }
                        }
                    }

                }
                marksCursor.moveToNext();
            }
        }
        finally {
            marksCursor.close();
        }

    }

    public int addMark(Prac newPrac,Student student)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.MarksTable.Cols.TITLE,newPrac.getTitle());
        cv.put(DatabaseSchema.MarksTable.Cols.MAXMARK,newPrac.getMaxMarks());
        cv.put(DatabaseSchema.MarksTable.Cols.DIS,newPrac.getDescription());
        cv.put(DatabaseSchema.MarksTable.Cols.USERNAME,student.getUsername());
        cv.put(DatabaseSchema.MarksTable.Cols.MARK,newPrac.getMark());

        pracGraderDb.insert(DatabaseSchema.MarksTable.NAME,null,cv);

        return pracs.size() -1;
    }

    public void editMark(Prac newPrac, Student student)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.MarksTable.Cols.TITLE,newPrac.getTitle());
        cv.put(DatabaseSchema.MarksTable.Cols.MAXMARK,newPrac.getMaxMarks());
        cv.put(DatabaseSchema.MarksTable.Cols.DIS,newPrac.getDescription());
        cv.put(DatabaseSchema.MarksTable.Cols.USERNAME,student.getUsername());
        cv.put(DatabaseSchema.MarksTable.Cols.MARK,newPrac.getMark());

        String where = DatabaseSchema.MarksTable.Cols.TITLE + " =? AND " +
                       DatabaseSchema.MarksTable.Cols.USERNAME + " =?";

        String whereValue1 = String.valueOf(newPrac.getTitle());
        String whereValue2 = String.valueOf(student.getUsername());

        String[] cond = new String[]{whereValue1,whereValue2};

        pracGraderDb.update(DatabaseSchema.MarksTable.NAME, cv, where,cond);
    }

}
