package com.example.jianancangku.ui.taiguofixuser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.base.AppConstant;
import com.example.base.BaseFragmentViewPagerApdapter;
import com.example.base.tBaseActivity;
import com.example.jianancangku.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: valy
 * @datetime: 2019-11-27
 * @desc:
 */
public class TaiGuoFixerActivity extends tBaseActivity {
    private BaseFragmentViewPagerApdapter baseFragmentViewPagerApdapter;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    private View[] views;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected boolean isUseMvp() {
        return false;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.taiguo_mainactivity;
    }

    @Override
    protected void initView() {
        initTablayout();
        initFragment();

    }

    private void initTablayout() {

        for (int i = 0; i < AppConstant.MAINUI.settingTv.length ; i++) {
            TabLayout.Tab tab = tablayout.newTab();
            tab.setText(AppConstant.MAINUI.settingTv[i]).setIcon(AppConstant.MAINUI.settingImg[i]);
            tablayout.addTab(tab);
        }

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    private void initFragment() {
        views = new View[AppConstant.MAINUI.settingTv.length];
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i=0;i<AppConstant.MAINUI.settingTv.length;i++){
            views[i] = layoutInflater.inflate(R.layout.main_fragment, null);
            fragments.add(new FixUserMainFragment());
        }
        baseFragmentViewPagerApdapter = new BaseFragmentViewPagerApdapter(this.getSupportFragmentManager(), fragments);
        //一次加载3个 防止销毁（解决懒加载的 只加载一次数据的问题） setOffscreenPageLimit
        viewpager.setOffscreenPageLimit(tablayout.getTabCount());
        viewpager.setAdapter(baseFragmentViewPagerApdapter);
        //滑动绑定
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        //点击tablayout选中绑定
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
