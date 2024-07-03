package com.example.productapiwithlazyloading;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<ProductModal> productList;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchProducts();


    }

    private void fetchProducts() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.escuelajs.co/api/v1/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create()).build();

        progressBar.setVisibility(View.VISIBLE);

        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        productAPI.getProduct().enqueue(new Callback<List<ProductModal>>() {
            @Override
            public void onResponse(Call<List<ProductModal>> call, Response<List<ProductModal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productList = response.body();
                    recyclerView.setAdapter(new ProductAdapter(MainActivity.this, productList));
                    progressBar.setVisibility(View.GONE);
                } else {
                    Log.e("API", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<ProductModal>> call, Throwable t) {
                Log.e("API", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}
