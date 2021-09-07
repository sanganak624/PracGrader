package com.example.pracgrader.databases.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pracgrader.classfiles.Instructor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

public class InstructorCursor extends CursorWrapper {

    public InstructorCursor(Cursor cursor) {
        super(cursor);
    }

    public Instructor getInstructor()
    {
        String username = getString(getColumnIndex(DatabaseSchema.InstructorTable.Cols.USERNAME));
        int pin = getInt(getColumnIndex(DatabaseSchema.InstructorTable.Cols.PIN));
        String name = getString(getColumnIndex(DatabaseSchema.InstructorTable.Cols.NAME));
        String email = getString(getColumnIndex(DatabaseSchema.InstructorTable.Cols.EMAIL));
        int country = getInt(getColumnIndex(DatabaseSchema.InstructorTable.Cols.COUNTRY));

        return new Instructor(username,pin,name,email,country,null);
    }
}
