package com.example.hackathon;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    private static final int TIME_INTERVAL = 2000;
    private long pressbacktwice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView tv = (TextView)findViewById(R.id.marquee1);
        tv.setSelected(true);
        tv.setSingleLine(true);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "font/lobsterregular.ttf");
        tv.setTypeface(face);
        //id for groceries image and vegetables image
        CardView groceries = findViewById(R.id.button_grocery);
        CardView vegetables = findViewById(R.id.button_vegetables);

        //intent for grociers image to groceries activity
        groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GroceriesActivity.class);
                startActivity(intent);
            }
        });

        //intent for vegetables image to vegetables activity
        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VegetablesActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
