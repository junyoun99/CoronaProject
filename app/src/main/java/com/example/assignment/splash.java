package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.MainActivity;

public class splash extends AppCompatActivity {

    private TextView mText;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mText = (TextView) findViewById(R.id.text);
        mImage = (ImageView) findViewById(R.id.image);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_imageview);
        Animation anima = AnimationUtils.loadAnimation(this,R.anim.splash_textview);

        mImage.startAnimation(animation);
        mText.startAnimation(anima);

       moveMain(2);
    }
/*
    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent = new Intent(splash.this, MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

*/
    private void moveMain(int sec){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
                finish();
                }
            }, 1000*sec);
    }


}