package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id for groceries image and vegetables image
        Button groceries = findViewById(R.id.button_grocery);
        Button vegetables = findViewById(R.id.button_vegetables);

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
