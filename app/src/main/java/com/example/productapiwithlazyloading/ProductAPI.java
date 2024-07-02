package com.example.productapiwithlazyloading;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ProductAPI {

    @GET("products")
    Call<List<ProductModal>> getProduct();

}
