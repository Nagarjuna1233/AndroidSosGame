package com.sos.nag.sosgame;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageView gyroView = (ImageView) findViewById(R.id.splash);
        gyroView.setBackgroundResource(R.drawable.splash_img_animation);
        AnimationDrawable gyroAnimation = (AnimationDrawable) gyroView.getBackground();
        gyroAnimation.start();
        // setting up a thread
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 8000) {       // this screen hold itself for 3sec
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    Intent i = new Intent();
                    i.setClass(getBaseContext(),HomePageActivity.class);  // RegisterActivity is the                                                                                                  //activity which opens after the splash screen
                    startActivity(i);
                }
            }
        };
        splashThread.start();   // starting of thread

    }
}
