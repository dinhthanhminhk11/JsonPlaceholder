package com.example.jsonplaceholder.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonplaceholder.R;
import com.example.jsonplaceholder.api.ApiRequest;
import com.example.jsonplaceholder.model.Photo;
import com.example.jsonplaceholder.ui.adapter.PhotoAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoUsing1Activity extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private RecyclerView listPhoto;
    private PhotoAdapter photoAdapter;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_using1);
        toolBar = (MaterialToolbar) findViewById(R.id.toolBar);
        listPhoto = (RecyclerView) findViewById(R.id.listPhoto);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        photoAdapter = new PhotoAdapter();
        listPhoto.setAdapter(photoAdapter);

        initToolbar();
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiRequest apiRequest = retrofit.create(ApiRequest.class);
        Call<List<Photo>> call = apiRequest.getPhotos();
        call.enqueue(new retrofit2.Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<Photo> photos = response.body();
                    photoAdapter.setData(photos);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(PhotoUsing1Activity.this, "Call lỗi " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initToolbar() {
        toolBar.setTitle("Quản lí album(Retrofit)");
        toolBar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}