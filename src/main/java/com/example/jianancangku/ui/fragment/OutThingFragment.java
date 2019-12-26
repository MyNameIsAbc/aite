package com.example.jianancangku.ui.fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.GothingBean;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.adpter.ThingAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
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

public class OutThingFragment extends BaseFragment {
    private RecyclerView outed_house_recy;
    private List<GothingBean.ListBean> allList = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private int pages = 1;
    private ThingAdapter recyAdapter;


    @Override
    protected void initModel() {

        getDatas();

    }

    @Override
    protected void initViews() {
        outed_house_recy = getMview().findViewById(R.id.thingbook_out_recy);
        recyAdapter = new ThingAdapter(getActivity(), allList, "out");
        outed_house_recy.setAdapter(recyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        outed_house_recy.setLayoutManager(linearLayoutManager);
        outed_house_recy.setItemAnimator(new DefaultItemAnimator());
        smartRefreshLayout = getMview().findViewById(R.id.refreshLayout);
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
                pages++;
                getDatas();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.outhouse_layout;
    }

    private void getDatas() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", pages);//1 入库员 2 出库员
        params.put("size", 20);
        params.put("type", 2);

        OkGo.<BaseData<GothingBean.ListBean>>post(Constant.workerthingAdrress)
                .tag(getContext())
                .params(params)
                .execute(new AbsCallback<BaseData<GothingBean.ListBean>>() {
                    @Override
                    public BaseData<GothingBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final GothingBean gothingBean = BeanConvertor.convertBean(baseData.getDatas(), GothingBean.class);
                        if (!baseData.isSuccessed())
                            ToastUtils.showToast(getActivity(), baseData.getErrorMsg());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed())return;
                                allList.addAll(gothingBean.getList());
                                recyAdapter.notifyDataSetChanged();

                            }
                        });
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<GothingBean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<GothingBean.ListBean>> response) {

                    }
                });

    }

}
