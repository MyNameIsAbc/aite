package com.example.jianancangku.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.Thingbookbean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.ui.activity.QrCodeActivity;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.example.jianancangku.view.RecyView;
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

public class GoHouseFragment extends BaseFragment {
    private ThingbookAdapter recyAdapter;
    private RecyView thingbook_recy;
    private SmartRefreshLayout smartRefreshLayout;
    private Button makehouse_btn, add_btn;
    private List<String> fixList = new ArrayList<>();
    private int pages = 1;
    private List<Thingbookbean.ListBean> allList = new ArrayList<>();
    private ImageView empty_iv;

    @Override
    protected void initModel() {
        getDatas();
    }

    @Override
    protected void initViews() {
        smartRefreshLayout = getMview().findViewById(R.id.refreshLayout);
        makehouse_btn = getMview().findViewById(R.id.makehouse_btn);
        add_btn = getMview().findViewById(R.id.add_btn);
        empty_iv = getMview().findViewById(R.id.empty_iv);
        thingbook_recy = getMview().findViewById(R.id.thingbook_recy);
        recyAdapter = new ThingbookAdapter(getActivity(), allList, "others");
        thingbook_recy.setAdapter(recyAdapter);
        thingbook_recy.setEmptyView(empty_iv);
        recyAdapter.setGetfixSenderInterface(new ThingbookAdapter.GetfixSenderInterface() {
            @Override
            public void p(int postion, String type) {
                if (type.equals("add")) {
                    fixList.add(allList.get(postion).getWarehouse_order_id());
                    LogUtils.d(allList.get(postion).getWarehouse_order_id());
                } else if (type.equals("out")) {
                    try {
                        fixList.remove(allList.get(postion).getWarehouse_order_id());
                    } catch (Exception e) {
                        LogUtils.e(e.getCause() + e.getMessage() + e.getClass());
                    }
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        thingbook_recy.setLayoutManager(linearLayoutManager);
        thingbook_recy.setItemAnimator(new DefaultItemAnimator());
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("outinterface", "houseAll");
                Intent intent = new Intent(getActivity(), QrCodeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        makehouse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixBook();
            }
        });
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
        return R.layout.gohouse_layout;
    }



    private StringBuilder initList() {
        StringBuilder warehouse_order_id = new StringBuilder();
        if (fixList != null) {
            for (int i = 0; i < fixList.size(); i++) {
                String a = fixList.get(i);
                if (i != fixList.size() - 1)
                    warehouse_order_id.append(a).append(",");
                else
                    warehouse_order_id.append(a);
            }
        }
        return warehouse_order_id;
    }


    private void fixBook() {
        HttpParams params = new HttpParams();
        LogUtils.e(initList().toString());
        LogUtils.e(Constant.KEY);
        params.put("KEY", Constant.KEY);
        params.put("warehouse_order_id", initList().toString());
        OkGo.<BaseData<Thingbookbean.ListBean>>post(Constant.fixouthouseAdrress)
                .tag(getActivity())
                .params(params)
                .execute(new AbsCallback<BaseData<Thingbookbean.ListBean>>() {
                    @Override
                    public BaseData<Thingbookbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final Thingbookbean thingbookbean = BeanConvertor.convertBean(baseData.getDatas(), Thingbookbean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (baseData.isSuccessed())
                                    PopWindowsUtils.getmInstance().showcenterPopupWindow(getActivity(), "包裹打包成功");
                                else ToastUtils.showToast(getActivity(), baseData.getErrorMsg());


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

    private void getDatas() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", pages);
        params.put("size", 50);
        params.put("type", 1);//2已完成 1待打包
        OkGo.<BaseData<Thingbookbean.ListBean>>post(Constant.thingbookAddress)
                .tag(getActivity())
                .params(params)
                .execute(new AbsCallback<BaseData<Thingbookbean.ListBean>>() {
                    @Override
                    public BaseData<Thingbookbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        final Thingbookbean thingbookbean = BeanConvertor.convertBean(baseData.getDatas(), Thingbookbean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed())
                                    ToastUtils.showToast(getActivity(), baseData.getErrorMsg());
                                if (thingbookbean.getList() == null) return;
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
