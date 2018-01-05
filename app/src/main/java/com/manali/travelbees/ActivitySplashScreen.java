package com.manali.travelbees;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.VideoView;

/*
        **************************************************************************
        *   3 second welcome page with the brand                                 *
        *                                                                        *
        *                                                                        *
        *   Last Edited On : 12/03/17                                            *
        *   Last Edited By : Ayush Manish Agrawal                                *
        *   What Changed   : Added the new video                                 *
        *        			            		                                 *
        **************************************************************************
*/

public class ActivitySplashScreen extends AppCompatActivity {


    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        videoView = findViewById(R.id.splashVideo);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);
        videoView.setVideoURI(video);



        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(isFinishing()){
                    return;
                }

                startActivity(new Intent(ActivitySplashScreen.this, ActivityGoogleSignIn.class));
                finish();
            }
        });
        videoView.start();

               /*try {

                   getSupportActionBar().hide();
                   VideoView videoView = new VideoView(ActivitySplashScreen.this);
                   //setContentView(videoView);
                   Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_video);
                   videoView.setVideoURI(video);

                   videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                       @Override
                       public void onCompletion(MediaPlayer mediaPlayer) {
                           jump();
                       }
                   });
                   videoView.start();
               } catch (Exception e) {
                   jump();
               }


        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump();
        return true;
    }

    private void jump() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, ActivityGoogleSignIn.class));
        finish();
    }*/
    }
}
