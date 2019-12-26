package com.example.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-26
 * @desc:
 */
public class BaseFragmentViewPagerApdapter extends FragmentPagerAdapter {
    private String page_type = "";
    private ArrayList<Fragment> fragments;

    public BaseFragmentViewPagerApdapter(FragmentManager fm, ArrayList<Fragment> fragments, String page_type) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.page_type = page_type;
    }

    public BaseFragmentViewPagerApdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        Bundle bundle = new Bundle();
        if (page_type != null && !page_type.equals(""))
            bundle.putString("page_type", page_type);
        bundle.putString("position", String.valueOf(position));
        assert fragment != null;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
