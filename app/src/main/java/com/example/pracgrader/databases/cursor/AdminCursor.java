package com.example.pracgrader.databases.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.databases.schema.DatabaseSchema;

public class AdminCursor extends CursorWrapper {

    public AdminCursor(Cursor cursor) {
        super(cursor);
    }

    public Admin getAdmin()
    {
        String username = getString(getColumnIndex(DatabaseSchema.AdminTable.Cols.USERNAME));
        int pin = getInt(getColumnIndex(DatabaseSchema.AdminTable.Cols.PIN));

        return new Admin(username, pin,null);
    }
}
