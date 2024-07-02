package com.example.productapiwithlazyloading;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    String api = "https://api.escuelajs.co/api/v1/products/";

    public static RetrofitInstance instance;
    ProductAPI productAPI;

    RetrofitInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productAPI = retrofit.create(ProductAPI.class);

    }

    public static RetrofitInstance getInstance() {
        if (instance == null) {
            instance = new RetrofitInstance();
        }
        return instance;
    }

}
