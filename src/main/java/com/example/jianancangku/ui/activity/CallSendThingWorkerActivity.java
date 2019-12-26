package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.jianancangku.R;
import com.example.jianancangku.view.adpter.OutedHouseFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CallSendThingWorkerActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.start_thingscar_viewpager)
    ViewPager start_thingscar_viewpager;
    @BindView(R.id.start_thingscar_tabMode)
    TabLayout start_thingscar_tabMode;
    private OutedHouseFragmentAdapter outedHouseFragmentAdapter;
    private View[] views;
    @BindView(R.id.start_iv_back)
    ImageView start_iv_back;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_thingscar_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_iv_back:
                back();
                break;
        }
    }

    private void init() {
        initviews();
        initviewPagerFragment();
    }

    private void back() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    private void initviews() {
        start_iv_back.setOnClickListener((View.OnClickListener) context);
    }

    private void initviewPagerFragment() {
        views = new View[3];
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        views[0] = layoutInflater.inflate(R.layout.recy_layout, null);
        views[1] = layoutInflater.inflate(R.layout.recy_layout, null);
        views[2] = layoutInflater.inflate(R.layout.recy_layout, null);
        outedHouseFragmentAdapter = new OutedHouseFragmentAdapter(getSupportFragmentManager(), start_thingscar_tabMode.getTabCount());
        start_thingscar_viewpager.setAdapter(outedHouseFragmentAdapter);
        start_thingscar_viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(start_thingscar_tabMode));
        start_thingscar_tabMode.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                start_thingscar_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



}
