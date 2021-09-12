package com.example.pracgrader.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pracgrader.databases.schema.DatabaseSchema;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "pracGrader.db";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + DatabaseSchema.AdminTable.NAME + "(" +
                DatabaseSchema.AdminTable.Cols.USERNAME + " TEXT, " +
                DatabaseSchema.AdminTable.Cols.PIN + " INTEGER)");

        db.execSQL("create table " + DatabaseSchema.InstructorTable.NAME + "(" +
                DatabaseSchema.InstructorTable.Cols.USERNAME + " TEXT, " +
                DatabaseSchema.AdminTable.Cols.PIN + " INTEGER, " +
                DatabaseSchema.InstructorTable.Cols.NAME + " TEXT, " +
                DatabaseSchema.InstructorTable.Cols.EMAIL + " TEXT, " +
                DatabaseSchema.InstructorTable.Cols.COUNTRY + " INTEGER)");

        db.execSQL("create table " + DatabaseSchema.MarksTable.NAME + "(" +
                DatabaseSchema.MarksTable.Cols.USERNAME + " TEXT, " +
                DatabaseSchema.MarksTable.Cols.TITLE + " TEXT, " +
                DatabaseSchema.MarksTable.Cols.MARK + " REAL)");

        db.execSQL("create table " + DatabaseSchema.PracTable.NAME + "(" +
                DatabaseSchema.PracTable.Cols.TITLE + " TEXT, " +
                DatabaseSchema.PracTable.Cols.MAXMARK + " REAL, " +
                DatabaseSchema.PracTable.Cols.HASMARK + " REAL, " +
                DatabaseSchema.PracTable.Cols.DIS + " TEXT)");

        db.execSQL("create table " + DatabaseSchema.StudentTable.NAME + "(" +
                DatabaseSchema.StudentTable.Cols.USERNAME + " TEXT, " +
                DatabaseSchema.AdminTable.Cols.PIN + " INTEGER, " +
                DatabaseSchema.StudentTable.Cols.NAME + " TEXT, " +
                DatabaseSchema.StudentTable.Cols.EMAIL + " TEXT, " +
                DatabaseSchema.StudentTable.Cols.COUNTRY + " INTEGER, " +
                DatabaseSchema.StudentTable.Cols.CREATOR + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
