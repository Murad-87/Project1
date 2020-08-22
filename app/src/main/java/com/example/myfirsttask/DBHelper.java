package com.example.myfirsttask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "myDB";
    public static final int VERSION_2 = 2;

    public static final String TABLE_NAME = "myTable";
    public static final String ID = "id";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String AVATAR = "avatar";
    public static final String COMPANY = "company";
    public static final String URL = "url";
    public static final String TEXT = "text";


    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, VERSION_2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +
                "("
                + ID + " PRIMARY KEY,"
                + EMAIL + " TEXT,"
                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + AVATAR + " TEXT,"
                + COMPANY + " TEXT,"
                + URL + " TEXT,"
                + TEXT + " TEXT"
                + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);

    }

}
