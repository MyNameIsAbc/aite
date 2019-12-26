package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.Thingbookbean;
import com.example.jianancangku.bean.ThingfixBean;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.KeyBoardUtils;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.view.adpter.ThingbookAdapter;
import com.example.jianancangku.view.adpter.ThingfixAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.seach_edit)
    EditText seach_edit;
    @BindView(R.id.sure_seach_btn)
    Button sure_seach_btn;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.main_recy)
    RecyclerView main_recy;
    @BindView(R.id.seacher_recy)
    RecyclerView seacher_recy;

    Unbinder unbinder;
    private List<ThingfixBean.ListBean> recydatalist;
    private ThingfixAdapter thingfixAdapter;
    private ThingbookAdapter thingbookAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seacher_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();
    }

    private void init() {
        sure_seach_btn.setOnClickListener(this::onClick);
        iv_back.setOnClickListener(this::onClick);
        seach_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                sure_seach_btn.setText("取消");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (seach_edit.getText().toString().length() == 0)
                    sure_seach_btn.setText("取消");
                else
                    sure_seach_btn.setText("搜索");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getWorkerDatas(String package_sn) {
        HttpParams params = initParams(package_sn);
        OkGo.<BaseData<Thingbookbean.ListBean>>post(Constant.thingbookAddress)
                .tag(context)
                .params(params)
                .execute(new com.example.jianancangku.callback.AbsCallback<BaseData<Thingbookbean.ListBean>>() {
                    @Override
                    public BaseData<Thingbookbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final Thingbookbean thingbookbean = BeanConvertor.convertBean(baseData.getDatas(), Thingbookbean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                thingbookAdapter = new ThingbookAdapter(context, thingbookbean.getList(), "others");
                                seacher_recy.setAdapter(thingbookAdapter);
                                thingbookAdapter.setGetfixSenderInterface(new ThingbookAdapter.GetfixSenderInterface() {
                                    @Override
                                    public void p(int postion, String type) {

                                    }
                                });
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                seacher_recy.setLayoutManager(linearLayoutManager);
                                seacher_recy.setItemAnimator(new DefaultItemAnimator());
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

    private HttpParams initParams(String package_sn) {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("p", 1);
        params.put("size", 20);
        params.put("package_sn", package_sn);
        return params;
    }

    private void getDatas(String package_sn) {
        HttpParams params = initParams(package_sn);
        OkGo.<BaseData<ThingfixBean.ListBean>>post(Constant.thingsFixAddress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<ThingfixBean.ListBean>>() {
                    @Override
                    public BaseData<ThingfixBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final ThingfixBean thingfixBean = BeanConvertor.convertBean(baseData.getDatas(), ThingfixBean.class);
                        recydatalist = thingfixBean.getList();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!baseData.isSuccessed())
                                    ToastUtils.showToast(context, "未查询到本订单");
                                else {
                                    thingfixAdapter = new ThingfixAdapter(context, recydatalist);
                                    main_recy.setAdapter(thingfixAdapter);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                    main_recy.setLayoutManager(linearLayoutManager);
                                    main_recy.setItemAnimator(new DefaultItemAnimator());
                                }
                            }
                        });

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<ThingfixBean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ThingfixBean.ListBean>> response) {


                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_seach_btn:
                if (seach_edit.getText().toString().length() != 0) {
                    getDatas(seach_edit.getText().toString().trim());
                    getWorkerDatas(seach_edit.getText().toString().trim());
                }
                KeyBoardUtils.hideKeyboard(seach_edit);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}
