package com.example.jianancangku.ui.fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.NogetBean;
import com.example.jianancangku.bean.OutedHouseBean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.adpter.NogetRecyAdapter;
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
import java.util.Objects;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class NoGetFragment extends BaseFragment {
    private RecyclerView outed_house_recy;
    private NogetRecyAdapter nogetRecyAdapter;
    private int pages = 1;
    private List<NogetBean.ListBean> allList=new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void initModel() {
        getDatas();

    }

    @Override
    protected void initViews() {
        nogetRecyAdapter = new NogetRecyAdapter(getContext(), allList);
        outed_house_recy.setAdapter(nogetRecyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        outed_house_recy.setLayoutManager(linearLayoutManager);
        outed_house_recy.setItemAnimator(new DefaultItemAnimator());
        smartRefreshLayout = getMview().findViewById(R.id.smartlayout);
        smartRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        smartRefreshLayout.setRefreshHeader(new WaterDropHeader(getContext()));
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
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recy_layout;
    }


    private void getDatas() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", pages);
        params.put("size", 50);
        params.put("type", 2);//1待配送 2待取件 3已配送
        OkGo.<BaseData<OutedHouseBean.ListBean>>post(Constant.sendlistAddress)
                .tag(getActivity())
                .params(params)
                .execute(new AbsCallback<BaseData<OutedHouseBean.ListBean>>() {
                    @Override
                    public BaseData<OutedHouseBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final NogetBean nogetBean = BeanConvertor.convertBean(baseData.getDatas(), NogetBean.class);
                        if (!baseData.isSuccessed())
                            ToastUtils.showToast(getActivity(), baseData.getErrorMsg());
                        LogUtils.d(nogetBean.getList());
                        if (!baseData.isSuccessed()) {
                            ToastUtils.showSnakbar(Objects.requireNonNull(getActivity()).getWindow().getDecorView(), baseData.getErrorMsg(), null);
                        }
                        ToastUtils.showToast(getContext(), baseData.getErrorMsg());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed())return;
                                allList.addAll(nogetBean.getList());
                                nogetRecyAdapter.notifyDataSetChanged();

                            }
                        });

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<OutedHouseBean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<OutedHouseBean.ListBean>> response) {


                    }
                });

    }
}
