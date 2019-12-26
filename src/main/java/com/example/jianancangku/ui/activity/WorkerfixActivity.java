package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.KeyBean;
import com.example.jianancangku.bean.WorkerfixBean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.example.jianancangku.view.adpter.WorkerFixAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
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

public class WorkerfixActivity extends BaseActivity {
    @BindView(R.id.workerfix_recy)
    RecyclerView workerfix_recy;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_back_workerfix)
    ImageView iv_back_workerfix;
    @BindView(R.id.add_txt)
    TextView add_txt;

    private Unbinder unbinder;
    private WorkerFixAdapter workerFixAdapter;
    private int pages = 1;
    private List<WorkerfixBean.ListBean> allList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker_fix_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        add_txt.setOnClickListener(this);
        iv_back_workerfix.setOnClickListener(this);
        workerFixAdapter = new WorkerFixAdapter(context, allList);
        workerfix_recy.setAdapter(workerFixAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        workerfix_recy.setLayoutManager(linearLayoutManager);
        workerfix_recy.setItemAnimator(new DefaultItemAnimator());
        workerFixAdapter.setUnIcallback(new WorkerFixAdapter.UnIcallback() {
            @Override
            public void getPostion(int postion) {
                PopWindowsUtils.getmInstance().showdialogPopwindow(context, new PopWindowsUtils.showdialogPopwindowIcallback() {
                    @Override
                    public void call(boolean isok) {
                        killworkerPost(allList.get(postion).getWarehouse_clerk_id(), isok ? "2" : "1");
                    }
                }, "确定要禁用员工: " + allList.get(postion).getName() + "？");
            }
        });
        workerFixAdapter.setIcallback(new WorkerFixAdapter.Icallback() {
            @Override
            public void getPostion(int postion) {
                Bundle bundle = new Bundle();
                bundle.putString("id", allList.get(postion).getWarehouse_clerk_id());
                Intent intent = new Intent(context, WorkerFixChirenActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        });
        //下拉刷新
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
                pages++;
                getDatas();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });


        getDatas();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    private void killworkerPost(String warehouse_clerk_id, String isok) {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("warehouse_clerk_id", warehouse_clerk_id);
        params.put("type", isok);//1 禁用 2 启用

        OkGo.<BaseData<KeyBean>>post(Constant.killworkerAdrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<KeyBean>>() {
                    @Override
                    public BaseData<KeyBean> convertResponse(okhttp3.Response response) throws Throwable {
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final KeyBean keyBean = BeanConvertor.convertBean(baseData.getDatas(), KeyBean.class);
                        ToastUtils.showToast(context, baseData.isSuccessed() ? keyBean.getMessage() : keyBean.getError());
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<KeyBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<KeyBean>> response) {
                    }
                });

    }

    private void getDatas() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", pages);
        params.put("size", 50);
        OkGo.<BaseData<WorkerfixBean.ListBean>>post(Constant.workerFixAddress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<WorkerfixBean.ListBean>>() {
                    @Override
                    public BaseData<WorkerfixBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        final WorkerfixBean workerfixBean = BeanConvertor.convertBean(baseData.getDatas(), WorkerfixBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed())return;
                                allList.addAll(workerfixBean.getList());
                                workerFixAdapter.notifyDataSetChanged();
                            }
                        });
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<WorkerfixBean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<WorkerfixBean.ListBean>> response) {

                    }

                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_txt:
                startActivity(new Intent(context, AddWorkerActivity.class));
                break;
            case R.id.iv_back_workerfix:
                onBackPressed();
                break;
        }
    }
}

