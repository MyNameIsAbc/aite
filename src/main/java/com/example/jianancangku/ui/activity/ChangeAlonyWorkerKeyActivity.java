package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.utils.http.HttpOkgoUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChangeAlonyWorkerKeyActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back)
    ImageView iv_back;
    Unbinder unbinder;
    @BindView(R.id.key_new)
    EditText key_new;
    @BindView(R.id.key_sure)
    EditText key_sure;
    @BindView(R.id.changekey_btn)
    Button changekey_btn;
    private static String warehouse_clerk_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_alony_worker_key_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();
    }

    private void getDatas(String id, String password, String password_confirm) {
        HttpParams params = initHttpParams(id, password, password_confirm);
        HttpOkgoUtils.getmInstance().mChangeAlonyWorkerKeyActivity(context, params, Constant.workerfixkeyAdrress);
    }

    private HttpParams initHttpParams(String id, String password, String password_confirm) {
        HttpParams params = new HttpParams();
        params.put("KEY", Constant.KEY);
        params.put("warehouse_clerk_id", id);
        params.put("type", 2);//1 修改职位和状态 2 修改员工密码
        params.put("password", password);//1 修改职位和状态 2 修改员工密码
        params.put("password_confirm", password_confirm);//1 修改职位和状态 2 修改员工密码
        return params;
    }

    private void init() {
        initBundle();
        changekey_btn.setOnClickListener((View.OnClickListener) context);
        iv_back.setOnClickListener((View.OnClickListener) context);

    }

    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            warehouse_clerk_id = bundle.getString("warehouse_clerk_id");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changekey_btn:
                getDatas(warehouse_clerk_id, key_new.getText().toString().trim(), key_sure.getText().toString().trim());
                break;
            case R.id.iv_back:
                onBackPressed();
                break;

        }
    }
}
