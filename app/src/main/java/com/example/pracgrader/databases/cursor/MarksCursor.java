package com.example.pracgrader.databases.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pracgrader.classfiles.Prac;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.LinkedList;
import java.util.List;

public class MarksCursor extends CursorWrapper {
    public MarksCursor(Cursor cursor) {
        super(cursor);
    }

    public List<Object> getMarked()
    {
        String title = getString(getColumnIndex(DatabaseSchema.MarksTable.Cols.TITLE));
        double maxMark = getDouble(getColumnIndex(DatabaseSchema.MarksTable.Cols.MAXMARK));
        String description = getString(getColumnIndex(DatabaseSchema.MarksTable.Cols.DIS));
        String student = getString(getColumnIndex(DatabaseSchema.MarksTable.Cols.USERNAME));
        double mark = getDouble(getColumnIndex(DatabaseSchema.MarksTable.Cols.MARK));
        Prac newPrac = new Prac(title,maxMark,mark,description);
        List<Object> returnList = new LinkedList<>();

        returnList.add(newPrac);
        returnList.add(student);

        return returnList;
    }
}
