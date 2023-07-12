package com.example.jsonplaceholder.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonplaceholder.R;
import com.example.jsonplaceholder.model.Photo;
import com.example.jsonplaceholder.ui.adapter.PhotoAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotoUsing2Activity extends AppCompatActivity {
    private MaterialToolbar toolBar;
    private RecyclerView listPhoto;
    private ProgressBar progressBar;
    private PhotoAdapter photoAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_using2);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/photos");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        List<Photo> photos = parseJsonResponse(response.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                photoAdapter.setData(photos);
                            }
                        });
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initToolbar() {
        toolBar.setTitle("Quản lí album(HttpURLConnection)");
        toolBar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private List<Photo> parseJsonResponse(String jsonResponse) {
        List<Photo> photos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int albumId = jsonObject.getInt("albumId");
                String title = jsonObject.getString("title");
                String url = jsonObject.getString("url");
                String thumbnailUrl = jsonObject.getString("thumbnailUrl");

                Photo photo = new Photo(id, albumId, title, url, thumbnailUrl);
                photos.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photos;
    }
}