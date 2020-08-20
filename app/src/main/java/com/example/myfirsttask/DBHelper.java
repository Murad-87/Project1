package com.example.myfirsttask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "myDB";
    private static final int VERSION_2 = 2;

    private static final String TABLE_NAME = "myTable";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String AVATAR = "avatar";
    private static final String COMPANY = "company";
    private static final String URL = "url";
    private static final String TEXT = "text";


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
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);

    }

}
