package com.example.myfirsttask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolder {

    // остальной url адрес после базового
    @GET("api/users/2")

    Call<Post> getPosts();
}
