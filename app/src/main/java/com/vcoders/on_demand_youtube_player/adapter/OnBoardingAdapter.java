package com.vcoders.on_demand_youtube_player.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.vcoders.on_demand_youtube_player.features.onBoarding.OnBoardingView;

import java.util.List;

public class OnBoardingAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    List<Fragment> fragmentList;
    OnBoardingView listener;

    public OnBoardingAdapter(FragmentManager fm, List<Fragment> fragmentList, ViewPager viewPager, OnBoardingView listener) {
        super(fm);
        this.listener = listener;
        this.fragmentList = fragmentList;
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        listener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
