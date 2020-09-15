package com.example.myfirsttask;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewBase.class}, version = 8)
public abstract class MainBase extends RoomDatabase {

    private static MainBase instance;

    // Room auto генерирует весь необходимый код для этого метода
    public abstract MyDao myDao();

    // создает базу данных если таковой ещё нет
    // synchronized нужен для того чтобы метод нельзя
    // было вызывать в разных потоках.
    // только один поток одновременно может вызывать этот метод
    public static synchronized MainBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MainBase.class, "my_table")
                    // метод fallbackToDestructiveMigration удалят базу данных
                    // при внесении в неё каких либо изменений и создает её занова
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
