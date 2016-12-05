package com.selfapps.rav.alltogether.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.selfapps.rav.alltogether.fragments.EventsFragment;
import com.selfapps.rav.alltogether.fragments.MembersFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                EventsFragment eventsFragment = new EventsFragment();
                return eventsFragment;
            case 1:
                MembersFragment membersFragment = new MembersFragment();
                return membersFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Events";
            case 1:
                return "Members";
            default:
                return null;
        }
    }
}
