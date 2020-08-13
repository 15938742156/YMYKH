package com.ykh.yinmeng.ymykh2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author Angel
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
	
	private ArrayList<Fragment> fragmentsList;
	
	/**
	 * 这个构造方法把ArrayList<Fragment>传过来
	 * @param fm
	 * @param fragments
	 */
	public FragmentViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

	public FragmentViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentsList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentsList.size();
	}

}
