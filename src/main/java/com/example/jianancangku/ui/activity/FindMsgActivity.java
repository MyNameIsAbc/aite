package com.example.jianancangku.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jianancangku.R;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.ChangeKeyBean;
import com.example.jianancangku.bean.LogInBean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.NetworkUtils;
import com.example.jianancangku.utils.StatusBarUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.utils.http.HttpOkgoUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.view.adpter.AreaCodeAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.jianancangku.args.Constant.phonecodefindkeyAddress;

public class FindMsgActivity extends BaseActivity implements View.OnClickListener {
    private int recLen = 30;
    private Button sendSms_btn;
    private EditText find_key_phonenumber;
    @BindView(R.id.iv_back_findmsg)
    ImageView iv_back;
    @BindView(R.id.phonesms_key)
    EditText phonesms_key;
    @BindView(R.id.key_new)
    EditText key_new;
    @BindView(R.id.key_sure)
    EditText key_sure;
    @BindView(R.id.find_key_sure_btn)
    Button find_key_sure_btn;
    @BindView(R.id.findmsg_area_front_txt_id)
    TextView findmsg_area_front_txt_id;
    Unbinder unbinder;
    private String findkeytype = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_msg_layout);
        unbinder = ButterKnife.bind((Activity) context);
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_findmsg:
                startActivity(new Intent(this, LogInActivity.class));
                finish();
                break;
            case R.id.find_key_sure_btn:
                initSurebtn();
                break;
            case R.id.sendSms_btn:
                sendFindMsg();
                break;
            case R.id.findmsg_area_front_txt_id:
                showPopWindow();
                break;
        }
    }

    private void initViews() {
        StatusBarUtils.setColor(context, Color.WHITE);
        initIntent();
        sendSms_btn = findViewById(R.id.sendSms_btn);
        iv_back = findViewById(R.id.iv_back_findmsg);
        iv_back.setOnClickListener(this);
        find_key_phonenumber = findViewById(R.id.find_key_phonenumber);
        sendSms_btn.setOnClickListener((View.OnClickListener) context);
        findmsg_area_front_txt_id.setOnClickListener(this);
        find_key_sure_btn.setOnClickListener(this);


    }

    private void initIntent() {
        String way = getIntent().getStringExtra("findkeyway");
        if (way == null || TextUtils.isEmpty(way)) return;
        if (!way.equals("phone")) findmsg_area_front_txt_id.setVisibility(View.GONE);
        findkeytype = way.equals("phone") ? "Ismobilereg" : "Isemailreg";
    }


    private void sendFindMsg() {
        if (find_key_phonenumber.getText().toString().trim() == null || TextUtils.isEmpty(find_key_phonenumber.getText().toString().trim()))
            return;
        if (!NetworkUtils.isNetworkAvailable(context)) {
            ToastUtils.showToast(context, "网络错误");
            return;
        }
        sendSms_btn.setEnabled(false);
        phonecode(find_key_phonenumber.getText().toString().trim());
        final Timer timer = new Timer();
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    if (!(recLen <= 0))
                        sendSms_btn.setText(recLen + " S");
                    if (recLen <= 0) {
                        timer.cancel();
                        recLen = 30;
                        sendSms_btn.setText(R.string.send);
                        sendSms_btn.setEnabled(true);
                    }
                }
            }
        };
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                recLen--;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 3000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initSurebtn() {
        String findkeyphonenumber = find_key_phonenumber.getText().toString();
        String phonesmskey = phonesms_key.getText().toString();
        String keynew = key_new.getText().toString();
        String keysure = key_sure.getText().toString();
        if (TextUtils.isEmpty(findkeyphonenumber))
            ToastUtils.showToast(this, "账号为空");
        if (TextUtils.isEmpty(phonesmskey))
            ToastUtils.showToast(this, "验证码为空");
        if (TextUtils.isEmpty(keysure))
            ToastUtils.showToast(this, "请输入密码");
        if (TextUtils.isEmpty(keynew) || TextUtils.isEmpty(keysure))
            ToastUtils.showToast(this, "请检查密码");
        if (keynew.equals(keysure) && !TextUtils.isEmpty(keynew) && !TextUtils.isEmpty(keysure))
            ToastUtils.showToast(this, "密码相同");
        if (keynew.equals(keysure)) {
            String num = phonesms_key.getText().toString().trim();
            initfindkey(num);
            ToastUtils.showToast(context, "请注意查看您的验证码");
        }


    }

    private void phonecode(String phoneNumber) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            ToastUtils.showToast(context, "当前无网络");
            return;
        }
        HttpParams params = initPhonecodeHttpParams(phoneNumber);
        HttpOkgoUtils.getmInstance().mFindMsgActivityPhoneCode(context, params, phonecodefindkeyAddress);
    }

    private HttpParams initPhonecodeHttpParams(String phoneNumber) {
        HttpParams params = new HttpParams();
        params.put("mobile", phoneNumber);
        return params;
    }

    private void initfindkey(String num) {
        if (!NetworkUtils.isNetworkAvailable(context))
            ToastUtils.showSnakbar(getWindow().getDecorView(), "当前无网络", null);
        String phonenumber = find_key_phonenumber.getText().toString().trim();
        if (TextUtils.isEmpty(phonenumber))
            return;
        HttpParams params = initfindkeyHttpparams(num, phonenumber);
        HttpOkgoUtils.getmInstance().mFindMsgActivityok(context, params, Constant.findkeyAddress);
    }

    private HttpParams initfindkeyHttpparams(String num, String phonenumber) {
        HttpParams params = new HttpParams();
        params.put("username", phonenumber);
        params.put("regtype", findkeytype);
        params.put("mobile_code", num);
        return params;
    }

    private void showPopWindow() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findmsg_area_front_txt_id.setText("+86");
                PopWindowsUtils.getmInstance().dismissPopWindow();

            }
        };
        PopWindowsUtils.getmInstance().showDownPopupWindow(
                this,
                findmsg_area_front_txt_id,
                R.layout.found_msg_layout,
                0.9f,
                "+86",
                v);
    }
}
