package com.example.jianancangku.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.AreaCodeBean;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.JpushUtils;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.NetworkUtils;
import com.example.jianancangku.utils.SharePreferencesHelper;
import com.example.jianancangku.utils.StatusBarUtils;
import com.example.jianancangku.utils.StringUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.utils.http.HttpOkgoUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.example.jianancangku.view.adpter.AreaCodeAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.log_img_id)
    ImageView log_img_id;
    @BindView(R.id.phone_number_login_txt)
    TextView phone_number_login_txt;
    @BindView(R.id.sms_number_login_txt)
    TextView sms_number_login_txt;
    @BindView(R.id.phone_area_front_txt_id)
    TextView phone_area_front_txt_id;
    @BindView(R.id.phone_area_front_img_id)
    ImageView phone_area_front_img_id;
    @BindView(R.id.number_get_edit)
    EditText number_get_edit;
    @BindView(R.id.phone_area_front_ll)
    LinearLayout phone_area_front_ll;
    @BindView(R.id.key_get_edit)
    TextInputEditText key_get_edit;
    @BindView(R.id.widget_textinput_layout)
    TextInputLayout widget_textinput_layout;
    @BindView(R.id.see_eye_img)
    CheckBox see_eye_img;
    @BindView(R.id.remember_key_img)
    ImageView remember_key_img;
    @BindView(R.id.remember_key_txt)
    CheckBox remember_key_txt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.find_key_txt)
    TextView find_key_txt;
    private SharePreferencesHelper sharePreferencesHelper;
    private List<AreaCodeBean.ListBean> listBeanArrayList = new ArrayList<>();
    private String PHONECODE = "00086";
    private String LOGAWAY = "1";//1手机 2邮箱


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        applypermission();
        initviews();
        LogUtils.d(Constant.LogInAddress);
        getData();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                logInFix();
                break;
            case R.id.phone_number_login_txt:
                LOGAWAY = "1";
                phone_number_login_txt.setTextColor(getResources().getColor(R.color.yelllow));
                sms_number_login_txt.setTextColor(getResources().getColor(R.color.glay));
                phone_area_front_img_id.setVisibility(View.VISIBLE);
                phone_area_front_txt_id.setVisibility(View.VISIBLE);
                number_get_edit.setHint("手机号码");
                key_get_edit.setText("");
                if (number_get_edit.getText().toString().length() != 0)
                    number_get_edit.setText("");
                break;
            case R.id.sms_number_login_txt:
                LOGAWAY = "2";
                phone_number_login_txt.setTextColor(getResources().getColor(R.color.glay));
                sms_number_login_txt.setTextColor(getResources().getColor(R.color.yelllow));
                phone_area_front_img_id.setVisibility(View.GONE);
                phone_area_front_txt_id.setVisibility(View.GONE);
                number_get_edit.setHint("邮箱编号");
                key_get_edit.setText("");
                if (number_get_edit.getText().toString().length() != 0)
                    number_get_edit.setText("");
                break;
            case R.id.phone_area_front_txt_id:
            case R.id.phone_area_front_img_id:
                showPopWindow();
                break;
            case R.id.find_key_txt:
                Intent intent = new Intent(context, FindMsgActivity.class);
                intent.putExtra("findkeyway", "phone");
                context.startActivity(intent);
                break;
        }
    }

    private void logInFix() {
        String username = number_get_edit.getText().toString().trim();
        String userkey = key_get_edit.getText().toString().trim();
        if (TextUtils.isEmpty(userkey) || TextUtils.isEmpty(username))
            ToastUtils.showToast(context, "请检查账号信息");
        if (!NetworkUtils.isNetworkAvailable(context)) {
            ToastUtils.showToast(context, "请检查网络设置");
            return;
        }
        HttpParams params = initHttpParam(username, userkey);
        HttpOkgoUtils.getmInstance().mLogInActivity(context, params, Constant.LogInAddress, username, userkey);


    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    /**
     * client=ios
     * &code=00086&
     * device_id=121c83f7604a058fe4d&
     * loginType=isMobile
     * &login_role=3&
     * password=123456&
     * username=16600001111
     * <p>
     * 参数名字	提交方式	类型	是否必须	默认值	其他	说明	test
     * username	post	字符串	必须			用户名/手机号/邮箱
     * password	post	字符串	必须			登录密码
     * client	post	字符串	必须			来源端口 wap ios android
     * device_id	post	字符串	必须			设备id
     * loginType	post	字符串	必须			登陆方式:isMobile、isEmail、isAccount
     * code	post	字符串	可选			手机区号：如00086、00855、00066
     * login_role	post	整型	必须			登录角色 1会员登录 2商户登录 3仓库员登录 4配送员
     *
     * @param username
     * @param userkey
     * @return
     */
    public HttpParams initHttpParam(String username, String userkey) {
        HttpParams params = new HttpParams();
        params.put("username", username);
        params.put("code", PHONECODE);
        params.put("loginType", LOGAWAY.equals("1") ? Constant.MOBILE : Constant.EMAIL);
        params.put("login_role", 3);
        params.put("password", userkey);
        params.put("client", Constant.device);
        params.put("device_id", StringUtils.isEmpty(JpushUtils.getDeviceId(context)) ? JpushUtils.getDeviceId(context) : "12345");
        return params;
    }


    private void showPopWindow() {
        AreaCodeAdapter areaCodeAdapter = new AreaCodeAdapter(context, listBeanArrayList);
        LogUtils.e(PopWindowsUtils.getmInstance().showChoiceAreaPopupwindow(context, phone_area_front_txt_id, areaCodeAdapter));
        areaCodeAdapter.setGetfixSenderInterface(new AreaCodeAdapter.GetfixSenderInterface() {
            @Override
            public void p(int postion) {
                PopWindowsUtils.getmInstance().dismissPopWindow();
                if (!listBeanArrayList.isEmpty() && listBeanArrayList.get(postion) != null) {
                    phone_area_front_txt_id.setText(listBeanArrayList.get(postion).getCode());
                    PHONECODE = listBeanArrayList.get(postion).getArea_code();
                }
            }
        });
    }

    private void getData() {
        OkGo.<BaseData<AreaCodeBean>>post(Constant.getAreaCodeAdrress)
                .tag(context)
                .execute(new AbsCallback<BaseData<AreaCodeBean>>() {
                    @Override
                    public BaseData<AreaCodeBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        final AreaCodeBean areaCodeBean = BeanConvertor.convertBean(baseData.getDatas(), AreaCodeBean.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LogUtils.d(areaCodeBean.getList().get(2).getArea_name());
                                listBeanArrayList.addAll(areaCodeBean.getList());

                            }
                        });

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<AreaCodeBean>, ? extends Request> request) {

                    }

                    @Override
                    public void onSuccess(Response<BaseData<AreaCodeBean>> response) {

                    }
                });

    }

    private void initviews() {
        StatusBarUtils.setColor(context, Color.WHITE);
        phone_number_login_txt.setOnClickListener(this);
        sms_number_login_txt.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        find_key_txt.setOnClickListener(this);
        phone_area_front_img_id.setOnClickListener(this);
        phone_area_front_txt_id.setOnClickListener(this);
        number_get_edit.setHint("手机号码");
        key_get_edit.setHint("登录密码");
        sharePreferencesHelper = new SharePreferencesHelper(context, "USER_INFO");
        number_get_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (sharePreferencesHelper.contain(number_get_edit.getText().toString().trim())) {
                    String key = (String) sharePreferencesHelper.getSharePreference(
                            number_get_edit.getText().toString().trim() + "KEY",
                            number_get_edit.getText().toString().trim() + "KEY");
                    key_get_edit.setText(key);
                    remember_key_img.setImageDrawable(getResources().getDrawable(R.drawable.correct));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        remember_key_txt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharePreferencesHelper = new SharePreferencesHelper(context, "USER_INFO");
                String usernumber = number_get_edit.getText().toString().trim();
                String userkey = key_get_edit.getText().toString().trim();
                if (isChecked) {
                    remember_key_img.setImageDrawable(getResources().getDrawable(R.drawable.correct));
                    if (userkey != null && !TextUtils.isEmpty(userkey)) {
                        if (usernumber != null && !TextUtils.isEmpty(usernumber)) {
                            sharePreferencesHelper.put(usernumber, usernumber);
                            sharePreferencesHelper.put(usernumber + "KEY", userkey);
                        }
                    }


                } else {
                    remember_key_img.setImageDrawable(getResources().getDrawable(R.drawable.corrtect_none));
                    if (sharePreferencesHelper.contain(usernumber)) {
                        sharePreferencesHelper.remove(usernumber);
                        sharePreferencesHelper.remove(usernumber + "KEY");
                    }

                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
