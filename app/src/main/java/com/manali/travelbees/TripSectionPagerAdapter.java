package com.manali.travelbees;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ayush on 8/19/2017.
 */

class TripSectionPagerAdapter extends FragmentPagerAdapter {

    public TripSectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PhotosFragment photosFragment = new PhotosFragment();
                return photosFragment;
            case 1:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "PHOTOS";
            case 1:
                return "GROUP CHAT";
            default:
                return null;
        }
    }
}
