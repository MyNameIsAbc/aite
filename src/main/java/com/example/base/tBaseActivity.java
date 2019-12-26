package com.example.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.utils.AppManager;
import com.example.jianancangku.utils.KeyBoardUtils;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.SharePreferencesHelper;
import com.example.jianancangku.utils.StatusBarUtils;
import com.example.jianancangku.utils.SystemUtil;
import com.example.jianancangku.utils.ToastUtils;
import com.example.mvp.BasePresenterImpl;
import com.example.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-23
 * @desc:
 */

public abstract class tBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends mBaseActivity<V, T> implements View.OnClickListener, BaseView {

    protected static final String TAG = "TODO";


    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initDatas();

    protected abstract void initResume();

    protected abstract void initReStart();


    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    private Bundle savedInstanceState;


    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
//        if (isCanSavedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty())
            this.savedInstanceState = savedInstanceState;
        try {
            if (isUseMvp()) {
                //mvp
                mPresenter = getInstance(this, 1);
                mPresenter.attachView((V) this);
            }
        } catch (Exception e) {
            LogUtils.e("mvp（可能未使用mvp格式）" + "/n" + e.getClass() + e);
        }
        context = this;
        unbinder = ButterKnife.bind((Activity) context);
        AppManager.getInstance().addActivity((Activity) context);
//        StatusBarUtils.setTransparent(context);
        StatusBarUtils.setColor(context, getResources().getColor(R.color.white));
        initView();
        initDatas();

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected boolean isUseMvp() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initResume();
        if (!SystemUtil.isNetworkConnected()) {
//            ToastUtils.showToast(context, "请检查网络设置");
            LogUtils.d("当前无网络");
        }


    }

    protected HttpParams initKeyParams() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        return params;
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharePreferencesHelper sharePreferencesHelper = new SharePreferencesHelper(context, "PERMISSION_OK");
        if (!sharePreferencesHelper.contain("PERMISSION_OK") && !Boolean.valueOf(String.valueOf(sharePreferencesHelper.getSharePreference("PERMISSION_OK", false))))
            applypermission();
    }

    @Override
    protected void onSmartLoadMore() {


    }

    protected boolean isFileHas(File file) {
        return file.exists();
    }

    @Override
    protected void onSmartRefresh() {


    }

    protected String getUrlKey(String url, String key) {
        return Uri.parse(url).getQueryParameter(key);
    }

    protected int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    protected int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    protected void showToast(final String msg, final int gravity) {
        runOnUiThread(() -> ToastUtils.showToast(context, msg, gravity));
    }

    protected void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(context, msg);

            }
        });
    }


    protected void killThisActvity() {
        AppManager.getInstance().killActivity((Activity) context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void applyperssionbody() {

    }


    @Override
    public void showError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(context, msg);
                if (msg.equals("请登录")) {
                    Intent intent = new Intent();
                    intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
                    AppManager.getInstance().killAllActivity();
                    startActivity(intent);
                }
                LogUtils.e("服务器返回错误信息-----------" + msg);
            }
        });
    }

    protected void hideSoftWare() {
        KeyBoardUtils.hideKeyboard(getWindow().getDecorView());
    }
}
