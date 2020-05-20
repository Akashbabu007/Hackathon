package com.example.hackathon;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        Toolbar about = (Toolbar) findViewById(R.id.about_toolbars);
        about.setTitle("About");
        about.setTitleTextColor(Color.WHITE);
        about.setBackgroundColor(Color.parseColor("#1F5BF3"));
        AboutPage aboutPage = new AboutPage(getApplicationContext())
                .isRTL(false)
                .setImage(R.mipmap.bookmystore)
                .setDescription(getString(R.string.about_us_description))
                .addItem(new Element("Version " + BuildConfig.VERSION_NAME, R.drawable.ic_info))
                .addGroup("Connect with us")
                .addFacebook(getString(R.string.FACEBOOK_ID))
                .addEmail(getString(R.string.GMAIL_ID));
        //LinearLayout parentLayout = (LinearLayout)findViewById(R.id.parent);
        View aboutPageView = aboutPage.create();
       // parentLayout.addView(aboutPageView);
        setContentView(aboutPageView);

    }
}
