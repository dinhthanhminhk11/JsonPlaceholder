package com.example.jsonplaceholder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jsonplaceholder.ui.activity.PhotoUsing1Activity;
import com.example.jsonplaceholder.ui.activity.PhotoUsing2Activity;

public class MainActivity extends AppCompatActivity {
    private Button using1;
    private Button using2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        using1 = (Button) findViewById(R.id.using1);
        using2 = (Button) findViewById(R.id.using2);

        using1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhotoUsing1Activity.class));
            }
        });

        using2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhotoUsing2Activity.class));
            }
        });
    }
}