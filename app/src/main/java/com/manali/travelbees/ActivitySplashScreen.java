package com.manali.travelbees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/*
        **************************************************************************
        *   3 second welcome page with the brand                                 *
        *                                                                        *
        *                                                                        *
        *   Last Edited On : 11/06/17                                            *
        *   Last Edited By : Ayush Manish Agrawal                                *
        *   What Changed   :                                                     *
        *        			            		                                 *
        **************************************************************************
*/

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activity);

        Thread myThread = new Thread(){
            @Override
            public void run() {
               try {
                   sleep(3000);
                   Intent mainActivityIntent = new Intent(ActivitySplashScreen.this, ActivityMain.class);
                   overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                   startActivity(mainActivityIntent);

                   finish();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }


            }
        };

        myThread.start();
    }
}
