package com.example.imagepro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.imagepro.ADMIN.AdminLoginPage;
import com.example.imagepro.NEARME.NearmeActivity;
import com.example.imagepro.TEST.TestHome;
import com.example.imagepro.TEST.UploadDescription;

import org.opencv.android.OpenCVLoader;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }

    //private CardView camera_button;
    private CardView combine_letter_button;
    private CardView take_test_button;
    private CardView learn_button;
    private CardView nearby_button;

    TextView goToAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                                                                                




        //camera_button=findViewById(R.id.camera_button);
        combine_letter_button=findViewById(R.id.combine_button);
        take_test_button=findViewById(R.id.take_test_button);
        learn_button=findViewById(R.id.learn_button);
        nearby_button=findViewById(R.id.nearby_button);
        goToAdmin=findViewById(R.id.goToAdminPage);




        combine_letter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CombineLetterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });

        take_test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(), TestHome.class));

            }
        });

        goToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminLoginPage.class));
            }
        });


        learn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LearnActivity.class));
            }
        });

        nearby_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NearmeActivity.class));
            }
        });







    }
}