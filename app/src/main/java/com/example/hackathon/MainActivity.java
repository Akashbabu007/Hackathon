package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_INTERVAL = 2000;
    private long pressbacktwice ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //id for groceries image and vegetables image
        CardView groceries = findViewById(R.id.button_grocery);
        CardView vegetables = findViewById(R.id.button_vegetables);

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
//    @Override
//    public void onBackPressed() {
//
//
//        if (pressbacktwice + TIME_INTERVAL > System.currentTimeMillis())
//        {
//            super.onBackPressed();
//            Intent a = new Intent(Intent.ACTION_MAIN);
//            a.addCategory(Intent.CATEGORY_HOME);
//            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(a);
//            return;
//        }
//        else { Toast.makeText(getBaseContext(), "Tap back button again in order to exit", Toast.LENGTH_SHORT).show(); }
//
//        pressbacktwice = System.currentTimeMillis();
//    }


}
