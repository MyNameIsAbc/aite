package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.utils.ToastUtils;
import com.example.jianancangku.utils.http.HttpOkgoUtils;
import com.example.jianancangku.view.PopWindowsUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddWorkerActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.worker_height_linear)
    LinearLayout worker_height_linear;
    @BindView(R.id.worker_height_txt)
    TextView worker_height_txt;
    @BindView(R.id.worker_name)
    EditText worker_name;
    @BindView(R.id.sure_change_btn)
    Button sure_change_btn;
    @BindView(R.id.account_txt)
    EditText account_txt;
    @BindView(R.id.key_edit)
    EditText key_edit;
    Unbinder unbinder;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_worker_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();
    }

    private void init() {
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_change_btn:
                change();
                break;
            case R.id.worker_height_linear:
                showbottompop();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.account_txt:
                break;
        }
    }

    private void showbottompop() {
        PopWindowsUtils.getmInstance().showBottomPopupWindow(context, new PopWindowsUtils.Icallback() {
            @Override
            public String call(String msg) {
                worker_height_txt.setText(msg);
                type=msg.equals("入库员")?"1":"2";
                return null;
            }
        });

    }


    private void initViews() {
        sure_change_btn.setOnClickListener((View.OnClickListener) context);
        worker_height_linear.setOnClickListener((View.OnClickListener) context);
        iv_back.setOnClickListener((View.OnClickListener) context);
    }


    private void postUrl(String name, String account, String type, String key) {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("member_name", name);
        params.put("account_number", account);
        params.put("member_passwd", key);
        params.put("position_type", type);
        HttpOkgoUtils.getmInstance().mAddWorkerActivitypostUrl(context,params,Constant.createAccountAdrress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    private void change() {
        // name account KEY
        if (TextUtils.isEmpty(
                worker_name.getText().toString().trim()) || TextUtils.isEmpty(
                        account_txt.getText().toString().trim())||TextUtils.isEmpty(
                                key_edit.getText().toString().trim())) {
            ToastUtils.showToast(context, "请检查信息");
            return;
        }
        if (type==null||TextUtils.isEmpty(type))type="1";
        postUrl(worker_name.getText().toString().trim(), account_txt.getText().toString().trim(), type,key_edit.getText().toString().trim());
    }
}
