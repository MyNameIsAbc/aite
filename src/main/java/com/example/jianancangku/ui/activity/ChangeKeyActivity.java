package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.utils.http.HttpOkgoUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChangeKeyActivity extends BaseActivity implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.key_old)
    EditText key_old;
    @BindView(R.id.key_new)
    EditText key_new;
    @BindView(R.id.key_sure)
    EditText key_sure;
    @BindView(R.id.changekey_btn)
    Button changekey_btn;
    @BindView(R.id.iv_back)
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changekey_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changekey_btn:
                changeKey();
                break;
            case R.id.iv_back:
                back();
                break;
        }

    }

    private void init() {
        changekey_btn.setOnClickListener((View.OnClickListener) context);
        iv_back.setOnClickListener((View.OnClickListener) context);
    }

    private void back() {
        onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void changeKey() {
        String old = key_old.getText().toString().trim();
        String keynew = key_new.getText().toString();
        String keysure = key_sure.getText().toString();
        if (TextUtils.isEmpty(keysure))
            ToastUtils.showToast(this, "请输入密码");
        if (TextUtils.isEmpty(keynew) || TextUtils.isEmpty(keysure))
            ToastUtils.showToast(this, "请检查密码");
        if (keynew.equals(keysure))
            changeKeyURL(old, keynew, keysure);
        if (!keynew.equals(keysure))
            ToastUtils.showToast(context, "请检查密码");
    }

    private void changeKeyURL(String old, String keynew, String keysure) {
        HttpParams params = initHttpParams(old, keynew, keysure);
        HttpOkgoUtils.getmInstance().mChangeKeyActivitychangeKeyURL(context, params, Constant.changekeyAdrress);
    }

    private HttpParams initHttpParams(String old, String keynew, String keysure) {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("password_old", old);
        params.put("password", keynew);
        params.put("password_confirm", keysure);
        return params;

    }


}
