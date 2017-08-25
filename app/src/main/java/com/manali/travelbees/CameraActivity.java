package com.manali.travelbees;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;

public class CameraActivity extends AppCompatActivity {


    private TextureView mTextureView;
    /*private TextureView.SurfaceTextureListener mSurfaceTextureListner =
            new TextureView.SurfaceTextureListener() {
                @Override
            }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
    }
}
