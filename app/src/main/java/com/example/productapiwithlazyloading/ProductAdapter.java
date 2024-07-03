package com.example.productapiwithlazyloading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductModal> productList;
    private Context context;

    public ProductAdapter(Context context, List<ProductModal> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_list, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModal product = productList.get(position);
        holder.titleTextView.setText(product.getTitle());
        holder.priceTextView.setText(String.valueOf(product.getPrice()));
        holder.descriptionTextView.setText(product.getDescription());
        holder.creationDateTextView.setText(product.getCreationAt());
        holder.categoryTextView.setText(product.getCategory().getName());

        // Load the first image from the images list (if available)
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            String imageUrl = product.getImages().get(0); // Assuming first image
            Glide.with(context).load(imageUrl).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, priceTextView, descriptionTextView, creationDateTextView, categoryTextView;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.product_title);
            priceTextView = itemView.findViewById(R.id.product_price);
            descriptionTextView = itemView.findViewById(R.id.product_description);
            creationDateTextView = itemView.findViewById(R.id.product_creation_date);
            categoryTextView = itemView.findViewById(R.id.product_category);
            imageView = itemView.findViewById(R.id.product_image);
        }
    }

}
