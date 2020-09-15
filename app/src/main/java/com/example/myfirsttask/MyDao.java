package com.example.myfirsttask;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// интерфейс с описаем методов которые будут использоваться
// DAO - Data Access Object
@Dao
public interface MyDao {

    // onConflictStrategy.REPLACE нужен для того что бы не заносить
    // одни и те же данные повторно в бд. Если уже существующие данные
    // совпадают с теми что будут вноситься то и заменяются и не стакаются

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewBase newBase);


    @Query("DELETE FROM my_table" )
    void deleteAllDate();

    @Query("SELECT * FROM my_table")
    LiveData<List<NewBase>> getAllData();

}
