package com.example.pracgrader.databases.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.classfiles.AppData;
import com.example.pracgrader.databases.DatabaseHelper;
import com.example.pracgrader.databases.cursor.AdminCursor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class AdminList {
    private AppData appData = AppData.getInstance();
    private List<Admin> admins = appData.getAdmins();
    private SQLiteDatabase pracGraderDb;

    public AdminList(){

    }

    public void loadAdmins(Context context)
    {
        if(pracGraderDb == null)
        {
            pracGraderDb = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        }
        AdminCursor adminCursor = new AdminCursor(pracGraderDb.query(DatabaseSchema.AdminTable.NAME,
                null,null,null,null,null,null));

        try{
            adminCursor.moveToFirst();
            while (!adminCursor.isAfterLast())
            {
                admins.add(adminCursor.getAdmin());
                adminCursor.moveToNext();
            }
        }
        finally {
            adminCursor.close();
        }
    }

    public int adminSize()
    {
        return admins.size();
    }

    public Admin getAdmin(int i)
    {
        return admins.get(i);
    }

    public int addAdmin(Admin newUser)
    {
        admins.add(newUser);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.AdminTable.Cols.USERNAME,newUser.getUsername());
        cv.put(DatabaseSchema.AdminTable.Cols.PIN,newUser.getPin());

        pracGraderDb.insert(DatabaseSchema.AdminTable.NAME,null,cv);


        return admins.size() -1;
    }

    public void editAdmin(Admin newUser)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.AdminTable.Cols.USERNAME,newUser.getUsername());
        cv.put(DatabaseSchema.AdminTable.Cols.PIN,newUser.getPin());

        String[] whereValue = { String.valueOf(newUser.getUsername()) };
        pracGraderDb.update(DatabaseSchema.AdminTable.NAME, cv, DatabaseSchema.AdminTable.Cols.USERNAME + " = ?", whereValue);
    }

}
