package com.roshanaryal.quizzerjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    public void playAgain(View view){
        finish();
        startActivity(new Intent(this,QuestionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView scoreText=findViewById(R.id.scoreText);
        scoreText.setText(getIntent().getStringExtra("score"));
    }
}
