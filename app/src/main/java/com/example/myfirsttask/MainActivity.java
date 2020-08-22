package com.example.myfirsttask;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String LOG_TAG = "myLogs";
    Button btnADD, btnRead, btnClear;

    DBHelper dbHelper;

    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnADD = (Button) findViewById(R.id.btnAdd);
        btnADD.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        mQueue = Volley.newRequestQueue(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                // ссылка по которой получаем JSON объект
                String url = "https://reqres.in/api/users/2";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                // объявляем элементы базы данных чтобы сохранить в ней распарсенный JSON object
                                 ContentValues cv = new ContentValues();
                                // подключаемся к БД
                                SQLiteDatabase database = dbHelper.getWritableDatabase();

                                // объявляем элементы базы данных чтобы сохранить в ней распарсенный JSON object
                                try {
                                    JSONObject data = response.getJSONObject("data");
                                    int id = data.getInt("id");
                                    String first_name = data.getString("first_name");
                                    String email = data.getString("email");
                                    String last_name = data.getString("last_name");
                                    String avatar = data.getString("avatar");

                                    JSONObject ad = response.getJSONObject("ad");
                                    String company = ad.getString("company");
                                    String url = ad.getString("url");
                                    String text = ad.getString("text");

                                    cv.put(DBHelper.ID, id);
                                    cv.put(DBHelper.FIRST_NAME, first_name);
                                    cv.put(DBHelper.EMAIL, email);
                                    cv.put(DBHelper.LAST_NAME, last_name);
                                    cv.put(DBHelper.AVATAR, avatar);

                                    cv.put(DBHelper.COMPANY, company);
                                    cv.put(DBHelper.URL, url);
                                    cv.put(DBHelper.TEXT, text);

                                    long rowID = database.insert(DBHelper.TABLE_NAME, null, cv);
                                    Log.d(LOG_TAG, "row inserted ID = " + rowID);
                                    dbHelper.close();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                mQueue.add(request);
                break;

            // открываем второе Activity
            case R.id.btnRead:
                Intent intent = new Intent(this, ActivitySecond.class);
                startActivity(intent);
                break;

            // по нажатию очищаем базу данных
            case R.id.btnClear:
                SQLiteDatabase database1 = dbHelper.getWritableDatabase();
                Log.d(LOG_TAG, "--- Clear myTable: ---");
                int clearCount = database1.delete(DBHelper.TABLE_NAME, null, null);
                Log.d(LOG_TAG, "delete rows count = " + clearCount);
                break;
        } // switch
        dbHelper.close();
    } // onClick
} // class
