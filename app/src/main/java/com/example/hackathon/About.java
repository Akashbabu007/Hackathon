package com.example.hackathon;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        Toolbar about = (Toolbar) findViewById(R.id.about_toolbars);
        about.setTitle("About");
        about.setTitleTextColor(Color.WHITE);
        about.setBackgroundColor(Color.parseColor("#1F5BF3"));

    }
}
