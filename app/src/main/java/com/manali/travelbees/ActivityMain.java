package com.manali.travelbees;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
        **************************************************************************
        *   This class contain the main activity of the trips which the user     *
        *    will see                                                            *
        *                                                                        *
        *   Last Edited On : 11/06/17                                            *
        *   Last Edited By : Ayush Manish Agrawal                                *
        *   What Changed   :                                                     *
        *        			            		                                 *
        **************************************************************************
*/

public class ActivityMain extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "GoogleActivity";

    private ViewPager mViewPager;
    private SectionPagerAdapterMain mSectionsPageAdapter;

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        mAuth = FirebaseAuth.getInstance();

        //Used to Sign out user from the google account
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                    .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);


        //Tabs for the main Page (Groups and Friends)
        mViewPager = (ViewPager) findViewById(R.id.main_tab_pager);
        mSectionsPageAdapter = new SectionPagerAdapterMain(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPageAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //Used to add new groups
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(ActivityMain.this, ActivityCreateNewTrip.class);
                startActivity(createGroupIntent);
            }
        });
    }

    @Override
    //check if user is authenticated (not null) and update UI
    public void onStart() {
        super.onStart();
        // Check if the user is signed in and update UI
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            sendToSignIn();
        }


    }

    //send user to signIn page after logging out
    private void sendToSignIn() {
        Intent signInIntent = new Intent(ActivityMain.this, ActivityGoogleSignIn.class);
        startActivity(signInIntent);
        finish();
    }

    private void signOut() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_btn){
            FirebaseAuth.getInstance().signOut();
            signOut();
            sendToSignIn();
        }
        if(item.getItemId() == R.id.main_settings_btn){

        }

        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
