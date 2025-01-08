package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GradeCalculator.db";
    private static final int DATABASE_VERSION = 2; 


    private static final String TABLE_MODULES = "modules";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COEFFICIENT = "coefficient";
    private static final String COLUMN_TEST = "test";
    private static final String COLUMN_PRACTICAL = "practical";
    private static final String COLUMN_PROJECT = "project";
    private static final String COLUMN_TEST_PERCENTAGE = "test_percentage";
    private static final String COLUMN_PRACTICAL_PERCENTAGE = "practical_percentage";
    private static final String COLUMN_PROJECT_PERCENTAGE = "project_percentage";
    private static final String COLUMN_AVERAGE = "average";

    private static final String CREATE_MODULES_TABLE = "CREATE TABLE " + TABLE_MODULES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_COEFFICIENT + " REAL,"
            + COLUMN_TEST + " REAL,"
            + COLUMN_PRACTICAL + " REAL,"
            + COLUMN_PROJECT + " REAL,"
            + COLUMN_TEST_PERCENTAGE + " REAL,"
            + COLUMN_PRACTICAL_PERCENTAGE + " REAL,"
            + COLUMN_PROJECT_PERCENTAGE + " REAL,"
            + COLUMN_AVERAGE + " REAL" + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MODULES_TABLE);
        Log.d("DBHelper", "Table is created " + TABLE_MODULES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULES);
        onCreate(db);
        Log.d("DBHelper", "Version has been updated " + oldVersion + " to " + newVersion);
    }

    
    public boolean addModule(String name, double coefficient, double test, double practical, double project,
                             double testPercentage, double practicalPercentage, double projectPercentage, double average) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.query(TABLE_MODULES,
                new String[]{COLUMN_NAME},
                COLUMN_NAME + " = ?",
                new String[]{name},
                null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            cursor.close();
            Log.e("DBHelper", "Module already exists: " + name);
            return false;
        }
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_COEFFICIENT, coefficient);
        values.put(COLUMN_TEST, test);
        values.put(COLUMN_PRACTICAL, practical);
        values.put(COLUMN_PROJECT, project);
        values.put(COLUMN_TEST_PERCENTAGE, testPercentage);
        values.put(COLUMN_PRACTICAL_PERCENTAGE, practicalPercentage);
        values.put(COLUMN_PROJECT_PERCENTAGE, projectPercentage);
        values.put(COLUMN_AVERAGE, average);

        long result = db.insert(TABLE_MODULES, null, values);

        if (result == -1) {
            Log.e("DBHelper", "Not added " + name);
            return false;
        } else {
            Log.d("DBHelper", "Modele added" + name);
            return true;
        }
    }


    public Cursor getAllModules() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MODULES, null);
    }

    public boolean deleteModule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MODULES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean updateModule(int id, double test, double practical, double project) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEST, test);
        values.put(COLUMN_PRACTICAL, practical);
        values.put(COLUMN_PROJECT, project);


        int result = db.update(TABLE_MODULES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}