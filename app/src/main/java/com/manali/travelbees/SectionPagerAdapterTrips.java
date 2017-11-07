package com.manali.travelbees;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class SectionPagerAdapterTrips extends FragmentPagerAdapter {

    SectionPagerAdapterTrips(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentPhotos();
            case 1:
                return new FragmentChat();
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
