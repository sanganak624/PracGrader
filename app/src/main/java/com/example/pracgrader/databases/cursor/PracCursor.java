package com.example.pracgrader.databases.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.classfiles.Prac;
import com.example.pracgrader.databases.schema.DatabaseSchema;

public class PracCursor extends CursorWrapper {
    public PracCursor(Cursor cursor) {
        super(cursor);
    }

    public Prac getPrac()
    {
        String title = getString(getColumnIndex(DatabaseSchema.PracTable.Cols.TITLE));
        double maxMark = getDouble(getColumnIndex(DatabaseSchema.PracTable.Cols.MAXMARK));
        String description = getString(getColumnIndex(DatabaseSchema.PracTable.Cols.DIS));

        return new Prac(title, maxMark,description);
    }
}
