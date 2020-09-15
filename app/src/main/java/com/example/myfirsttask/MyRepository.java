package com.example.myfirsttask;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

//класс, через который можно получить доступ к данным
// класс нужен для ассинхронного выполнения методов
// базы данных
public class MyRepository {

    private MyDao myDao;
    private LiveData<List<NewBase>> allData;

    public MyRepository (Application application) {
        MainBase mainBase = MainBase.getInstance(application);
        myDao = mainBase.myDao();
        // метод, созданный в интерфейсе DAO
        allData = myDao.getAllData();
    }

    //мы должны выполнять эти операции в AsyncTask, потому что
    // Room не позволяет выполнять метод в основном потоке
    public void insert(NewBase newBase) {
        new InsertDataAsyncTask(myDao).execute(newBase);
    }

    public void deleteAllDate() {
        new DeleteAllDataAsyncTask(myDao).execute();
    }

    public LiveData<List<NewBase>> getAllData() {
        return allData;
    }

    private static class InsertDataAsyncTask extends AsyncTask <NewBase, Void, Void> {
        private MyDao myDao;

        private InsertDataAsyncTask (MyDao myDao) {
            this.myDao = myDao;
        }

        @Override
        protected Void doInBackground(NewBase... newBases) {
            myDao.insert(newBases [0]);
            return null;
        }
    }

    private static class DeleteAllDataAsyncTask extends AsyncTask <Void, Void, Void> {
        private MyDao myDao;

        private DeleteAllDataAsyncTask (MyDao myDao) {
            this.myDao = myDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDao.deleteAllDate();
            return null;
        }
    }


}