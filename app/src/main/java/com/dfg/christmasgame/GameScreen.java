package com.dfg.christmasgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity {

    private ConstraintLayout cl;
    private TextView textViewScore;
    private TextView startGame;
    private ImageView enemy;
    private ImageView candy;
    private ImageView dad;
    private ImageView hat;
    int dadX;
    int dadY;

    private int grapicWidth;
    private int grapicHeight;

    private int dadWidth;
    private int dadHeight;

    int enemyX;
    int enemyY;
    int candX;
    int candY;
    int hatX;
    int hatY;

    boolean touchControl = false;
    boolean startControl = false;

    Timer timer = new Timer();
    Handler handler = new Handler();

    private int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        textViewScore = findViewById(R.id.textViewScore);
        startGame = findViewById(R.id.textViewStartGame);

        enemy = findViewById(R.id.enemy);
        candy = findViewById(R.id.candy);
        dad = findViewById(R.id.mainCharacter);
        hat = findViewById(R.id.hat);

        cl = findViewById(R.id.cl);

        enemy.setX(-90);
        candy.setX(-90);
        hat.setX(  -90);
        enemy.setY(-90);
        candy.setY(-90);
        hat.setY(  -90);


        cl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (startControl) {
                    if (event.getAction() == event.ACTION_DOWN) {
                        touchControl = true;
                    }
                    if (event.getAction() == event.ACTION_UP) {
                        touchControl = false;
                    }
                } else {
                    startControl = true;

                    startGame.setVisibility(View.INVISIBLE);
                    dadX = (int) dad.getX();
                    dadY = (int) dad.getY();

                    dadWidth = dad.getWidth();
                    dadHeight = dad.getHeight();

                    grapicWidth = cl.getWidth();
                    grapicHeight = cl.getHeight();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    move();
                                    moveToItem();
                                    crashControl();
                                }
                            });
                        }
                    }, 0, 20);
                }

                return true;
            }
        });
    }

    public void move() {
        if (touchControl) {
            dadY -= 20;
        } else {
            dadY += 20;
        }
        if (dadY <= 0) {
            dadY = 0;
        }
        if (dadY >= grapicHeight - dadHeight) {
            dadY = grapicHeight - dadHeight;
        }
        dad.setY(dadY);
    }
    public void moveToItem(){

        enemyX-=20;
        if (enemyX<0){
            enemyX=grapicWidth+20;
            enemyY=(int) Math.floor(Math.random()*(grapicHeight));
        }
        enemy.setX(enemyX);
        enemy.setY(enemyY);
//
      hatX-=15;
      if (hatX<0){
          hatX=grapicWidth+20;
          hatY=(int) Math.floor(Math.random()*(grapicHeight));
      }
      hat.setX(hatX);
      hat.setY(hatY);
//
      candX-=25;
      if (candX<0){
          candX=grapicWidth+20;
          candY=(int) Math.floor(Math.random()*(grapicHeight));
      }
      candy.setX(candX);
      candy.setY(candY);
    }

    public void crashControl(){

        int hatRadiusX=hatX+hat.getWidth()/2;
        int hatRadiusY=hatY+hat.getHeight()/2;

        if (0<=hatRadiusX && hatRadiusX<=dadWidth && dadY<=hatRadiusY && hatRadiusY<= dadY+dadHeight){
            score+=20;
            hatX=-10;
        }
        //
        int enemyRadiusX=enemyX+enemy.getWidth()/2;
        int enemyRadiusY=enemyY+enemy.getHeight()/2;

        if (0<=enemyRadiusX && enemyRadiusX<=dadWidth && dadY<=enemyRadiusY && enemyRadiusY<= dadY+dadHeight){

            enemyX=-10;
            timer.cancel();
            timer=null;

            Intent intent=new Intent(GameScreen.this,ResultScreen.class);
            intent.putExtra("Score",score);
            startActivity(intent);
        }
        //
        int candyRadiusX=candX+candy.getWidth()/2;
        int candyRadiusY=candY+candy.getHeight()/2;

        if (0<=candyRadiusX && candyRadiusX<=dadWidth && dadY<=candyRadiusY && candyRadiusY<= dadY+dadHeight){
            score+=50;
            candX=-10;
        }
        textViewScore.setText(String.valueOf(score));
    }
}