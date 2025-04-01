package com.example.imagepro.TEST;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.imagepro.R;

import java.util.List;

public class LevelActivity extends AppCompatActivity {

    private TextView questionTextView;
    private ImageView image1, image2, image3, image4;
    private ProgressBar progressBar;
    private TextView timerTextView;

    private int timeLimit;
    private CountDownTimer countDownTimer;
    private int currentQuestionIndex = 0;

    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        questionTextView = findViewById(R.id.questionTextView);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        progressBar = findViewById(R.id.progressBar);
        timerTextView = findViewById(R.id.timerTextView);

        int level = getIntent().getIntExtra("level", 1);
        setLevelQuestions(level);

        setAnswerClickListeners();
    }

    private void setLevelQuestions(int level) {
        switch (level) {
            case 1:
                timeLimit = 30000;
                questions = QuestionData.getLevelOneQuestions();
                break;
            case 2:
                timeLimit = 20000;
                questions = QuestionData.getLevelTwoQuestions();
                break;
            case 3:
                timeLimit = 10000;
                questions = QuestionData.getLevelThreeQuestions();
                break;
        }
        startTimer(timeLimit);
        loadQuestion();
    }

    private void startTimer(int duration) {
        progressBar.setMax(duration / 1000);
        progressBar.setProgress(duration / 1000);

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                progressBar.setProgress(secondsLeft);
                timerTextView.setText(secondsLeft + "s");
            }

            @Override
            public void onFinish() {
                loadNextQuestion();
            }
        }.start();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionTextView.setText(currentQuestion.getQuestionText());
            loadImagesAsync(currentQuestion.getImageAnswer1(), image1);
            loadImagesAsync(currentQuestion.getImageAnswer2(), image2);
            loadImagesAsync(currentQuestion.getImageAnswer3(), image3);
            loadImagesAsync(currentQuestion.getImageAnswer4(), image4);
        } else {
            finishLevel();
        }
    }

    private void loadImagesAsync(int imageResId, ImageView imageView) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                return BitmapFactory.decodeResource(getResources(), imageResId);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        }.execute();
    }

    private void loadNextQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            startTimer(timeLimit);
            loadQuestion();
        } else {
            finishLevel();
        }
    }


    private int score = 0;

    private void setAnswerClickListeners() {
        View.OnClickListener answerClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                checkAnswer(view);
                loadNextQuestion();
            }
        };

        image1.setOnClickListener(answerClickListener);
        image2.setOnClickListener(answerClickListener);
        image3.setOnClickListener(answerClickListener);
        image4.setOnClickListener(answerClickListener);
    }

    private void checkAnswer(View selectedImage) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int selectedAnswer = 0;

        if (selectedImage == image1) {
            selectedAnswer = currentQuestion.getImageAnswer1();
        } else if (selectedImage == image2) {
            selectedAnswer = currentQuestion.getImageAnswer2();
        } else if (selectedImage == image3) {
            selectedAnswer = currentQuestion.getImageAnswer3();
        } else if (selectedImage == image4) {
            selectedAnswer = currentQuestion.getImageAnswer4();
        }

        if (selectedAnswer == currentQuestion.getCorrectAnswer()) {
            score++;
        }
    }


    private void finishLevel() {
        Intent intent = new Intent(LevelActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questions.size());
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
