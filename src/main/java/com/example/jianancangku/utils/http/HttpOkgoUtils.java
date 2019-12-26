package com.example.jianancangku.utils.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.ChangeKeyBean;
import com.example.jianancangku.bean.KeyBean;
import com.example.jianancangku.bean.LogInBean;
import com.example.jianancangku.bean.MainuiBean;
import com.example.jianancangku.bean.MsgCenterbean;
import com.example.jianancangku.bean.OutedHouseBean;
import com.example.jianancangku.bean.WorkerfixBean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.eventbus.LogInEventBus;
import com.example.jianancangku.ui.activity.LogInActivity;
import com.example.jianancangku.ui.taiguofixuser.TaiGuoFixerActivity;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.SharePreferencesHelper;
import com.example.jianancangku.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

//  ToastUtils.showToast(context, baseData.isSuccessed() ? baseData.isSuccessed() + "" : baseData.getErrorMsg());
public class HttpOkgoUtils {

    private volatile static HttpOkgoUtils mInstance;

    public static HttpOkgoUtils getmInstance() {
        if (mInstance == null) {
            synchronized (HttpOkgoUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpOkgoUtils();
                }
            }
        }
        return mInstance;
    }

    private HttpOkgoUtils() {

    }

    private static boolean isActivity(Context context) {
        return !(context instanceof Activity);
    }

    public void mMsgCenterActivity(Context context, HttpParams params, String urladrress, MsgcenterInterface msgcenterInterface) {
        if (isActivity(context)) return;
        OkGo.<BaseData<MsgCenterbean.ListBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<MsgCenterbean.ListBean>>() {
                    @Override
                    public BaseData<MsgCenterbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        MsgCenterbean msgCenterbean = BeanConvertor.convertBean(baseData.getDatas(), MsgCenterbean.class);
                        if (baseData.isSuccessed())
                            msgcenterInterface.getList(msgCenterbean.getList());
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<MsgCenterbean.ListBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<MsgCenterbean.ListBean>> response) {


                    }
                });


    }

    public interface MsgcenterInterface {
        void getList(List<MsgCenterbean.ListBean> listBeans);
    }

    public void mMainActivity(Context context, HttpParams params, String urladrress, ImageView people_img, TextView worker, TextView worker_name, ImageView zhuguan) {
        if (isActivity(context)) return;
        OkGo.<BaseData<MainuiBean.InfoBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<MainuiBean.InfoBean>>() {
                    @Override
                    public BaseData<MainuiBean.InfoBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        final MainuiBean mainuiBean = BeanConvertor.convertBean(baseData.getDatas(), MainuiBean.class);
                        assert mainuiBean != null;
                        final MainuiBean.InfoBean infoBean = BeanConvertor.convertBean(mainuiBean.getInfo(), MainuiBean.InfoBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                assert infoBean != null;
                                Glide.with(context).load(infoBean.getAvatar()).transform(new CircleCrop()).into(people_img);
                                Constant.peopeliconUrl = infoBean.getAvatar();
                                Constant.peopelwoker = infoBean.getPosition();
                                Constant.peopelname = infoBean.getName();
                                Constant.usernumb = infoBean.getAccount_number();
                                worker.setText(infoBean.getPosition());
                                worker_name.setText(infoBean.getName());
                                ToastUtils.showToast(context, String.valueOf(infoBean.getAccount_number()));
                            }
                        });
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<MainuiBean.InfoBean>, ? extends Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<BaseData<MainuiBean.InfoBean>> response) {
                    }
                });

    }

    public void mLogInActivity(Context context, HttpParams params, String urladrress, String usernumber, String userkey) {
        if (isActivity(context)) return;
        OkGo.<BaseData<LogInBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new com.lzy.okgo.callback.AbsCallback<BaseData<LogInBean>>() {
                    @Override
                    public BaseData<LogInBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final LogInBean logInBean = BeanConvertor.convertBean(baseData.getDatas(), LogInBean.class);
                        SharePreferencesHelper sharePreferencesHelper = new SharePreferencesHelper(context, "USER_INFO");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Constant.KEY = logInBean.getKey();
                                ToastUtils.showToast(context, !TextUtils.isEmpty(Constant.KEY) ? "登陆成功" : "登陆错误");
//                                LogUtils.d(Constant.KEY);
                            }
                        });
                        if (baseData.getCode().toString().equals("200")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    assert logInBean != null;
                                    Constant.usernumber = usernumber;
                                    Constant.isLogin = true;
                                    Constant.KEY = logInBean.getKey();
                                    sharePreferencesHelper.put("usernumber", usernumber);
                                    sharePreferencesHelper.put("usernumber" + "isLogIn", true);
                                    sharePreferencesHelper.put("usernumber" + "userkey", userkey);

                                    ToastUtils.showToast(context, "登陆成功 请等待....");
                                    Set<String> set = new HashSet<>();
                                    set.add(logInBean.getConfig().getMember_id());
                                    JPushInterface.setTags(context, set, null);//设置标签
                                    context.startActivity(new Intent(context, TaiGuoFixerActivity.class));


//                            context.startActivity(new Intent(context, MainActivity.class));
                                }
                            });

                        } else if (!baseData.isSuccessed())
                            ToastUtils.showToast(context, baseData.getErrorMsg());

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<LogInBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<LogInBean>> response) {

                    }
                });

    }

    public void mFindMsgActivityok(Context context, HttpParams params, String urladrress) {
        if (isActivity(context)) return;
        OkGo.<BaseData<ChangeKeyBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<ChangeKeyBean>>() {
                    @Override
                    public BaseData<ChangeKeyBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().toString().trim(), BaseData.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                assert baseData != null;
                                if (baseData.getCode().equals("200")) {
                                    ToastUtils.showToast(context, "修改成功");
                                    context.startActivity(new Intent(context, LogInActivity.class));
                                    ((Activity) context).finish();
                                } else if (!baseData.isSuccessed())
                                    ToastUtils.showToast(context, baseData.getErrorMsg());
                            }
                        });

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<ChangeKeyBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ChangeKeyBean>> response) {

                    }
                });

    }

    public void mFindMsgActivityPhoneCode(Context context, HttpParams params, String urladrress) {
        if (isActivity(context)) return;
        OkGo.<BaseData<ChangeKeyBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<ChangeKeyBean>>() {
                    @Override
                    public BaseData<ChangeKeyBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().toString().trim(), BaseData.class);
                        assert baseData != null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(context, baseData.isSuccessed() ? "发送成功" : baseData.getErrorMsg());
                            }
                        });

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<ChangeKeyBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ChangeKeyBean>> response) {

                    }
                });

    }


    public void mChangeKeyActivitychangeKeyURL(Context context, HttpParams params, String urladrress) {
        if (isActivity(context)) return;
        OkGo.<BaseData<ChangeKeyBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new com.lzy.okgo.callback.AbsCallback<BaseData<ChangeKeyBean>>() {
                    @Override
                    public BaseData<ChangeKeyBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final ChangeKeyBean changeKeyBean = BeanConvertor.convertBean(baseData.getDatas(), ChangeKeyBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (baseData.getCode().equals("200")) {
                                    ToastUtils.showToast(context, "修改成功");
                                    context.startActivity(new Intent(context, LogInActivity.class));
                                    Constant.isLogin = false;
                                    ((Activity) context).finish();
                                } else if (!baseData.isSuccessed())
                                    ToastUtils.showToast(context, baseData.getErrorMsg());
                            }
                        });
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<ChangeKeyBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ChangeKeyBean>> response) {

                    }
                });

    }


    public void mChangeAlonyWorkerKeyActivity(Context context, HttpParams params, String urladrress) {
        if (isActivity(context)) return;
        OkGo.<BaseData<KeyBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<KeyBean>>() {
                    @Override
                    public BaseData<KeyBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        final KeyBean keyBean = BeanConvertor.convertBean(baseData.getDatas(), KeyBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(
                                        context,
                                        baseData.isSuccessed() ? keyBean.getMessage() : keyBean.getError());
                            }
                        });
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


    public void mAllmsgActivity(Context context, HttpParams params, String urladrress) {
        if (isActivity(context)) return;
        OkGo.<BaseData<OutedHouseBean.ListBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<OutedHouseBean.ListBean>>() {
                    @Override
                    public BaseData<OutedHouseBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(context, baseData.isSuccessed() ? "" : baseData.getErrorMsg());
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

    public void mAddWorkerActivitypostUrl(Context context, HttpParams params, String urladrress) {
        if (isActivity(context)) return;
        OkGo.<BaseData<WorkerfixBean.ListBean>>post(urladrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<WorkerfixBean.ListBean>>() {
                    @Override
                    public BaseData<WorkerfixBean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        assert response.body() != null;
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        assert baseData != null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast(context, baseData.isSuccessed() ? "操作成功" : baseData.getErrorMsg());
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


}
