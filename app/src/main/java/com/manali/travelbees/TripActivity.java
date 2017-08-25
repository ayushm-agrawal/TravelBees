package com.manali.travelbees;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TripActivity extends AppCompatActivity {

    private ViewPager tripViewPager;
    private TripSectionPagerAdapter tripSectionPagerAdapter;

    private TabLayout tripTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        String groupId= getIntent().getStringExtra("groupId");


        Toolbar tripToolbar = (Toolbar) findViewById(R.id.trip_page_toolbar);
        setSupportActionBar(tripToolbar);


        //tabs for the trip page
        tripViewPager = (ViewPager) findViewById(R.id.trip_tab_pager);
        tripSectionPagerAdapter = new TripSectionPagerAdapter(getSupportFragmentManager());

        tripViewPager.setAdapter(tripSectionPagerAdapter);

        tripTabLayout = (TabLayout) findViewById(R.id.trip_tabs);
        tripTabLayout.setupWithViewPager(tripViewPager);

        //Used to add new groups
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TripActivity.this, "ADD CAMERA ACTIVITY", Toast.LENGTH_LONG).show();
            }
        });


    }
}
