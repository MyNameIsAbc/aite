package com.example.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.mvp.BasePresenterImpl;
import com.example.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class tBaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends mBaseFragment<V, T> implements View.OnClickListener, BaseView {

    protected abstract void initModel();

    protected abstract void initViews();

    protected abstract int getLayoutResId();

    private SmartRefreshLayout smartRefreshLayout;

    protected float screenwidth = 0;
    protected boolean hasMore = false;


    protected RecyclerView mBaserecyclerView;

    private Unbinder unbinder;

    public View getMview() {
        return mview;
    }

    private View mview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        mview = view;
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (isUseMvp()) {
                mPresenter = getInstance(this, 1);
                mPresenter.attachView((V) this);
            }

        } catch (Exception e) {
            LogUtils.e("mvp错误" + e);
        }
        if (screenwidth == 0) {
            screenwidth = context.getResources().getDisplayMetrics().widthPixels;
        }

    }

    @Override
    protected boolean isUseMvp() {
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initModel();

        LogUtils.d(this.getClass() + "       onViewCreated");
    }

    public float getScreenwidth() {
        return screenwidth;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        if (mPresenter != null)
            mPresenter.detachView();
    }


    protected void initMoreRecy() {
        mBaserecyclerView = mview.findViewById(R.id.recycler_view);
    }


    protected void showMoreRecy() {
        mBaserecyclerView.setVisibility(View.VISIBLE);
    }


    /**
     * 初始化刷新控件
     * 是否可以上拉加载
     *
     * @param isRefresh
     */
    public void initSmartLayout(boolean isRefresh) {
        try {
            smartRefreshLayout = mview.findViewById(R.id.smartlayout);
            smartRefreshLayout.setEnableAutoLoadMore(isRefresh);
            smartRefreshLayout.setRefreshHeader(new WaterDropHeader(context));
            mCurrentPage = 1;
            smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    mCurrentPage = 1;
                    onSmartRefresh();
                    smartRefreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败

                }
            });
            smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    LogUtils.d("mCurrentPage" + mCurrentPage);
                    if (hasMore) {
                        mCurrentPage++;
                        smartRefreshLayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                        onSmartLoadMore();
                    } else {
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();

                    }

                }
            });
        } catch (Exception e) {
            LogUtils.e("initSmartLayout-fail" + e + e.getClass());
        }

    }

    protected String replaceString(String msg, int start, int end) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < msg.toCharArray().length; i++) {
            if (i > start && i < end) {
                b.append("*");
            } else {
                b.append(msg.toCharArray()[i]);
            }
        }
        return b.toString();
    }

    @Override
    protected void onSmartLoadMore() {

    }

    @Override
    protected void onSmartRefresh() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 缩放操作
        webSettings.setSupportZoom(true); // 支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); // 设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); // 隐藏原生的缩放控件
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
    }

    protected class JsInterface {
        private WebView webView;

        public JsInterface(WebView webView) {
            this.webView = webView;
        }

        /**
         * 返回
         */
        @JavascriptInterface
        public void AppGoBack() {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                getActivity().finish();
            }
        }
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
        LogUtils.d("服务器错误信息+++++++" + msg);
    }

    protected void showToast(String msg) {
        ToastUtils.showToast(context, msg);
    }

    protected void showToast(String msg, int gravity) {
        ToastUtils.showToast(context, msg, gravity);
    }

    protected void onBackPressed() {
        if (getActivity() != null)
            getActivity().onBackPressed();
        else {
            LogUtils.d("未得到上下文");
            showToast("处理返回键错误 ");
        }
    }

    protected HttpParams initKeyParams() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        return params;
    }

    protected boolean isStringEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
