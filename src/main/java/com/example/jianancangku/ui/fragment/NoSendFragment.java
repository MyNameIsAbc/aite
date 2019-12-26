package com.example.jianancangku.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.OutedHouseBean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.ui.activity.AllmsgActivity;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.example.jianancangku.view.adpter.OutedHouseAdapter;
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

public class NoSendFragment extends BaseFragment {
    private RecyclerView outed_house_recy;
    private OutedHouseAdapter recyAdapter;
    private Button call_worker_btn;
    private List<String> callList = new ArrayList<>();
    private int pages = 1;
    private SmartRefreshLayout smartRefreshLayout;
    private List<OutedHouseBean.ListBean> allList = new ArrayList<>();


    @Override
    protected void initModel() {
        getDatas();


    }

    @Override
    protected void initViews() {
        outed_house_recy = getMview().findViewById(R.id.outed_house_recy);
        smartRefreshLayout = getMview().findViewById(R.id.smartlayout);
        call_worker_btn = getMview().findViewById(R.id.call_worker_btn);
        recyAdapter = new OutedHouseAdapter(getContext(), allList, "ok");
        outed_house_recy.setAdapter(recyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        outed_house_recy.setLayoutManager(linearLayoutManager);
        outed_house_recy.setItemAnimator(new DefaultItemAnimator());
        recyAdapter.setGetCallSenderInterface(new OutedHouseAdapter.GetCallSenderInterface() {
            @Override
            public void getList(String allpackageNumber) {
                LogUtils.e(allpackageNumber);
            }
        });
        recyAdapter.setOnitemClickListener(new OutedHouseAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                Intent intent = new Intent(getContext(), AllmsgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("number", allList.get(position).getPackage_sn());
                bundle.putString("time", allList.get(position).getCreate_time());
                bundle.putString("addrress", allList.get(position).getBusiness_address());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void p(int postion, String type) {
                if (type.equals("add")) {
                    callList.add(allList.get(postion).getPackage_sn());
                } else if (type.equals("out")) {
                    try {
                        callList.remove(allList.get(postion).getPackage_sn());
                    } catch (Exception e) {
                        LogUtils.e(e.getCause() + e.getMessage() + e.getClass());
                    }
                }

            }
        });

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
        call_worker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopWindowsUtils.getmInstance().showfindWorkerPopupWindow(getActivity());
                getCallworkerDatas();
            }
        });
    }


    private StringBuilder initList() {
        StringBuilder warehouse_order_id = new StringBuilder();
        if (callList != null) {
            for (int i = 0; i < callList.size(); i++) {
                String a = callList.get(i);
                if (i != callList.size() - 1)
                    warehouse_order_id.append(a).append(",");
                else
                    warehouse_order_id.append(a);
            }
        }
        return warehouse_order_id;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.callworker_fragment_layout;
    }


    private void getCallworkerDatas() {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("warehouse_order_id", initList().toString());
        OkGo.<BaseData<OutedHouseBean>>post(Constant.callfindsendworkerAddress)
                .tag(getActivity())
                .params(params)
                .execute(new AbsCallback<BaseData<OutedHouseBean>>() {
                    @Override
                    public BaseData<OutedHouseBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
//                        final OutedHouseBean outedHouseBean = BeanConvertor.convertBean(baseData.getDatas(), OutedHouseBean.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                assert baseData != null;
                                ToastUtils.showToast(getActivity(), baseData.isSuccessed() ? "呼叫成功" : baseData.getErrorMsg());

                            }
                        });


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<OutedHouseBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<OutedHouseBean>> response) {


                    }
                });
    }

    private void getDatas() {
//        HttpParams httpParams=initHttpParams();
//        HttpOkgoUtils.getmInstance().mMainActivity(getActivity(),);
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", pages);
        params.put("size", 50);
        params.put("type", 1);//1待配送 2待取件 3已配送
        OkGo.<BaseData<OutedHouseBean.ListBean>>post(Constant.sendlistAddress)
                .tag(getActivity())
                .params(params)
                .execute(new AbsCallback<BaseData<OutedHouseBean.ListBean>>() {
                    @Override
                    public BaseData<OutedHouseBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final OutedHouseBean outedHouseBean = BeanConvertor.convertBean(baseData.getDatas(), OutedHouseBean.class);
                        if (!baseData.isSuccessed())
                            ToastUtils.showToast(getActivity(), baseData.getErrorMsg());
                        LogUtils.d(outedHouseBean.getList());
                        if (!baseData.isSuccessed()) {
                            ToastUtils.showSnakbar(Objects.requireNonNull(getActivity()).getWindow().getDecorView(), baseData.getErrorMsg(), null);
                        }
                        ToastUtils.showToast(getContext(), baseData.getErrorMsg());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed()) return;
                                allList.addAll(outedHouseBean.getList());
                                recyAdapter.notifyDataSetChanged();

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

//    private HttpParams initHttpParams() {
//        HttpParams params=new HttpParams();
//        params.put();
//        return null;
//    }
}
