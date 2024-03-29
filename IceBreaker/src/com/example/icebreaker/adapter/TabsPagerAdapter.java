package com.example.icebreaker.adapter;

import com.example.icebreaker.MessagesFragment;
import com.example.icebreaker.ProfileFragment;
import com.example.icebreaker.ExploreFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter{
	 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return new ExploreFragment();
        case 1:
            return new ProfileFragment();
        case 2:
        	return new MessagesFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        return 3;
    }
}
