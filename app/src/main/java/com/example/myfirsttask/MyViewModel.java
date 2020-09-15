package com.example.myfirsttask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// класс служит для смен конфигурации приложения
// для этого вместо context используется application
// чтобы при смене конфигурации данные не потерялись
// и не произошли никакие изменения
// и не было утечек памяти
// также класс служит как дополнительный слой абстракции
// между репозиторием и mainActivity
public class MyViewModel extends AndroidViewModel {

    private MyRepository myRepository;
    private LiveData<List<NewBase>> allData;

    public MyViewModel(@NonNull Application application) {
        super(application);
        myRepository = new MyRepository(application);
        allData = myRepository.getAllData();
    }

    public void insert(NewBase newBase) {
        myRepository.insert(newBase);
    }

    public void deleteAllDate() {
        myRepository.deleteAllDate();
    }

    public LiveData<List<NewBase>> getAllData() {
        return allData;
    }
}

