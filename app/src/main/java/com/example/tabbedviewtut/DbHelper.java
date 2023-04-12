package com.example.tabbedviewtut;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "university.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table StudentDetails(firstName TEXT NOT NULL, LastName TEXT NOT NULL, studId TEXT UNIQUE primary key, course TEXT NOT NULL, gender TEXT)");
        // create another table to store  units
        sqLiteDatabase.execSQL("create Table Units(unitId TEXT UNIQUE primary key, unitCode TEXT NOT NULL)");
        // create student_ units table to store student units
        sqLiteDatabase.execSQL("create Table Student_Units(unitId TEXT, studId TEXT, foreign key (unitId) references Units(unitId), foreign key (studId) references StudentDetails(studId))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists StudentDetails");
        sqLiteDatabase.execSQL("drop Table if exists Units");
        sqLiteDatabase.execSQL("drop Table if exists Student_Units");


    }

    public Boolean insertUserData(String fname, String lname, String id, String course, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", fname);
        contentValues.put("lastName", lname);
        contentValues.put("studId", id);
        contentValues.put("course", course);
        contentValues.put("gender", gender);
        try {

        long result = sqLiteDatabase.insert("StudentDetails", null, contentValues);
        // close db
        sqLiteDatabase.close();

        return result != -1;
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            return false;
        }
    }

    public Boolean updateUserData(String fname, String lname, String id, String course, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstName", fname);
        contentValues.put("lastName", lname);
        contentValues.put("course", course);
        contentValues.put("gender", gender);

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from StudentDetails where studId = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.update("StudentDetails", contentValues, "studId=?", new String[]{id});
            cursor.close();
            return result != -1;
        }else {
            cursor.close();
            return false;
        }

    }

    public Boolean deleteUserData(String id ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from StudentDetails where studId = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.delete("StudentDetails", "studId=?", new String[]{id});
            cursor.close();
            return result != -1;
        }else {
            cursor.close();
            return false;
        }
    }

    public Cursor getStudentsData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("Select * from StudentDetails", null);
    }

    // get details of a specific student using the student id
    public Cursor getStudentDetails(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("Select * from StudentDetails where studId = ?", new String[]{id});
        // check if db returns data
        Log.d("DB_CHECK_StudentDets", "Number of rows returned by the cursor: " + cursor.getCount());
        return cursor;

    }

    // insert unit data
    public Boolean insertUnitData(String unitId, String unitCode){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("unitId", unitId);
        contentValues.put("unitCode", unitCode);

        long result = sqLiteDatabase.insert("Units", null, contentValues);
        // close db
        sqLiteDatabase.close();


        return result != -1;
    }

    // update unit data
    public Boolean updateUnitsData(String unitId, String unitCode){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("unitCode", unitCode);


        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Units where unitId=?" , new String[]{unitId});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.update("Units", contentValues,  "unitId=?", new String[]{unitId});
            cursor.close();

            return result != -1;
        }else {
            cursor.close();
            return false;
        }
    }

    // delete unit data
    public Boolean deleteUnitsData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Units where unitId=?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.delete("Units", "unitId=?", new String[]{id});
            cursor.close();

            return result != -1;
        }else {
            cursor.close();

            return false;
        }

    }
    // check if student id exists
    public Boolean checkStudentId(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM StudentDetails WHERE studId=?", new String[]{id});
        int count = cursor.getCount();
        Log.d("DB_CHECK", "Number of rows returned by the cursor: " + count);
        cursor.close();
        return count > 0;
    }


    // get unit data
    public Cursor getUnitsData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("Select * from Units", null);
    }

    // add units to a specific student
    public void addStudentUnits(String studId, String unitId ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


            contentValues.put("studId", studId);
            contentValues.put("unitId", unitId);

            sqLiteDatabase.insert("Student_Units", null, contentValues);

        // close db
        sqLiteDatabase.close();

    }

    // delete student unit relationship
    public void deleteStudentUnit(String studId, String unitId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("Student_Units", "studId=? and unitId=?", new String[]{studId, unitId});
        sqLiteDatabase.close();

    }

    // get all units taken by a SPECIFIC STUDENT
    public List<String> getStudentUnits(String studId){
        // create list to store units
        List<String> unitsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("Select Units.unitCode from Student_Units JOIN Units ON Student_Units.unitId = Units.unitId WHERE Student_Units.studId=?", new String[]{studId});
            // get the cursor count
            int count = cursor.getCount();
            Log.d("DB_GET_STD_CHECK", "Number of rows returned by the cursor: " + count);
            // loop through the cursor and add the units to the list
            if (cursor.moveToFirst()) {
                do {
                    unitsList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            if (cursor.moveToFirst()) {
                do {
                    unitsList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
//        free cursor
            cursor.close();
        }catch (Exception e){
            Log.d("DB_GET_STD_CHECK2", e.getMessage());
        }
        return unitsList;
    }

    //


}
