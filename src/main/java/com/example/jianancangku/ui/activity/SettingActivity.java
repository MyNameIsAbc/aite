package com.example.jianancangku.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.SPUtils;
import com.example.jianancangku.utils.SharePreferencesHelper;
import com.example.jianancangku.utils.VersionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.exit_btn)
    Button exit_btn;
    @BindView(R.id.currrent_number)
    TextView currrent_number;
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        unbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        currrent_number.setText(VersionUtils.getAppVersionName(context));
        exit_btn.setOnClickListener(this::onClick);
        iv_back.setOnClickListener(this::onClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_btn:
                Constant.isLogin = false;
                SharePreferencesHelper sharePreferencesHelper = new SharePreferencesHelper(context, "USER_INFO");
                sharePreferencesHelper.put(String.valueOf("usernumber" + "isLogIn"), false);
                startActivity(new Intent(context, LogInActivity.class));
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
