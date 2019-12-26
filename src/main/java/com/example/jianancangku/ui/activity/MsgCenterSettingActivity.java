package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jianancangku.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MsgCenterSettingActivity extends BaseActivity implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.iv_back_msgcenter_setting)
    ImageView iv_back_msgcenter_setting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msgcenter_setting_layout);
        unbinder=ButterKnife.bind((Activity) context);
        init();

    }

    private void init() {
        iv_back_msgcenter_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
