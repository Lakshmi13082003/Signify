package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button more_info_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        View button = findViewById(R.id.more_info_button);
        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", 0f, 10f, 0f);
        animator.setDuration(1000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();


        more_info_button = findViewById(R.id.more_info_button);

//        more_info_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, SignLanguageDetailActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}