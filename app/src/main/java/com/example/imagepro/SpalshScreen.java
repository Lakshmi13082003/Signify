package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;



public class SpalshScreen extends AppCompatActivity {

    TextView appName;
    LottieAnimationView lottie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        appName= findViewById(R.id.appname);
//        lottie=findViewById(R.id.lottie);
//
//            appName.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
//            lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//               Intent in = new Intent(getApplicationContext(),MainActivity.class);
//               startActivity(in);
//            }
//        }, 5000);


    }
}