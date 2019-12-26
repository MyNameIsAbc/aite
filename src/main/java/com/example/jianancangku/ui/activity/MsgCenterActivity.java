package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.MsgCenterbean;
import com.example.jianancangku.utils.http.HttpOkgoUtils;
import com.example.jianancangku.view.adpter.MsgCenterRecyAdapter;
import com.lzy.okgo.model.HttpParams;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MsgCenterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back_msgcenter)
    ImageView ivbackmsgcenter;
    ImageView setting_icon_msgcenter;
    RecyclerView msgcenterrecy;

    private MsgCenterRecyAdapter recyAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    Unbinder unbinder;
    private int pages = 1;
    private List<MsgCenterbean.ListBean> allListBeans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msgcenter_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();
    }

    private void init() {
        smartRefreshLayout = findViewById(R.id.refreshLayout);
        msgcenterrecy = findViewById(R.id.msgcenter_recy);
        setting_icon_msgcenter = findViewById(R.id.setting_icon_msgcenter);

        ivbackmsgcenter.setOnClickListener(this);
        setting_icon_msgcenter.setOnClickListener(this);

        recyAdapter = new MsgCenterRecyAdapter(MsgCenterActivity.this, allListBeans);
        msgcenterrecy.setAdapter(recyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        msgcenterrecy.setLayoutManager(linearLayoutManager);
        msgcenterrecy.setItemAnimator(new DefaultItemAnimator());

        smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(context));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pages = 1;
                getDatas();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
                pages++;
                getDatas();

            }
        });

        getDatas();
    }


    private void getDatas() {
        HttpParams params = initHttpParams();
        HttpOkgoUtils.getmInstance().mMsgCenterActivity(context, params, Constant.msgcenterAddress, new HttpOkgoUtils.MsgcenterInterface() {
            @Override
            public void getList(List<MsgCenterbean.ListBean> listBeans) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allListBeans.addAll(listBeans);
                        recyAdapter.notifyDataSetChanged();


                    }
                });

            }
        });


    }

    private HttpParams initHttpParams() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", pages);
        params.put("size", 20);
        return params;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_msgcenter:
                onBackPressed();
                break;
            case R.id.setting_icon_msgcenter:
                startActivity(new Intent(context, MsgCenterSettingActivity.class));
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
