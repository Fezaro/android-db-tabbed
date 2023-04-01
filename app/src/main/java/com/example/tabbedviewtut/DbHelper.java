package com.example.tabbedviewtut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "Student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table StudentDetails(firstName TEXT, LastName TEXT, id TEXT primary key, course TEXT, gender TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists StudentDetails");

    }

    public Boolean insertUserdata(String fname, String lname, String id, String course, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", fname);
        contentValues.put("lastName", lname);
        contentValues.put("id", id);
        contentValues.put("course", course);
        contentValues.put("gender", gender);
        long result = sqLiteDatabase.insert("StudentDetails", null, contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean updateUserdata(String fname, String lname, String id, String course, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", fname);
        contentValues.put("lastName", lname);
        contentValues.put("course", course);
        contentValues.put("gender", gender);

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from StudentDetails where id = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.update("StudentDetails", contentValues, "id=?", new String[]{id});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public Boolean deleteUserdata(String id ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from StudentDetails where id = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.delete("StudentDetails", "id=?", new String[]{id});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public Cursor getStudentData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from StudentDetails", null);
        return cursor;
    }
}
