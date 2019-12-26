package com.example.jianancangku.ui.fragment;

import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.Thingbookbean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.adpter.ThingbookAdapter;
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

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class OutHouseFragment extends BaseFragment {
    private ThingbookAdapter recyAdapter;
    private RecyclerView thingbook_out_recy;
    private SmartRefreshLayout smartRefreshLayout;
    private List<Thingbookbean.ListBean> allList = new ArrayList<>();
    private int pages = 1;

    @Override
    protected void initModel() {
        getDatas();

    }

    @Override
    protected void initViews() {
        init(getMview());

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.outhouse_layout;
    }



    private void init(View view) {
        thingbook_out_recy = view.findViewById(R.id.thingbook_out_recy);
        recyAdapter = new ThingbookAdapter(getActivity(), allList, null);
        thingbook_out_recy.setAdapter(recyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        thingbook_out_recy.setLayoutManager(linearLayoutManager);
        thingbook_out_recy.setItemAnimator(new DefaultItemAnimator());
        smartRefreshLayout = view.findViewById(R.id.refreshLayout);
        //下拉刷新
        smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
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
    }

    private void getDatas() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", pages);
        params.put("size", 50);
        params.put("type", 2);//2已完成 1待打包
        OkGo.<BaseData<Thingbookbean.ListBean>>post(Constant.thingbookAddress)
                .tag(getActivity())
                .params(params)
                .execute(new AbsCallback<BaseData<Thingbookbean.ListBean>>() {
                    @Override
                    public BaseData<Thingbookbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final Thingbookbean thingbookbean = BeanConvertor.convertBean(baseData.getDatas(), Thingbookbean.class);
                        if (!baseData.isSuccessed())
                            ToastUtils.showToast(getActivity(), baseData.getErrorMsg());
                        thingbookbean.getList();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed())return;
                                allList.addAll(thingbookbean.getList());
                                recyAdapter.notifyDataSetChanged();

                            }
                        });

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<Thingbookbean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<Thingbookbean.ListBean>> response) {


                    }
                });

    }
}
