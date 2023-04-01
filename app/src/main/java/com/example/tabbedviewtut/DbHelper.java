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
        // create another table to store 5 units with foreignkey referencing student id
        sqLiteDatabase.execSQL("create Table Units(unit1 TEXT, unit2 TEXT, unit3 TEXT, unit4 TEXT, unit5 TEXT)");





    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists StudentDetails");
        sqLiteDatabase.execSQL("drop Table if exists Units");


    }

    public Boolean insertUserData(String fname, String lname, String id, String course, String gender){
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

    public Boolean updateUserData(String fname, String lname, String id, String course, String gender){
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

    public Boolean deleteUserData(String id ){
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

    // insert unit data
    public Boolean insertUnitsData(String unit1, String unit2, String unit3, String unit4, String unit5){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("unit1", unit1);
        contentValues.put("unit2", unit2);
        contentValues.put("unit3", unit3);
        contentValues.put("unit4", unit4);
        contentValues.put("unit5", unit5);
        long result = sqLiteDatabase.insert("Units", null, contentValues);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    // update unit data
    public Boolean updateUnitsData(String unit1, String unit2, String unit3, String unit4, String unit5){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("unit1", unit1);
        contentValues.put("unit2", unit2);
        contentValues.put("unit3", unit3);
        contentValues.put("unit4", unit4);
        contentValues.put("unit5", unit5);

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Units", null);

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.update("Units", contentValues, null, null);

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    // delete unit data
    public Boolean deleteUnitsData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Units", null);

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.delete("Units", null, null);

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    // get unit data
    public Cursor getUnitsData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Units", null);
        return cursor;
    }

}
