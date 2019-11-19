package tw.edu.pu.cs.wrist_band;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Mario extends AppCompatActivity {
    FrameLayout gameFrame;
    int frameHeight,frameWidth,initalframeWidth;
    Drawable imageMarioRight,imageMarioLeft;
    LinearLayout startLayout;
    ImageView box,mario,coin;
    int mariosize;
    float marioX,marioY;
    float coinX,coinY;
    float boxX,boxY;
    //score
    TextView scoreLabel,hightScoreLabel;
    int score,highscore,timeCount;
    SharedPreferences settings;
    //class
    Timer timer;
    Handler handler = new Handler();

    //Status
    boolean start_flg = false;
    boolean action_flg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mario);
        getSupportActionBar().hide(); //隱藏標題
        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        mario = findViewById(R.id.mario);
        box = findViewById(R.id.box);
        coin = findViewById(R.id.coin);
        scoreLabel = findViewById(R.id.scoreLabel);
        hightScoreLabel = findViewById(R.id.highScoreLabel);

        imageMarioLeft = getResources().getDrawable(R.drawable.mario_left);
        imageMarioRight = getResources().getDrawable(R.drawable.mario_right);

        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highscore = settings.getInt("HIGH_SCORE",0);
        hightScoreLabel.setText("High Score : "+highscore);

    }
    public void changePos(){
        timeCount +=20;

        //coin
        coinY +=12;
        float coinCenterX = coinX + coin.getWidth() / 2;
        float coinCenterY = coinY + coin.getHeight() / 2;
        if(hitCheck(coinCenterX, coinCenterY)){
            coinY = frameHeight + 100;
            score += 10;
            if (initalframeWidth > frameWidth *110/100){
                frameWidth = frameWidth *110/100;
                changeFrameWidth(frameWidth);
            }
        }
        if (coinY > frameHeight){
            coinY = -100;
            coinX = (float)Math.floor(Math.random() * (frameWidth - coin.getWidth()));
        }
        coin.setX(coinX);
        coin.setY(coinY);

        //box
        boxY  += 18;
        float boxCenterX = boxX + box.getWidth() / 2;
        float boxCenterY = boxY + box.getHeight() /2;
        if(hitCheck(boxCenterX,boxCenterY)){
            boxY = frameHeight + 100;
            frameWidth = frameWidth* 80 /100;
            changeFrameWidth(frameWidth);

            if(frameWidth<mariosize){
                //Game over
                gameover();
            }
        }
        if(boxY > frameHeight){
            boxY = -100;
            boxX = (float)Math.floor(Math.random() * (frameWidth - box.getWidth()));
        }
        box.setX(boxX);
        box.setY(boxY);
        if(action_flg){
            marioX += 14;
            mario.setImageDrawable(imageMarioRight);
        }else{
            marioX -= 14;
            mario.setImageDrawable(imageMarioLeft);
        }
        if(marioX < 0){
            marioX = 0;
            mario.setImageDrawable(imageMarioRight);
        }
        if(frameWidth - mariosize < marioX){
            marioX = frameWidth - mariosize;
            mario.setImageDrawable(imageMarioLeft);
        }
        mario.setX(marioX);

        scoreLabel.setText("Score :"+score);
    }
    public boolean hitCheck(float x,float y){
        if(marioX <= x && x <= marioX + mariosize &&
                marioY <= y && y<frameHeight){
            return true;
        }
        return false;
    }
    public void changeFrameWidth(int frameWidth){
        ViewGroup.LayoutParams params = gameFrame.getLayoutParams();
        params.width = frameWidth;
        gameFrame.setLayoutParams(params);
    }
    public void gameover(){
        timer.cancel();
        timer = null;
        start_flg = false;
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        changeFrameWidth(initalframeWidth);

        startLayout.setVisibility(View.VISIBLE);
        mario.setVisibility(View.INVISIBLE);
        box.setVisibility(View.INVISIBLE);
        coin.setVisibility(View.INVISIBLE);

        if (score > highscore){
            highscore = score;
            hightScoreLabel.setText("High Score : "+highscore);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE",highscore);
            editor.commit();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(start_flg){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else if (event.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }
        return true;
    }


    public void startGame(View view){
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);
        if (frameHeight == 0 ){
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            initalframeWidth = frameWidth;

            mariosize = mario.getHeight();
            marioX = mario.getX();
            marioY = mario.getY();
        }
        frameWidth = initalframeWidth;
        mario.setX(0.0f);
        box.setY(3000.0f);
        coin.setY(3000.0f);

        boxY = box.getY();
        coinY = coin.getY();

        mario.setVisibility(View.VISIBLE);
        coin.setVisibility(View.VISIBLE);
        box.setVisibility(View.VISIBLE);

        timeCount = 0;
        score = 0;
        scoreLabel.setText("Score : 0");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(start_flg){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }
        },0,20);
    }
    public void quitGame(View view){
    finish();
    }
}
