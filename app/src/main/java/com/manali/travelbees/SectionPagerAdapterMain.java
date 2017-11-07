package com.manali.travelbees;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class SectionPagerAdapterMain extends FragmentPagerAdapter{
    SectionPagerAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentGroups();
            case 1:
                return new FragmentFriends();
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
                return "GROUPS";
            case 1:
                return "FRIENDS";
            default:
                return null;
        }
    }
}
