package com.example.myfirsttask;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


// описание того что будет храниться в базе данных
// Entity: представляет таблицу в базе данных
@Entity (tableName = "my_table")
public class NewBase {

    // описание колонн базы
    //  Primary key нужно для указания начальной точки для бд
    @PrimaryKey(autoGenerate = true)
    private int idl;

    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
    private String company;
    private String url;
    private String text;


    public NewBase(int id, String email, String first_name, String last_name, String avatar, String company, String url, String text) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.company = company;
        this.url = url;
        this.text = text;
    }


    public int getIdl() {
        return idl;
    }

    public void setIdl(int idl) {
        this.idl = idl;
    }

    // getters чтобы получать данные из базы (для инкапсуляции)
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCompany() {
        return company;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
