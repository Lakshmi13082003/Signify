package com.example.imagepro.TEST;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.example.imagepro.R;

public class TestHome extends AppCompatActivity {

    private Button level1Button, level2Button, level3Button;
    private Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_home);

        level1Button = findViewById(R.id.level1Button);
        level2Button = findViewById(R.id.level2Button);
        level3Button = findViewById(R.id.level3Button);

        // Load animations
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        // Set onClickListeners with animations
        level1Button.setOnClickListener(v -> {
            v.startAnimation(scaleUp);
            v.startAnimation(scaleDown);
            startLevel(1);
        });

        level2Button.setOnClickListener(v -> {
            v.startAnimation(scaleUp);
            v.startAnimation(scaleDown);
            startLevel(2);
        });

        level3Button.setOnClickListener(v -> {
            v.startAnimation(scaleUp);
            v.startAnimation(scaleDown);
            startLevel(3);
        });

        // Unlock levels based on user progress
        unlockLevels();
    }

    private void startLevel(int level) {
        Intent intent = new Intent(TestHome.this, LevelActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    private void unlockLevels() {
        // Example logic to unlock levels based on progress
        if (isLevelCompleted(1)) {
            level2Button.setEnabled(true);
        }
        if (isLevelCompleted(2)) {
            level3Button.setEnabled(true);
        }
    }

    private boolean isLevelCompleted(int level) {
        // Implement logic to check if the level is completed
        // You could use SharedPreferences to store the completion status
        return true; // Placeholder, replace with actual logic
    }
}
