package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnAddModule, btnViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddModule = findViewById(R.id.btnAddModule);
        btnViewResults = findViewById(R.id.btnViewResults);

        btnAddModule.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddModuleActivity.class);
            startActivity(intent);
        });

        btnViewResults.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
        });
    }
}