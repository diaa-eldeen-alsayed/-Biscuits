package com.example.task_typical_design;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.task_typical_design.product_contract.ProductyEntry;

import androidx.annotation.Nullable;

public class productDbhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 29;


    public productDbhelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       // "CREATE TABLE students(id integer primary key autoincrement, name text, faculty integer);"
        final String SQL_CREATE_PRODUCT_TABLE ="CREATE TABLE products(id integer primary key autoincrement , name text , price text ,image text ,q text );";
    sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS products;";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
        Log.i("tag=","new database");

    }
}
