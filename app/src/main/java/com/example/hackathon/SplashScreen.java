package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    Animation rotateAnimation;
    Animation fromtop;
    TextView text;
    ImageView iconSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        RotateAnimation animation = new RotateAnimation(0f, 360f);
//        animation.setRepeatCount(Animation.ABSOLUTE);
//        animation.setDuration(2000);
//
//        ImageView imageView=  findViewById(R.id.imageView3);
//        imageView.startAnimation(animation);

//        iconSplash = findViewById(R.id.imageView3);
        text = findViewById(R.id.textView);

        fromtop = AnimationUtils.loadAnimation(this,R.anim.fromtopscreen);
        text.setAnimation(fromtop);
//        rotateAnimation();

        LogoLauncher logolauncher = new LogoLauncher();
        logolauncher.start();
    }

//    private void rotateAnimation() {
//        rotateAnimation = AnimationUtils.loadAnimation(this,R.anim.rotate);
//        iconSplash.startAnimation(rotateAnimation);
//    }

    private class LogoLauncher extends Thread {
        public void run() {
            try {
                int SLEEP_TIMER = 5;
                sleep(1000 * SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }
}
