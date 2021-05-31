package com.example.q_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {


    private Animation  bottom, top ;
    private ImageView imageViewSplashLogo;
    private TextView textViewSplashAppName, textViewSplashAppContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        imageViewSplashLogo = findViewById(R.id.imageViewSplashLogo);
        textViewSplashAppName = findViewById(R.id.textViewSplashAppName);
        textViewSplashAppContent = findViewById(R.id.textViewSplashAppContent);


        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imageViewSplashLogo.setAnimation(top);
        textViewSplashAppContent.setAnimation(bottom);
        textViewSplashAppName.setAnimation(bottom);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.fade_out);
                finish();
            }
        }, 4000);
    }
}