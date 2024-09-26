package com.example.productapiwithlazyloading;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    static String api = "https://api.escuelajs.co/api/v1/products/";


    // Lazy loading for Retrofit object
    private static Retrofit retrofit = null;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static ProductAPI getApiService() {
        return getRetrofitInstance().create(ProductAPI.class);
    }

}
