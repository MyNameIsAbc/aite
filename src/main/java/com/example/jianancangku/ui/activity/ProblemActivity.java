package com.example.jianancangku.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jianancangku.R;
import com.example.jianancangku.args.Constant;
import com.example.jianancangku.bean.BaseData;
import com.example.jianancangku.bean.MainuiBean;
import com.example.jianancangku.callback.AbsCallback;
import com.example.jianancangku.utils.BeanConvertor;
import com.example.jianancangku.utils.LogUtils;
import com.example.jianancangku.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.jianancangku.args.Constant.postproblemAdrress;

public class ProblemActivity extends BaseActivity implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.sure_btn)
    Button sure_btn;
    @BindView(R.id.package_id)
    EditText package_id;
    @BindView(R.id.problem_reason)
    EditText problem_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_layout);
        unbinder = ButterKnife.bind((Activity) context);
        init();

    }

    private void init() {
        iv_back.setOnClickListener((View.OnClickListener) context);
        sure_btn.setOnClickListener((View.OnClickListener) context);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.sure_btn:
                getDatas();
                break;
        }
    }

    private void getDatas() {
        String reason=problem_reason.getText().toString().trim();
        String packageNumber=package_id.getText().toString().trim();
        HttpParams params=new HttpParams();
        params.put("KEY",Constant.KEY);
        params.put("package_sn",reason);
        params.put("remark",packageNumber);

        OkGo.<BaseData<MainuiBean.InfoBean>>post(postproblemAdrress)
                .tag(context)
                .params(params)
                .execute(new AbsCallback<BaseData<MainuiBean.InfoBean>>() {
                    @Override
                    public BaseData<MainuiBean.InfoBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response);
                        final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
                        ToastUtils.showToast(context,baseData.isSuccessed()?"提交成功":baseData.getErrorMsg());
                        return baseData;
                    }

                    @Override
                    public void onStart(Request<BaseData<MainuiBean.InfoBean>, ? extends Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<BaseData<MainuiBean.InfoBean>> response) {
                    }
                });

    }
}
