package com.example.pracgrader.databases.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pracgrader.classfiles.Student;
import com.example.pracgrader.databases.schema.DatabaseSchema;

public class StudentCursor extends CursorWrapper {
    public StudentCursor(Cursor cursor) {
        super(cursor);
    }

    public Student getStudent()
    {
        String username = getString(getColumnIndex(DatabaseSchema.StudentTable.Cols.USERNAME));
        int pin = getInt(getColumnIndex(DatabaseSchema.StudentTable.Cols.PIN));
        String name = getString(getColumnIndex(DatabaseSchema.StudentTable.Cols.NAME));
        String email = getString(getColumnIndex(DatabaseSchema.StudentTable.Cols.EMAIL));
        int country = getInt(getColumnIndex(DatabaseSchema.StudentTable.Cols.COUNTRY));

        return new Student(username,pin,name,email,country);
    }
}
