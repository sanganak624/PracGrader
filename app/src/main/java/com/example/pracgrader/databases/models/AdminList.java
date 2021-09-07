package com.example.pracgrader.databases.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.pracgrader.classfiles.Admin;
import com.example.pracgrader.databases.DatabaseHelper;
import com.example.pracgrader.databases.cursor.AdminCursor;
import com.example.pracgrader.databases.schema.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;

public class AdminList {
    private List<Admin> admins = new ArrayList<>();
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
                Admin newAdmin = adminCursor.getAdmin();

                admins.add(newAdmin);
//
//                if(newUser.getRole()==1)
//                {
//                    // rs = pracGraderDb.query()
//                    //admins.add(newUser);
//                }
//                else if(newUser.getRole()==2)
//                {
//                    String query = "Select * from " + DatabaseSchema.InstructorTable.NAME + " where "
//                            + DatabaseSchema.InstructorTable.Cols.USERNAME + " = " + newUser.getUsername();
//                    InstructorCursor instructorCursor = new InstructorCursor(pracGraderDb.rawQuery(query,null));
//                    if(instructorCursor.getCount()==1)
//                    {
//                        Instructor tempInstructor = instructorCursor.getInstructor();
//                        instructors.add(new Instructor(newUser.getUsername(), newUser.getPin(), newUser.getRole(),
//                                tempInstructor.getEmail(),tempInstructor.getName(),tempInstructor.getCountry(),null));
//                    }
//                    instructorCursor.close();
//                }
//                else if (newUser.getRole()==3)
//                {
//                    String query = "Select * from " + DatabaseSchema.StudentTable.NAME + " where "
//                            + DatabaseSchema.StudentTable.Cols.USERNAME + " = " + newUser.getUsername();
//                    StudentCursor studentCursor = new StudentCursor(pracGraderDb.rawQuery(query,null));
//                    if(studentCursor.getCount()==1)
//                    {
//                        Student tempStudent = studentCursor.getStudent();
//                        instructors.add(new Instructor(newUser.getUsername(), newUser.getPin(), newUser.getRole(),
//                                tempStudent.getEmail(),tempStudent.getName(),tempStudent.getCountry(),null));
//                    }
//                    studentCursor.close();
//                }
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
