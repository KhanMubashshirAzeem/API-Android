package com.example.productapiwithlazyloading;

public class CategoryModel {
    private int id;
    private String name;
    private String image;
    private String creationAt;
    private String updatedAt;

    // Empty constructor (Required for parsing with Firebase/Retrofit/GSON)
    public CategoryModel() {
    }

    public CategoryModel(int id, String name, String image, String creationAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.creationAt = creationAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreationAt() {
        return creationAt;
    }

    public void setCreationAt(String creationAt) {
        this.creationAt = creationAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
