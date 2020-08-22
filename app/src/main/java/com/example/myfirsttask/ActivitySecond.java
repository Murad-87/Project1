package com.example.myfirsttask;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


public class ActivitySecond extends AppCompatActivity {

    TextView tvText, tvText_2;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        DBHelper dbHelper;
        dbHelper = new DBHelper(this);

        tvText = findViewById(R.id.tvText);
        tvText_2 = findViewById(R.id.tvText_2);
        imageView = findViewById(R.id.imageView);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int id_Index = cursor.getColumnIndex(DBHelper.ID);
            int email_Index = cursor.getColumnIndex(DBHelper.EMAIL);
            int first_name_Index = cursor.getColumnIndex(DBHelper.FIRST_NAME);
            int last_name_Index = cursor.getColumnIndex(DBHelper.LAST_NAME);
            int avatar_Index = cursor.getColumnIndex(DBHelper.AVATAR);
            int company_Index = cursor.getColumnIndex(DBHelper.COMPANY);
            int url_Index = cursor.getColumnIndex(DBHelper.URL);
            int text_Index = cursor.getColumnIndex(DBHelper.TEXT);
            String url = cursor.getString(avatar_Index);

            do {
                Log.d(MainActivity.LOG_TAG, cursor.getString(id_Index) + cursor.getString(email_Index) + cursor.getString(first_name_Index) +
                        cursor.getString(last_name_Index) + cursor.getString(avatar_Index) + cursor.getString(company_Index) +
                        cursor.getString(url_Index) + cursor.getString(text_Index));
                // собираем строку для отображение данных из базы
                String sent = "id: " + cursor.getString(id_Index) + "\n" + "Email: " + cursor.getString(email_Index) + "\n" +
                        "First name: " + cursor.getString(first_name_Index) + "\n" + "Last name: " + cursor.getString(last_name_Index) +
                        "\n" + "Avatar: " + cursor.getString(avatar_Index) + "\n";
                tvText.setText(sent);
                Glide.with(this).load(url).into(imageView);
                String sent2 = "Company: " + cursor.getString(company_Index) + "\n" + "Url: " + cursor.getString(url_Index) +
                        "\n" + "Text: " + cursor.getString(text_Index) + "\n";
                tvText_2.setText(sent2);
            } while (cursor.moveToNext());
        }
        // если в таблице нет данных
        else {
            Log.d(MainActivity.LOG_TAG, "0 rows");
            String diff = "Database is empty";
            tvText.setText(diff);
        }
        cursor.close();
    }
}
