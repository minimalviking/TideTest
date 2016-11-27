package com.minimalviking.tidetest.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minimalviking.tidetest.home.barList.BarListFragment;
import com.minimalviking.tidetest.home.map.BarMapFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0) {
			return BarListFragment.newInstance();
		} else {
			return BarMapFragment.newInstance();
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0) {
			return "Bar List";
		} else {
			return "Bar Map";
		}
	}
}
