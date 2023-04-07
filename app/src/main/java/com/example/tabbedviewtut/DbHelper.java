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
        sqLiteDatabase.execSQL("create Table Units(unitCode TEXT NOT NULL,unitId TEXT UNIQUE primary key)");
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

        long result = sqLiteDatabase.insert("StudentDetails", null, contentValues);
        // close db
        sqLiteDatabase.close();

        return result != -1;
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
            cursor.close();
            return result != -1;
        }else {
            cursor.close();
            return false;
        }

    }

    public Boolean deleteUserData(String id ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from StudentDetails where id = ?", new String[]{id});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.delete("StudentDetails", "id=?", new String[]{id});
            cursor.close();
            return result != -1;
        }else {
            cursor.close();
            return false;
        }
    }

    public Cursor getStudentData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("Select * from StudentDetails", null);
    }

    // insert unit data
    public Boolean insertUnitData(String unitCode, String unitId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("unitCode", unitCode);
        contentValues.put("unitId", unitId);

        long result = sqLiteDatabase.insert("Units", null, contentValues);
        // close db
        sqLiteDatabase.close();


        return result != -1;
    }

    // update unit data
    public Boolean updateUnitsData(String unitCode, String unitId){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("unitCode", unitCode);


        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Units where unitId=?" , new String[]{unitId});

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.update("Units", contentValues, null, null);
            cursor.close();

            return result != -1;
        }else {
            cursor.close();
            return false;
        }
    }

    // delete unit data
    public Boolean deleteUnitsData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from Units where UnitId=?", null);

        if(cursor.getCount() > 0){
            long result = sqLiteDatabase.delete("Units", null, null);
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
        Cursor cursor = sqLiteDatabase.rawQuery("Select Units.unitId from Units JOIN Student_Units ON Units.unitId = Student_Units.unitId WHERE Student_Units.studId=?", new String[]{studId});
        // get the cursor count
        int count = cursor.getCount();
        Log.d("DB_GET_STD_CHECK", "Number of rows returned by the cursor: " + count);
        // loop through the cursor and add the units to the list
        if(cursor.moveToFirst()){
            do{
                unitsList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
//        free cursor
        cursor.close();

        return unitsList;
    }

    //


}
