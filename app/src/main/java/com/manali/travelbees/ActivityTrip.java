package com.manali.travelbees;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/*
        **************************************************************************
        *   This class populates the trips view for the user                     *
        *                                                                        *
        *                                                                        *
        *   Last Edited On : 11/06/17                                            *
        *   Last Edited By : Ayush Manish Agrawal                                *
        *   What Changed   :                                                     *
        *        			            		                                 *
        **************************************************************************
*/

public class ActivityTrip extends AppCompatActivity {

    private static final String TAG="TripActivity";
    private ViewPager tripViewPager;
    private SectionPagerAdapterTrips sectionPagerAdapterTrips;


    private TabLayout tripTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        String groupId= getIntent().getStringExtra("groupId");


        setupToolbar();

        //tabs for the trip page
        tripViewPager = (ViewPager) findViewById(R.id.trip_tab_pager);
        sectionPagerAdapterTrips = new SectionPagerAdapterTrips(getSupportFragmentManager());

        tripViewPager.setAdapter(sectionPagerAdapterTrips);

        tripTabLayout = (TabLayout) findViewById(R.id.trip_tabs);
        tripTabLayout.setupWithViewPager(tripViewPager);

        //Used to add new groups
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityTrip.this, "ADD CAMERA ACTIVITY", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setupToolbar() {
        Toolbar tripToolbar = (Toolbar) findViewById(R.id.trip_page_toolbar);
        setSupportActionBar(tripToolbar);

        ImageView profileMenu = (ImageView) findViewById(R.id.settingsMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings." );
            }
        });
    }
}
