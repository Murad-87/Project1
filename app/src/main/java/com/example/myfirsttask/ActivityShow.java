package com.example.myfirsttask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ActivityShow extends AppCompatActivity {

    MyViewModel myViewModel;
    private TextView tvText, tvText_2;
    private ImageView imageGlide;

    // добавляем эту строку когда код обозначен желтым цветом
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        tvText = findViewById(R.id.tvText);
        tvText_2 = findViewById(R.id.tvText_2);

        imageGlide = findViewById(R.id.image_view_glide);

        // объявляем и инициализируем viewModel для доступа к методу insert в базе данных
        // в качестве context указываем application
        // объявляем observer
        myViewModel = new ViewModelProvider
                (this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(MyViewModel.class);
        myViewModel.getAllData().observe(this, newBases -> {

            // достаем данные из бд через newBase
            if (!newBases.isEmpty()) {
                for (int i = 0, size = newBases.size(); i < size; i++) {
                    String url = newBases.get(i).getAvatar();

                    // формируем строку для отображения в TextView
                    tvText.setText("Id: " + newBases.get(i).getId() + "\n" + "Email: " + newBases.get(i).getEmail() + "\n" +
                            "First_name: " + newBases.get(i).getFirst_name() + "\n" + "Last_name: " + newBases.get(i).getLast_name() +
                            "\n" + "Avatar: " + "\n");
                    // Glide добавляется в build.gradle для отображения фото
                    Glide.with(ActivityShow.this).load(url).into(imageGlide);

                    // формируем строку для отображения в TextView
                    tvText_2.setText("Company: " + newBases.get(i).getCompany() + "\n" +
                            "Url: " + newBases.get(i).getUrl() + "\n" +
                            "Text: " + newBases.get(i).getText());
                }
            } else {
                tvText.setText("Database is empty");
            }
        });

        // добавление кнопки "назад" в actionbar
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
    }// onCreate

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }// onSupportNavigateUp

}
