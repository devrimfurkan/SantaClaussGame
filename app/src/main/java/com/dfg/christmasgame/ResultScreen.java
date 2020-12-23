package com.dfg.christmasgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {

    private TextView textViewTotalScore;
    private TextView textViewHighScore;
    private Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        textViewTotalScore=findViewById(R.id.textViewTotalScore);
        textViewHighScore=findViewById(R.id.textViewTotalScore2);
        playAgain=findViewById(R.id.button);

        int score= getIntent().getIntExtra("Score",0);
        textViewTotalScore.setText(String.valueOf(score));

        SharedPreferences sharedPreferences=getSharedPreferences("Sonuc",MODE_PRIVATE);
        int highScore=sharedPreferences.getInt("High Score",0);

        if (score>highScore){

            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putInt("High Score",score);
            editor.commit();

            textViewHighScore.setText(String.valueOf(score));
        }else {
            textViewHighScore.setText(String.valueOf(highScore));

        }

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultScreen.this,MainActivity.class);
                //finish();
                startActivity(intent);
            }
        });
    }
}