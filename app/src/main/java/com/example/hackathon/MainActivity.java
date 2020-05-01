package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id for groceries image and vegetables image
        ImageView groceries = findViewById(R.id.groceries_image);
        ImageView vegetables = findViewById(R.id.vegetables_image);

        //intent for grociers image to groceries activity
        groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GroceriesActivity.class);
                startActivity(intent);
            }
        });

        //intent for vegetables image to vegetables activity
        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,VegetablesActivity.class);
                startActivity(intent);
            }
        });
    }
}
