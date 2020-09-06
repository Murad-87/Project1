package com.example.myfirsttask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // добавляем Moshi
    Moshi moshi;


    public static final String LOG_TAG = "myLogs";
    Button btnADD, btnRead, btnClear;

    DBHelper dbHelper;

    // JsonAdapter обязателен. Название класса произвольное.
    JsonAdapter<Gist> gistJsonAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnADD = findViewById(R.id.btnAdd);
        btnADD.setOnClickListener(this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);


        dbHelper = new DBHelper (this);

        moshi = new Moshi.Builder().build();

        gistJsonAdapter = moshi.adapter(Gist.class);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        Intent intent;
        // объявляем переменные OkHttp для дальнейшей работы с ними
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new Request.Builder()
                .url("https://reqres.in/api/users/2")
                .build();

        switch (v.getId()){
            case R.id.btnAdd:
                // клиент для запрос на JSON file
                client.newCall(request).enqueue(new Callback() {

                    // если запрос на JSON file не удался
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        // в случае неудачи вывести ошибку в Event Log
                        e.printStackTrace();
                    }

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        // объявляем элементы базы данных чтобы сохранить в ней распарсенный JSON object
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        // если запрос на JSON файл прошел успешно
                        if (response.isSuccessful()) {
                            // парсит JSON file
                            final Gist gist = gistJsonAdapter.fromJson(Objects.requireNonNull(response.body()).source());
                            assert gist != null;

                            contentValues.put(DBHelper.KEY_ID, gist.data.id);
                            contentValues.put(DBHelper.KEY_EMAIL, gist.data.email);
                            contentValues.put(DBHelper.KEY_FIRST_NAME, gist.data.first_name);
                            contentValues.put(DBHelper.KEY_LAST_NAME, gist.data.last_name);
                            contentValues.put(DBHelper.KEY_AVATAR, gist.data.avatar);

                            contentValues.put(DBHelper.KEY_COMPANY, gist.ad.company);
                            contentValues.put(DBHelper.KEY_URL, gist.ad.url);
                            contentValues.put(DBHelper.KEY_TEXT, gist.ad.text);

                        }

                        long rowID = database.insert(DBHelper.THIS_TABLE, null, contentValues);
                        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                        dbHelper.close();
                    }
                });

                break;

            // открываем второе Activity
            case R.id.btnRead:
                intent = new Intent(this, ActivitySecond.class);
                startActivity(intent);
                break;

            // по нажатию очищаем базу данных
            case R.id.btnClear:
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Log.d(LOG_TAG, "--- Clear myTable: ---");
                int clearCount = database.delete(DBHelper.THIS_TABLE, null, null);
                Log.d(LOG_TAG, "delete rows count = " + clearCount);
                break;
        } // switch
        //dbHelper.close();
    } // onClick
} // class



