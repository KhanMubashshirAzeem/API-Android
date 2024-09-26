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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<ProductModal> productList;  // Holds the list of products fetched from the API
    private List<CategoryModel> categoryList;  // Holds the list of categories fetched from the API
    private RecyclerView recyclerView, categoryRecyclerView;  // RecyclerViews for products and categories
    private ProgressBar progressBar;  // Progress bar to show while loading data
    private long pressedTime;  // Used to handle back button press timing for exiting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge UI for this activity
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle system insets (like the status bar) and apply padding to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        recyclerView = findViewById(R.id.recycler_view);
        categoryRecyclerView = findViewById(R.id.recyclerViewCategory);
        progressBar = findViewById(R.id.progress_bar);

        // Set layout manager for product list RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set horizontal layout manager for category RecyclerView
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        categoryRecyclerView.setLayoutManager(categoryLayoutManager);

        // Fetch products and categories from API
        fetchProducts();
        fetchCategories();
    }

    // Method to fetch products from the API
    private void fetchProducts() {
        // Create Retrofit instance with base URL and Gson converter for JSON parsing
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.escuelajs.co/api/v1/")  // Base URL of the API
                .addConverterFactory(GsonConverterFactory.create())  // Use Gson to parse JSON
                .build();

        progressBar.setVisibility(View.VISIBLE);  // Show progress bar while data is loading

        // Create API interface
        ProductAPI productAPI = retrofit.create(ProductAPI.class);

        // Enqueue an asynchronous request to fetch the product list
        productAPI.getProduct().enqueue(new Callback<List<ProductModal>>() {
            @Override
            public void onResponse(Call<List<ProductModal>> call, Response<List<ProductModal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // If the response is successful and contains data
                    productList = response.body();
                    recyclerView.setAdapter(new ProductAdapter(MainActivity.this, productList));  // Set adapter for the product RecyclerView
                    progressBar.setVisibility(View.GONE);  // Hide progress bar
                } else {
                    Log.e("API", "Response not successful or body is null");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ProductModal>> call, Throwable t) {
                // Log the error message in case of failure
                Log.e("API", "onFailure: " + t.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // Method to fetch categories from the API
    private void fetchCategories() {
        // Create Retrofit instance with base URL and Gson converter for JSON parsing
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.escuelajs.co/api/v1/")  // Base URL of the API
                .addConverterFactory(GsonConverterFactory.create())  // Use Gson to parse JSON
                .build();

        progressBar.setVisibility(View.VISIBLE);  // Show progress bar while data is loading

        // Create API interface for Category also
        ProductAPI productAPI = retrofit.create(ProductAPI.class);

        // Enqueue an asynchronous request to fetch the category list
        productAPI.getCategory().enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // If the response is successful and contains data
                    categoryList = response.body();
                    categoryRecyclerView.setAdapter(new CategoryAdapter(MainActivity.this, categoryList));  // Set adapter for the category RecyclerView
                } else {
                    Log.e("API", "Category response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                // Log the error message in case of failure
                Log.e("API", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Handle back button press to exit the app with a double-tap mechanism
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();  // Exit the app
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

}
