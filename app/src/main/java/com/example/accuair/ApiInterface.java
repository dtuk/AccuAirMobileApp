package com.example.accuair;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("register.php")
    Call<User> performRegistration(@Query("name") String name, @Query("mail") String mail, @Query("pass") String pass, @Query("cpass") String cpass);

    @GET("login.php")
    Call<User> performUserLogin(@Query("name") String name, @Query("pass") String pass);



}
