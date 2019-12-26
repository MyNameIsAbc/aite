package com.example.jianancangku.view.adpter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.jianancangku.ui.fragment.GoHouseFragment;
import com.example.jianancangku.ui.fragment.OutHouseFragment;

public class FragmentAdpter extends FragmentPagerAdapter {
    private int num;
    GoHouseFragment goHouseFragment;
    OutHouseFragment outHouseFragment;


    public FragmentAdpter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                if (goHouseFragment == null) {
                    return new GoHouseFragment();
                }


            case 1:
                if (outHouseFragment == null) {
                    return new OutHouseFragment();
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