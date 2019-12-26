package com.example.jianancangku.view.adpter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.jianancangku.ui.fragment.GoThingFragment;
import com.example.jianancangku.ui.fragment.OutThingFragment;

public class ThingsFixActivityViewPagerApdapter extends FragmentPagerAdapter {
    private int num;
    GoThingFragment goh;
    OutThingFragment outh;

    public ThingsFixActivityViewPagerApdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                if (goh == null) {
                    return new GoThingFragment();
                }
            case 1:
                if (outh == null) {
                    return new OutThingFragment();
                }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
