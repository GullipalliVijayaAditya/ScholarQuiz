package com.example.adity.scholarquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adity.scholarquiz.Common.Common;
import com.example.adity.scholarquiz.Model.QuestionScore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtResultScore, getTxtResultQuestion;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference question_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_score");
        txtResultScore = (TextView) findViewById(R.id.txtTotalScore);
        getTxtResultQuestion = (TextView) findViewById(R.id.txtTotalQuestion);
        progressBar = (ProgressBar) findViewById(R.id.doneProgressBar);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Done.this, Home.class);
                startActivity(intent);
                finish();

            }
        });
//Get data from bundle and set view
        Bundle extra = getIntent().getExtras();
        if (extra != null)

        {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            txtResultScore.setText(String.format("SCORE : %d", score));
            getTxtResultQuestion.setText(String.format("PASSED : %d/ %d", correctAnswer, totalQuestion));
            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

//Upload point to DB
            question_score.child(String.format("%s_%s", Common.currentUser.getSlackId(),
                    Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getSlackId(),
                            Common.categoryId),
                            Common.currentUser.getSlackId(),
                            String.valueOf(score),
                            Common.categoryId,
                            Common.categoryName));
        }
    }
}
