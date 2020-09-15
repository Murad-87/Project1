package com.example.myfirsttask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyViewModel myViewModel;

    final static String LOG_TAG = "myLogs";

    Button btn_one, btn_two, btn_three;

    // Добавляем Retrofit
    Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_one = findViewById(R.id.btn_one);
        btn_one.setOnClickListener(this);

        btn_two = findViewById(R.id.btn_two);
        btn_two.setOnClickListener(this);

        btn_three = findViewById(R.id.btn_three);
        btn_three.setOnClickListener(this);


        myViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(MyViewModel.class);
        myViewModel.getAllData().observe(this, newBases -> {

        });


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        // обявляем наше второе Activity
        Intent intent;

        switch (view.getId()) {
            case R.id.btn_one:
                // инициализация и реализация Retrofit
                retrofit = new Retrofit.Builder()
                        // добавляем только базовый url
                        .baseUrl("https://reqres.in/")
                        // используем конвертер JSON объекта Moshi
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build();

                // переменная, которая необходима для получения проанализированных данных в форме JSON
                JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

                // переменная для вызова распарсенных данных JSON объекта
                Call<Post> call = jsonPlaceHolder.getPosts();

                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        // если запрос прошел неудачно
                        if (!response.isSuccessful()) {
                            return;
                        }

                        Post posts = response.body();
                        assert posts != null;

                        // заполняем строки для отправки в базу данных
                        int id = posts.data.getId();
                        String email = posts.data.getEmail();
                        String first_name = posts.data.getFirst_name();
                        String last_name = posts.data.getLast_name();
                        String avatar = posts.data.getAvatar();

                        String company = posts.ad.getCompany();
                        String url = posts.ad.getUrl();
                        String text = posts.ad.getText();

                        // указываем и отправляем строки в бд
                        NewBase newBase = new NewBase(id, email, first_name, last_name, avatar, company, url, text);
                        myViewModel.insert(newBase);

                    }
                    // в случае если произошла ошибка
                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
                break;
            case R.id.btn_two:
                myViewModel.deleteAllDate();
                break;

            case R.id.btn_three:
                // третья кнопка для перехода в наше ActivityShow
                intent = new Intent(this, ActivityShow.class);
                startActivity(intent);
                break;


        } // switch
    } // onClick
} // class



