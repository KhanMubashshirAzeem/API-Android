# Product API with Lazy Loading

This Android application fetches a list of products from a RESTful API and displays them using a RecyclerView.
## Features

- Fetch products from an API.
- Display products in a RecyclerView.
- Show a progress bar while loading data.
- Handle back button press with a confirmation toast.

## Connect with me.
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/khanmubashshir/)

## Dependencies

Add the following dependencies to your `build.gradle` file:

```groovy
dependencies {
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'androidx.recyclerview:recyclerview:1.4.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2'
    implementation 'com.github.bumptech.glide:glide:4.16.0'
}
