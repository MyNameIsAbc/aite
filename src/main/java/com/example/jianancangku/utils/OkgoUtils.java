package com.example.jianancangku.utils;

import android.content.Context;

import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.LogInBean;
import com.example.jianancangku.bean.MsgCenterbean;
import com.example.jianancangku.bean.ThingfixBean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.callback.ICallback;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class OkgoUtils<T> {
    private BaseData baseData;

    public void loginpost(String url, Context context, HttpParams params, ICallback iCallback) {
        OkGo.<BaseData<T>>post(url)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<T>>() {
                    @Override
                    public BaseData<T> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        LogUtils.d(baseData);
                        ToastUtils.showToast(context, baseData.isSuccessed() + "++++++++++++++");
                        if (!baseData.isSuccessed()) {
                            iCallback.onFailure(baseData.getErrorMsg());
                        } else {
                            iCallback.onSuccess(baseData.getCode() + "", null);
                            LogInBean thingfixBean = BeanConvertor.convertBean(baseData.getDatas(), LogInBean.class);
                            String a = thingfixBean.getKey();
                            Constant.KEY = a;
                            LogUtils.d(a);
//                                    iCallback.onSuccess(null,listBeans);
                        }
                        return baseData;
                    }

                    @Override
                    public void onStart(Request<BaseData<T>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<T>> response) {


                    }
                });
    }

    public final Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    //    OkGo.<BaseData<ThingfixBean.ListBean>>loginpost(Constant.thingsFixAddress)
//                    .tag(context)
//                .params(params)
//                .execute(new AbsCallback<BaseData<ThingfixBean.ListBean>>() {
//                    @Override
//                    public BaseData<ThingfixBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
//                        LogUtils.d(response);
//                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
//                        final ThingfixBean thingfixBean = BeanConvertor.convertBean(baseData.getDatas(), ThingfixBean.class);
//                        recydatalist = thingfixBean.getList();
//                        LogUtils.d(recydatalist);
//                        return null;
//                    }
//
//                    @Override
//                    public void onStart(Request<BaseData<ThingfixBean.ListBean>, ? extends Request> request) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(Response<BaseData<ThingfixBean.ListBean>> response) {
//                        LogUtils.d(recydatalist);
//                        if (recydatalist == null) {
//                            ToastUtils.showToast(context, "数据错误");
//                            return;
//                        }
//                        thingfixAdapter = new ThingfixAdapter(context, recydatalist);
//                        things_fix_recy.setAdapter(thingfixAdapter);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//                        things_fix_recy.setLayoutManager(linearLayoutManager);
//                        things_fix_recy.setItemAnimator(new DefaultItemAnimator());
//
//                    }
//                });
    public void post(String url, Context context, HttpParams params, ICallback iCallback) {
        OkGo.<BaseData<ThingfixBean.ListBean>>post(url)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<ThingfixBean.ListBean>>() {
                    @Override
                    public BaseData<ThingfixBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        LogUtils.d(baseData);
                        ToastUtils.showToast(context, baseData.isSuccessed() + "++++++++++++++");
                        if (baseData.isSuccessed()) {
                            ThingfixBean thingfixBean = BeanConvertor.convertBean(baseData.getDatas(), ThingfixBean.class);
                            List<ThingfixBean.ListBean> listBeans = thingfixBean.getList();
                            iCallback.onSuccess(String.valueOf(listBeans), listBeans);
                        }
                        if (!baseData.isSuccessed()) {
                            iCallback.onFailure(baseData.getErrorMsg());
                        }

                        //                        recydatalist = thingfixBean.getList();
//                        LogUtils.d(recydatalist);
                        return baseData;
                    }

                    @Override
                    public void onStart(Request<BaseData<ThingfixBean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ThingfixBean.ListBean>> response) {


                    }
                });
    }

    public void post2(String url, Context context, HttpParams params, ICallback iCallback) {
        OkGo.<BaseData<MsgCenterbean.ListBean>>
                post(url).tag(context).params(params).execute(new AbsCallback<BaseData<MsgCenterbean.ListBean>>() {
                            @Override
                            public BaseData<MsgCenterbean.ListBean> convertResponse(okhttp3.Response response) throws
                                    Throwable {
                                LogUtils.d(response);
                                final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                                final MsgCenterbean msgCenterbean = BeanConvertor.convertBean(baseData.getDatas(), MsgCenterbean.class);
                                iCallback.onSuccess(null,msgCenterbean.getList());
                                LogUtils.d(msgCenterbean.getList().get(1).getAdd_time());
                                return baseData;
                            }

                            @Override
                            public void onStart(Request<BaseData<MsgCenterbean.ListBean>, ? extends
                                    Request> request) {

                            }

                            @Override
                            public void onSuccess(Response<BaseData<MsgCenterbean.ListBean>> response) {

                            }
                        });
    }


}
