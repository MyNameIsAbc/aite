package com.example.jianancangku.view.adpter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.jianancangku.ui.fragment.GoingSendFragment;
import com.example.jianancangku.ui.fragment.NoGetFragment;
import com.example.jianancangku.ui.fragment.NoSendFragment;

public class OutedHouseFragmentAdapter extends FragmentPagerAdapter {
    private int num;
    GoingSendFragment goingSendFragment;
    NoGetFragment noGetFragment;
    NoSendFragment noSendFragment;


    public OutedHouseFragmentAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                if (noSendFragment == null) {
                    return new NoSendFragment();
                }


            case 1:
                if (noGetFragment == null) {
                    return new NoGetFragment();
                }

            case 2:
                if (goingSendFragment == null) {
                    return new GoingSendFragment();
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